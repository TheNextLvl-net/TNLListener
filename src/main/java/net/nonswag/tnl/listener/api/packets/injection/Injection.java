package net.nonswag.tnl.listener.api.packets.injection;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.event.Cancellable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

@Getter
public abstract class Injection<P> implements Cancellable {

    @Setter
    private boolean cancelled = false;
    @Nullable
    private After after;
    @Nonnull
    private final Class<P> packetClass;

    public Injection(@Nonnull Class<P> packetClass) {
        this.packetClass = packetClass;
    }

    public abstract boolean run(@Nonnull TNLPlayer player, @Nonnull P packet);

    @Nonnull
    public Injection<P> after(@Nullable After after) {
        this.after = after;
        return this;
    }

    public void uninject(@Nonnull TNLPlayer player) {
        player.uninject(this);
    }

    public void handle(@Nonnull Throwable throwable) {
        Logger.error.println(throwable);
    }

    public interface After {
        void run(@Nonnull TNLPlayer player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Injection<?> injection = (Injection<?>) o;
        return cancelled == injection.cancelled && Objects.equals(after, injection.after) && packetClass.equals(injection.packetClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cancelled, after, packetClass);
    }

    @Override
    public String toString() {
        return "Injection{" +
                "cancelled=" + cancelled +
                ", after=" + after +
                ", packetClass=" + packetClass.getName() +
                '}';
    }
}
