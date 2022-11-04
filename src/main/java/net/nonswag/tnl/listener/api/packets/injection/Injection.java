package net.nonswag.tnl.listener.api.packets.injection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.event.Cancellable;

import javax.annotation.Nullable;

@Getter
@ToString
@Deprecated
@EqualsAndHashCode
public abstract class Injection<P> implements Cancellable {

    @Setter
    @Deprecated
    private boolean cancelled = false;
    @Nullable
    @Deprecated
    private After after;
    @Deprecated
    private final Class<P> packetClass;

    @Deprecated
    public Injection(Class<P> packetClass) {
        this.packetClass = packetClass;
    }

    @Deprecated
    public abstract boolean run(TNLPlayer player, P packet);

    @Deprecated
    public Injection<P> after(@Nullable After after) {
        this.after = after;
        return this;
    }

    @Deprecated
    public void uninject(TNLPlayer player) {
        player.uninject(this);
    }

    @Deprecated
    public void handle(Throwable throwable) {
        Logger.error.println(throwable);
    }

    @Deprecated
    public interface After {
        @Deprecated
        void run(TNLPlayer player);
    }
}
