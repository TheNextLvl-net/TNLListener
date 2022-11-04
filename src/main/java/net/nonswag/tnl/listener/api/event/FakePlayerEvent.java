package net.nonswag.tnl.listener.api.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class FakePlayerEvent extends PlayerEvent {
    private final FakePlayer fakePlayer;

    protected FakePlayerEvent(FakePlayer fakePlayer, TNLPlayer player) {
        super(player);
        this.fakePlayer = fakePlayer;
    }
}
