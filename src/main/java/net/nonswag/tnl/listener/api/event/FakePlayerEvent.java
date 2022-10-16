package net.nonswag.tnl.listener.api.event;

import lombok.Getter;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class FakePlayerEvent extends PlayerEvent {

    @Getter
    @Nonnull
    private final FakePlayer fakePlayer;

    protected FakePlayerEvent(@Nonnull FakePlayer fakePlayer, @Nonnull TNLPlayer player) {
        super(player);
        this.fakePlayer = fakePlayer;
    }

    @Override
    public String toString() {
        return "FakePlayerEvent{" +
                "fakePlayer=" + fakePlayer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FakePlayerEvent that = (FakePlayerEvent) o;
        return fakePlayer.equals(that.fakePlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fakePlayer);
    }
}
