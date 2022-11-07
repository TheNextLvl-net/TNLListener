package net.nonswag.tnl.holograms.api.event;

import lombok.Getter;
import net.nonswag.tnl.holograms.api.Hologram;
import net.nonswag.tnl.listener.api.player.Hand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;

@Getter
public class InteractEvent extends PlayerHologramEvent {

    @Nonnull
    private final Hand.Side handSide;

    public InteractEvent(@Nonnull Hologram hologram, @Nonnull TNLPlayer player, @Nonnull Hand.Side handSide) {
        super(hologram, player);
        this.handSide = handSide;
    }
}
