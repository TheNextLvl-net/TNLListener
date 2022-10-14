package net.nonswag.tnl.listener.api.event;

import lombok.Getter;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class PlayerEvent extends TNLEvent {

    @Getter
    @Nonnull
    private final TNLPlayer player;

    protected PlayerEvent(@Nonnull TNLPlayer player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "PlayerEvent{" +
                "player=" + player +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayerEvent that = (PlayerEvent) o;
        return player.equals(that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player);
    }
}
