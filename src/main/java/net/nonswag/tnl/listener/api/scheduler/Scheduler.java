package net.nonswag.tnl.listener.api.scheduler;

import net.nonswag.core.api.object.Condition;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;

public interface Scheduler {

    @Nonnull
    default Thread async(@Nonnull Runnable runnable) {
        return async(() -> true, runnable);
    }

    @Nonnull
    default Thread async(@Nonnull Runnable runnable, long millis) {
        return async(() -> true, runnable, millis);
    }

    @Nonnull
    Thread async(@Nonnull Condition condition, @Nonnull Runnable runnable);

    @Nonnull
    Thread async(@Nonnull Condition condition, @Nonnull Runnable runnable, long millis);

    default void sync(@Nonnull Runnable runnable) {
        sync(() -> true, runnable);
    }

    @Nonnull
    default BukkitTask sync(@Nonnull Runnable runnable, long ticks) {
        return sync(() -> true, runnable, ticks);
    }

    void sync(@Nonnull Condition condition, @Nonnull Runnable runnable);

    @Nonnull
    BukkitTask sync(@Nonnull Condition condition, @Nonnull Runnable runnable, long ticks);
}
