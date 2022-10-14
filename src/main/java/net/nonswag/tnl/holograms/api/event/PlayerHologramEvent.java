package net.nonswag.tnl.holograms.api.event;

import lombok.Getter;
import net.nonswag.tnl.holograms.api.Hologram;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;

@Getter
public abstract class PlayerHologramEvent extends PlayerEvent {

    @Nonnull
    private final Hologram hologram;

    public PlayerHologramEvent(@Nonnull Hologram hologram, @Nonnull TNLPlayer player) {
        super(player);
        this.hologram = hologram;
    }
}
