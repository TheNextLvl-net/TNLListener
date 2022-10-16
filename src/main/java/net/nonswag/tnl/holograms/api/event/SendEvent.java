package net.nonswag.tnl.holograms.api.event;

import lombok.Getter;
import net.nonswag.tnl.holograms.api.Hologram;
import net.nonswag.tnl.listener.api.entity.TNLArmorStand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;

@Getter
public class SendEvent extends PlayerHologramEvent {

    @Nonnull
    private final TNLArmorStand[][] armorStands;

    public SendEvent(@Nonnull Hologram hologram, @Nonnull TNLPlayer player, @Nonnull TNLArmorStand[][] armorStands) {
        super(hologram, player);
        this.armorStands = armorStands;
    }
}
