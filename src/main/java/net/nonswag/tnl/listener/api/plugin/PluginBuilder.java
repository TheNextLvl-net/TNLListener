package net.nonswag.tnl.listener.api.plugin;

import lombok.Getter;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.object.Condition;
import net.nonswag.core.api.reflection.Reflection;
import net.nonswag.tnl.listener.api.command.CommandManager;
import net.nonswag.tnl.listener.api.event.EventManager;
import net.nonswag.tnl.listener.api.scheduler.Task;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class PluginBuilder extends PluginBase implements CombinedPlugin {
    private final PluginDescriptionFile description;
    private final PluginLogger logger;
    private final Plugin owner;
    @Nullable
    private EventManager eventManager = null;
    @Nullable
    private CommandManager commandManager = null;
    protected boolean enabled = false;
    @Getter
    private boolean manageable = false;

    protected PluginBuilder(PluginDescriptionFile description, Plugin owner) {
        this.description = description;
        this.owner = owner;
        setAuthors("NonSwag");
        setDescription("An official TNL-Production");
        setWebsite("https://www.thenextlvl.net");
        this.logger = new PluginLogger(this);
    }

    protected PluginBuilder(String name, String version, String main, Plugin owner) {
        this(new PluginDescriptionFile(name, version, main), owner);
    }

    protected PluginBuilder(String name, String version, Plugin owner) {
        this(new DescriptionBuilder(name, version).build(), owner);
    }

    protected PluginBuilder(String name, Plugin owner) {
        this(new DescriptionBuilder(name).build(), owner);
    }

    protected PluginBuilder(Class<?> clazz, Plugin owner) {
        this(new DescriptionBuilder(clazz).build(), owner);
    }

    protected final void setName(String name) {
        Reflection.Field.set(getDescription(), "name", name);
    }

    @Nonnull
    @Override
    @Deprecated(forRemoval = true)
    public PluginLoader getPluginLoader() {
        return null;
    }

    protected final void setAuthors(String... authors) {
        Reflection.Field.set(getDescription(), "authors", Arrays.asList(authors));
    }

    protected final void setAPIVersion(String apiVersion) {
        Reflection.Field.set(getDescription(), "apiVersion", apiVersion);
    }

    protected final void setDescription(String description) {
        Reflection.Field.set(getDescription(), "description", description);
    }

    protected final void setWebsite(String website) {
        Reflection.Field.set(getDescription(), "website", website);
    }

    protected final void setPrefix(String prefix) {
        Reflection.Field.set(getDescription(), "prefix", prefix);
    }

    @Nonnull
    @Override
    public final PluginDescriptionFile getDescription() {
        return description;
    }

    public final void setEnabled(boolean enabled) {
        if (!isEnabled() && enabled) {
            this.enabled = true;
            onEnable();
        } else if (isEnabled() && !enabled) {
            Bukkit.shutdown();
            this.enabled = false;
            onDisable();
        }
    }

    @Override
    public void onLoad() {
        try {
            load();
        } catch (Throwable t) {
            Logger.error.println("There was an error while loading plugin <'" + getName() + "'>", t);
        }
    }

    @Override
    public void onEnable() {
        try {
            if (!enabled) throw new IllegalStateException("called <'onEnable'> before <'setEnabled(true)'>");
            enable();
        } catch (Throwable t) {
            Logger.error.println("There was an error while enabling plugin <'" + getName() + "'>", t);
        }
    }

    @Override
    public void onDisable() {
        try {
            if (enabled) throw new IllegalStateException("called <'onDisable'> before <'setEnabled(false)'>");
            try {
                disable();
            } finally {
                getCommandManager().unregisterAllCommands();
                getEventManager().unregisterAll();
                HandlerList.unregisterAll(this);
            }
        } catch (Throwable t) {
            Logger.error.println("There was an error while disabling plugin <'" + getName() + "'>", t);
        }
    }

    public PluginBuilder register() {
        unregister();
        PluginHelper.getInstance().getPlugins().add(this);
        return this;
    }

    public PluginBuilder unregister() {
        PluginHelper.getInstance().getPlugins().removeIf(plugin -> plugin.getName().equals(getName()));
        return this;
    }

    public boolean isRegistered() {
        return PluginHelper.getInstance().getPlugins().contains(this);
    }

    public final EventManager getEventManager() {
        return eventManager == null ? eventManager = new EventManager(this) : eventManager;
    }

    public final CommandManager getCommandManager() {
        return commandManager == null ? commandManager = new CommandManager(this) : commandManager;
    }

    @Override
    public Thread async(Condition condition, Runnable runnable) {
        Thread thread = new Thread(() -> {
            if (condition.check()) runnable.run();
        });
        thread.start();
        return thread;
    }

    @Override
    public Thread async(Condition condition, Runnable runnable, long millis) {
        Thread thread = new Thread(() -> {
            if (Task.sleep(millis)) if (condition.check()) runnable.run();
        });
        thread.start();
        return thread;
    }

    @Override
    public void sync(Condition condition, Runnable runnable) {
        if (Bukkit.isPrimaryThread()) {
            if (condition.check()) runnable.run();
        } else Bukkit.getScheduler().runTask(this, () -> {
            if (condition.check()) runnable.run();
        });
    }

    @Override
    public BukkitTask sync(Condition condition, Runnable runnable, long ticks) {
        return Bukkit.getScheduler().runTaskLater(this, () -> {
            if (condition.check()) runnable.run();
        }, ticks);
    }

    @Override
    public BukkitTask repeatSynced(Runnable runnable, long period, long delay) {
        return Bukkit.getScheduler().runTaskTimer(this, runnable, delay, period);
    }

    @Override
    public void repeatSynced(Consumer<BukkitTask> runnable, long period, long delay) {
        Bukkit.getScheduler().runTaskTimer(this, runnable, delay, period);
    }

    @Override
    public void repeatAsync(Consumer<BukkitTask> runnable, long period, long delay) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, runnable, delay, period);
    }

    @Override
    public BukkitTask repeatAsync(Runnable runnable, long period, long delay) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(this, runnable, delay, period);
    }

    @Nonnull
    @Override
    public File getDataFolder() {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public FileConfiguration getConfig() {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getResource(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveConfig() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveDefaultConfig() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveResource(String s, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reloadConfig() {
        throw new UnsupportedOperationException();
    }

    public final Plugin getOwner() {
        return owner;
    }

    @Nonnull
    @Override
    public final Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public final boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isNaggable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNaggable(boolean naggable) {
        throw new UnsupportedOperationException();
    }

    public PluginBuilder setManageable(boolean manageable) {
        this.manageable = manageable;
        return this;
    }

    @Nonnull
    @Override
    public final java.util.logging.Logger getLogger() {
        return logger;
    }

    @Override
    @Deprecated
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String name, @Nullable String id) {
        return null;
    }

    @Nullable
    @Override
    public BiomeProvider getDefaultBiomeProvider(String world, @Nullable String id) {
        return null;
    }

    @Override
    public String toString() {
        return getName() + " v" + getDescription().getVersion();
    }

    @Getter
    public static class DescriptionBuilder {

        private String name = getClass().getSimpleName();
        private String main = getClass().getName();
        private String version = "1.0";
        private String api = "1.13";

        public DescriptionBuilder(String name, String version, String api, String main) {
            this.name = name;
            this.version = version;
            this.api = api;
            this.main = main;
        }

        public DescriptionBuilder(String name, String version, String api) {
            this.name = name;
            this.version = version;
            this.api = api;
        }

        public DescriptionBuilder(String name, String version) {
            this.name = name;
            this.version = version;
        }

        public DescriptionBuilder(String name) {
            this.name = name;
        }

        public DescriptionBuilder(Class<?> clazz) {
            this(clazz.getSimpleName(), "1.0", "1.13", clazz.getName());
        }

        public DescriptionBuilder() {
        }

        public DescriptionBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public DescriptionBuilder setMain(String main) {
            this.main = main;
            return this;
        }

        public DescriptionBuilder setVersion(String version) {
            this.version = version;
            return this;
        }

        public DescriptionBuilder setApi(String api) {
            this.api = api;
            return this;
        }

        public PluginDescriptionFile build() {
            PluginDescriptionFile description = new PluginDescriptionFile(getName(), getVersion(), getMain());
            Reflection.Field.set(description, "apiVersion", getApi());
            return description;
        }
    }
}
