package net.nonswag.tnl.listener.api.scheduler;

import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public interface RepeatingTask {

    void repeatSynced(@Nonnull Consumer<BukkitTask> runnable, long period, long delay);

    default void repeatSynced(@Nonnull Consumer<BukkitTask> runnable, long period) {
        repeatSynced(runnable, period, 0);
    }

    @Nonnull
    BukkitTask repeatSynced(@Nonnull Runnable runnable, long period, long delay);

    @Nonnull
    default BukkitTask repeatSynced(@Nonnull Runnable runnable, long period) {
        return repeatSynced(runnable, period, 0);
    }

    void repeatAsync(@Nonnull Consumer<BukkitTask> runnable, long period, long delay);

    default void repeatAsync(@Nonnull Consumer<BukkitTask> runnable, long period) {
        repeatAsync(runnable, period, 0);
    }

    @Nonnull
    BukkitTask repeatAsync(@Nonnull Runnable runnable, long period, long delay);

    @Nonnull
    default BukkitTask repeatAsync(@Nonnull Runnable runnable, long period) {
        return repeatAsync(runnable, period, 0);
    }
}
