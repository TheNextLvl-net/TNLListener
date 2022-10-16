package net.nonswag.tnl.listener.api.plugin;

import net.nonswag.tnl.listener.api.command.CommandManager;
import net.nonswag.tnl.listener.api.event.EventManager;
import net.nonswag.tnl.listener.api.scheduler.RepeatingTask;
import net.nonswag.tnl.listener.api.scheduler.Scheduler;

import javax.annotation.Nonnull;

public interface CombinedPlugin extends Scheduler, RepeatingTask {

    @Nonnull
    EventManager getEventManager();

    @Nonnull
    CommandManager getCommandManager();

    default void load() {
    }

    default void enable() {
    }

    default void disable() {
    }

    default void startupFinished() {
    }
}
