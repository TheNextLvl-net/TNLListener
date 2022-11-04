package net.nonswag.tnl.listener.api.scheduler;

import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface RepeatingTask {

    void repeatSynced(Consumer<BukkitTask> runnable, long period, long delay);

    default void repeatSynced(Consumer<BukkitTask> runnable, long period) {
        repeatSynced(runnable, period, 0);
    }

    BukkitTask repeatSynced(Runnable runnable, long period, long delay);

    default BukkitTask repeatSynced(Runnable runnable, long period) {
        return repeatSynced(runnable, period, 0);
    }

    void repeatAsync(Consumer<BukkitTask> runnable, long period, long delay);

    default void repeatAsync(Consumer<BukkitTask> runnable, long period) {
        repeatAsync(runnable, period, 0);
    }

    BukkitTask repeatAsync(Runnable runnable, long period, long delay);

    default BukkitTask repeatAsync(Runnable runnable, long period) {
        return repeatAsync(runnable, period, 0);
    }
}
