package net.nonswag.tnl.listener.api.scheduler;

import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.core.api.object.Condition;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface Scheduler {

    default Thread async(Runnable runnable) {
        return async(() -> true, runnable);
    }

    default Thread async(Runnable runnable, long millis) {
        return async(() -> true, runnable, millis);
    }

    Thread async(Condition condition, Runnable runnable);

    Thread async(Condition condition, Runnable runnable, long millis);

    default void sync(Runnable runnable) {
        sync(() -> true, runnable);
    }

    default BukkitTask sync(Runnable runnable, long ticks) {
        return sync(() -> true, runnable, ticks);
    }

    void sync(Condition condition, Runnable runnable);

    BukkitTask sync(Condition condition, Runnable runnable, long ticks);
}
