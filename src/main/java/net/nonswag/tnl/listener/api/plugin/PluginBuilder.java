package net.nonswag.tnl.listener.api.plugin;

import lombok.Getter;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.object.Condition;
import net.nonswag.core.api.reflection.Reflection;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.command.CommandManager;
import net.nonswag.tnl.listener.api.event.EventManager;
import net.nonswag.tnl.listener.api.scheduler.Task;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public abstract class PluginBuilder extends PluginBase implements CombinedPlugin {

    @Nonnull
    private final PluginDescriptionFile description;
    @Nonnull
    private final PluginLogger logger;
    @Nullable
    private EventManager eventManager = null;
    @Nullable
    private CommandManager commandManager = null;
    @Nonnull
    private final Loader loader;
    @Nonnull
    private final Plugin owner;
    protected boolean enabled = false;
    @Getter
    private boolean manageable = false;

    protected PluginBuilder(@Nonnull PluginDescriptionFile description, @Nonnull Plugin owner) {
        this.description = description;
        this.owner = owner;
        setAuthors("NonSwag");
        setDescription("An official TNL-Production");
        setWebsite("https://www.thenextlvl.net");
        this.logger = new PluginLogger(this);
        this.loader = new Loader();
    }

    protected PluginBuilder(@Nonnull String name, @Nonnull String version, @Nonnull String main, @Nonnull Plugin owner) {
        this(new PluginDescriptionFile(name, version, main), owner);
    }

    protected PluginBuilder(@Nonnull String name, @Nonnull String version, @Nonnull Plugin owner) {
        this(new DescriptionBuilder(name, version).build(), owner);
    }

    protected PluginBuilder(@Nonnull String name, @Nonnull Plugin owner) {
        this(new DescriptionBuilder(name).build(), owner);
    }

    protected PluginBuilder(@Nonnull Class<?> clazz, @Nonnull Plugin owner) {
        this(new DescriptionBuilder(clazz).build(), owner);
    }

    protected final void setName(@Nonnull String name) {
        Reflection.Field.set(getDescription(), "name", name);
    }

    protected final void setAuthors(@Nonnull String... authors) {
        Reflection.Field.set(getDescription(), "authors", Arrays.asList(authors));
    }

    protected final void setAPIVersion(@Nonnull String apiVersion) {
        Reflection.Field.set(getDescription(), "apiVersion", apiVersion);
    }

    protected final void setDescription(@Nonnull String description) {
        Reflection.Field.set(getDescription(), "description", description);
    }

    protected final void setWebsite(@Nonnull String website) {
        Reflection.Field.set(getDescription(), "website", website);
    }

    protected final void setPrefix(@Nonnull String prefix) {
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
                HandlerList.unregisterAll(this);
            }
        } catch (Throwable t) {
            Logger.error.println("There was an error while disabling plugin <'" + getName() + "'>", t);
        }
    }

    @Nonnull
    public PluginBuilder register() {
        unregister();
        PluginHelper.getInstance().getPlugins().add(this);
        return this;
    }

    @Nonnull
    public PluginBuilder unregister() {
        PluginHelper.getInstance().getPlugins().removeIf(plugin -> plugin.getName().equals(getName()));
        return this;
    }

    public boolean isRegistered() {
        return PluginHelper.getInstance().getPlugins().contains(this);
    }

    @Nonnull
    public final EventManager getEventManager() {
        return eventManager == null ? eventManager = new EventManager(this) : eventManager;
    }

    @Nonnull
    public final CommandManager getCommandManager() {
        return commandManager == null ? commandManager = new CommandManager(this) : commandManager;
    }

    @Nonnull
    @Override
    public Thread async(@Nonnull Condition condition, @Nonnull Runnable runnable) {
        Thread thread = new Thread(() -> {
            if (condition.check()) runnable.run();
        });
        thread.start();
        return thread;
    }

    @Nonnull
    @Override
    public Thread async(@Nonnull Condition condition, @Nonnull Runnable runnable, long millis) {
        Thread thread = new Thread(() -> {
            if (Task.sleep(millis)) if (condition.check()) runnable.run();
        });
        thread.start();
        return thread;
    }

    @Override
    public void sync(@Nonnull Condition condition, @Nonnull Runnable runnable) {
        if (Bukkit.isPrimaryThread()) {
            if (condition.check()) runnable.run();
        } else Bukkit.getScheduler().runTask(this, () -> {
            if (condition.check()) runnable.run();
        });
    }

    @Nonnull
    @Override
    public BukkitTask sync(@Nonnull Condition condition, @Nonnull Runnable runnable, long ticks) {
        return Bukkit.getScheduler().runTaskLater(this, () -> {
            if (condition.check()) runnable.run();
        }, ticks);
    }

    @Nonnull
    @Override
    public BukkitTask repeatSynced(@Nonnull Runnable runnable, long period, long delay) {
        return Bukkit.getScheduler().runTaskTimer(this, runnable, delay, period);
    }

    @Override
    public void repeatSynced(@Nonnull Consumer<BukkitTask> runnable, long period, long delay) {
        Bukkit.getScheduler().runTaskTimer(this, runnable, delay, period);
    }

    @Override
    public void repeatAsync(@Nonnull Consumer<BukkitTask> runnable, long period, long delay) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, runnable, delay, period);
    }

    @Nonnull
    @Override
    public BukkitTask repeatAsync(@Nonnull Runnable runnable, long period, long delay) {
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
    public InputStream getResource(@Nonnull String s) {
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
    public void saveResource(@Nonnull String s, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reloadConfig() {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    public final Plugin getOwner() {
        return owner;
    }

    @Nonnull
    @Override
    public final Loader getPluginLoader() {
        return loader;
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

    @Nonnull
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
    public final boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public ChunkGenerator getDefaultWorldGenerator(@Nonnull String name, @Nullable String id) {
        return null;
    }

    @Override
    public String toString() {
        return getName() + " v" + getDescription().getVersion();
    }

    @Getter
    public static class DescriptionBuilder {

        @Nonnull
        private String name = getClass().getSimpleName();
        @Nonnull
        private String main = getClass().getName();
        @Nonnull
        private String version = "1.0";
        @Nonnull
        private String api = "1.13";

        public DescriptionBuilder(@Nonnull String name, @Nonnull String version, @Nonnull String api, @Nonnull String main) {
            this.name = name;
            this.version = version;
            this.api = api;
            this.main = main;
        }

        public DescriptionBuilder(@Nonnull String name, @Nonnull String version, @Nonnull String api) {
            this.name = name;
            this.version = version;
            this.api = api;
        }

        public DescriptionBuilder(@Nonnull String name, @Nonnull String version) {
            this.name = name;
            this.version = version;
        }

        public DescriptionBuilder(@Nonnull String name) {
            this.name = name;
        }

        public DescriptionBuilder(@Nonnull Class<?> clazz) {
            this(clazz.getSimpleName(), "1.0", "1.13", clazz.getName());
        }

        public DescriptionBuilder() {
        }

        @Nonnull
        public DescriptionBuilder setName(@Nonnull String name) {
            this.name = name;
            return this;
        }

        @Nonnull
        public DescriptionBuilder setMain(@Nonnull String main) {
            this.main = main;
            return this;
        }

        @Nonnull
        public DescriptionBuilder setVersion(@Nonnull String version) {
            this.version = version;
            return this;
        }

        @Nonnull
        public DescriptionBuilder setApi(@Nonnull String api) {
            this.api = api;
            return this;
        }

        @Nonnull
        public PluginDescriptionFile build() {
            PluginDescriptionFile description = new PluginDescriptionFile(getName(), getVersion(), getMain());
            Reflection.Field.set(description, "apiVersion", getApi());
            return description;
        }
    }

    public class Loader implements PluginLoader {

        @Nonnull
        @Override
        public PluginBuilder loadPlugin(@Nonnull File file) {
            return PluginBuilder.this;
        }

        @Nonnull
        @Override
        public PluginDescriptionFile getPluginDescription(@Nonnull File file) {
            return PluginBuilder.this.getDescription();
        }

        @Nonnull
        @Override
        public Pattern[] getPluginFileFilters() {
            return Bootstrap.getInstance().getPluginLoader().getPluginFileFilters();
        }

        @Nonnull
        @Override
        public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(@Nonnull Listener listener, @Nonnull Plugin plugin) {
            return Bootstrap.getInstance().getPluginLoader().createRegisteredListeners(listener, plugin);
        }

        @Override
        public void enablePlugin(@Nonnull Plugin plugin) {
            PluginBuilder.this.setEnabled(true);
        }

        @Override
        public void disablePlugin(@Nonnull Plugin plugin) {
            PluginBuilder.this.setEnabled(false);
        }
    }
}
