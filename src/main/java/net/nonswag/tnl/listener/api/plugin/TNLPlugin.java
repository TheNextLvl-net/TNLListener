package net.nonswag.tnl.listener.api.plugin;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.nonswag.core.api.object.Condition;
import net.nonswag.tnl.listener.api.command.CommandManager;
import net.nonswag.tnl.listener.api.event.EventManager;
import net.nonswag.tnl.listener.api.registrations.RegistrationManager;
import net.nonswag.tnl.listener.api.scheduler.Task;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.function.Consumer;

@Getter
@Accessors(makeFinal = true)
public abstract class TNLPlugin extends JavaPlugin implements CombinedPlugin {
    private static final Logger logger = LoggerFactory.getLogger(TNLPlugin.class);
    private final EventManager eventManager = new EventManager(this);
    private final CommandManager commandManager = new CommandManager(this);
    private final RegistrationManager registrationManager = new RegistrationManager(this);

    @Override
    public final void onLoad() {
        try {
            load();
        } catch (Throwable t) {
            logger.error("There was an error while loading plugin <'" + getName() + "'>", t);
        }
    }

    @Override
    public final void onEnable() {
        try {
            if (!isEnabled()) throw new IllegalStateException("called <'onEnable'> before <'setEnabled(true)'>");
            enable();
        } catch (Throwable t) {
            logger.error("There was an error while enabling plugin <'" + getName() + "'>", t);
        }
    }

    @Override
    public final void onDisable() {
        try {
            if (isEnabled()) throw new IllegalStateException("called <'onDisable'> before <'setEnabled(false)'>");
            try {
                disable();
            } finally {
                getRegistrationManager().unregisterAll();
                getCommandManager().unregisterAllCommands();
                getEventManager().unregisterAll();
            }
        } catch (Throwable t) {
            logger.error("There was an error while disabling plugin <'" + getName() + "'>", t);
        }
    }

    @Nonnull
    @Override
    public File getFile() {
        return super.getFile();
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
}
