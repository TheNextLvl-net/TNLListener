package net.nonswag.tnl.listener.api.plugin;

import net.nonswag.tnl.listener.api.command.CommandManager;
import net.nonswag.tnl.listener.api.event.EventManager;
import net.nonswag.tnl.listener.api.scheduler.RepeatingTask;
import net.nonswag.tnl.listener.api.scheduler.Scheduler;

public interface CombinedPlugin extends Scheduler, RepeatingTask {

    EventManager getEventManager();

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
