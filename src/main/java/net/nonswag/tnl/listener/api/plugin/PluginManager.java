package net.nonswag.tnl.listener.api.plugin;

import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.reflection.Reflection;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.command.CommandManager;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {

    @Nonnull
    public static Plugin[] getPlugins() {
        return Bukkit.getPluginManager().getPlugins();
    }

    @Nullable
    public static Plugin getPlugin(@Nonnull String name) {
        return Bukkit.getPluginManager().getPlugin(name);
    }

    @Nonnull
    public static List<String> getPlugins(boolean includeVersion) {
        List<String> plugins = new ArrayList<>();
        for (Plugin plugin : getPlugins()) {
            if (plugin instanceof PluginBuilder builder && !builder.isManageable()) continue;
            if (includeVersion) plugins.add(getName(plugin, true));
            else plugins.add(plugin.getName());
        }
        return plugins;
    }

    @Nonnull
    public static String getName(@Nonnull Plugin plugin, boolean includeVersion) {
        if (includeVersion) {
            return (plugin.isEnabled() ? "§a" : "§c") + plugin.getName() + " §8(§7" + getVersion(plugin) +
                    (plugin.getDescription().getAPIVersion() == null || plugin.getDescription().getAPIVersion().isEmpty() ? "*" : "") + "§8)";
        } else return (plugin.isEnabled() ? "§a" : "§c") + plugin.getName();
    }

    @Nonnull
    public static String getVersion(@Nonnull Plugin plugin) {
        return plugin.getDescription().getVersion();
    }

    @Nullable
    private static Throwable loadSecure(@Nonnull File file) {
        try {
            load(file);
            return null;
        } catch (Throwable t) {
            return t;
        }
    }

    @Nonnull
    public static List<Plugin> getDependencies(@Nonnull Plugin plugin) {
        List<Plugin> dependencies = new ArrayList<>();
        for (Plugin all : getPlugins()) {
            if ((!all.equals(plugin)) && (all.getDescription().getDepend().contains(plugin.getName()) ||
                    all.getDescription().getSoftDepend().contains(plugin.getName()))) dependencies.add(all);
        }
        return dependencies;
    }

    public static void enable(@Nonnull Plugin plugin) {
        if (!plugin.isEnabled()) Bukkit.getPluginManager().enablePlugin(plugin);
    }

    public static void disable(@Nonnull Plugin plugin) {
        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    public static void unload(@Nonnull Plugin plugin) throws Exception {
        HandlerList.unregisterAll(plugin);
        disable(plugin);
        CommandManager.unregisterAllCommands(plugin);
        ClassLoader cl = plugin.getClass().getClassLoader();
        Exception exception = null;
        if (cl instanceof URLClassLoader loader) {
            Reflection.Field.set(cl, "plugin", null);
            Reflection.Field.set(cl, "pluginInit", null);
            try {
                loader.close();
            } catch (IOException e) {
                Logger.error.println("Failed to close the class loader of <'" + plugin.getName() + "'>", e);
                exception = e;
            }
        } else {
            exception = new IllegalStateException(String.format("(%s) <'URLClassLoader'> is not compatible with <'%s'>", plugin.getName(), cl.getClass().getName()));
        }
        PluginHelper.getInstance().getPlugins().remove(plugin);
        PluginHelper.getInstance().getLookupNames().remove(plugin.getName().toLowerCase());
        if (exception != null) throw exception;
    }

    public static void unloadSecure(@Nonnull Plugin plugin) {
        try {
            unload(plugin);
        } catch (Exception ignored) {
        }
    }

    @Nonnull
    public static Plugin load(@Nonnull File file) throws InvalidPluginException, InvalidDescriptionException {
        Plugin plugin = Bukkit.getPluginManager().loadPlugin(file);
        if (plugin != null) plugin.onLoad();
        if (plugin != null && !plugin.isEnabled()) Bukkit.getPluginManager().enablePlugin(plugin);
        else throw new NullPointerException("Failed to load <'" + file.getAbsolutePath() + "'> as a plugin");
        return plugin;
    }

    public static void reload(@Nonnull Plugin plugin) throws Throwable {
        if (!plugin.isEnabled()) return;
        List<Plugin> dependencies = getDependencies(plugin);
        for (Plugin all : dependencies) unload(all);
        unload(plugin);
        System.gc();
        Throwable secure = loadSecure(new File("plugins/" + plugin.getName() + ".jar"));
        if (secure != null) throw secure;
        for (Plugin all : dependencies) {
            if ((secure = loadSecure(new File("plugins/" + all.getName() + ".jar"))) == null) continue;
            Logger.error.println("Failed to reload dependency <'" + all.getName() + "'> of plugin <'" + plugin.getName() + "'>", secure);
        }
        if (secure != null) throw secure;
    }

    public static void enableAll() {
        for (Plugin plugin : getPlugins()) enable(plugin);
    }

    public static void disableAll() {
        for (Plugin plugin : getPlugins()) disable(plugin);
    }

    public static void unloadAll() throws Exception {
        for (Plugin plugin : getPlugins()) unload(plugin);
    }

    public static void reloadAll() throws Throwable {
        for (Plugin plugin : getPlugins()) reload(plugin);
    }

    public static void checkFile(@Nonnull Plugin plugin) {
        File file;
        if (plugin instanceof TNLPlugin pl) file = pl.getFile();
        else if (plugin instanceof JavaPlugin pl) file = Reflection.Field.get(pl, JavaPlugin.class, "file");
        else if (plugin instanceof PluginBuilder) return;
        else file = new File(new File("plugins/"), plugin.getName() + ".jar");
        if (file != null) {
            if (!file.getName().equals(plugin.getName() + ".jar")) {
                Logger.error.printf("Please rename the jar file of <'%s'> to <'%s.jar'>", plugin.getName()).println();
            }
        } else Logger.error.printf("Couldn't find the jar file for <'%s'>", plugin.getName()).println();
    }

    public static void checkFiles() {
        for (Plugin plugin : getPlugins()) if (plugin.isEnabled()) checkFile(plugin);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void checkUpdate(@Nonnull File file) {
        File updateDirectory = Bukkit.getUpdateFolderFile();
        if (!updateDirectory.isDirectory()) return;
        File updateFile = new File(updateDirectory, file.getName());
        if (updateFile.isFile() && FileUtil.copy(updateFile, file)) updateFile.delete();
    }

    @Nonnull
    public static List<PluginBuilder> getCustomPlugins(@Nonnull Plugin plugin) {
        List<PluginBuilder> plugins = new ArrayList<>();
        if (plugin.equals(Bootstrap.getInstance())) plugins.add(Mapping.get());
        for (Plugin all : getPlugins()) {
            if (all instanceof PluginBuilder builder && builder.getOwner().equals(plugin)) plugins.add(builder);
        }
        return plugins;
    }
}
