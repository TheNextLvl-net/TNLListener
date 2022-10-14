package net.nonswag.tnl.listener.events.mods.labymod;

import net.nonswag.tnl.listener.api.mods.labymod.LabyPlayer;
import net.nonswag.tnl.listener.events.mods.ModPlayerJoinEvent;

import javax.annotation.Nonnull;

public class LabyPlayerJoinEvent extends ModPlayerJoinEvent {

    public LabyPlayerJoinEvent(@Nonnull LabyPlayer modPlayer) {
        super(modPlayer);
    }

    @Nonnull
    @Override
    public LabyPlayer getModPlayer() {
        return (LabyPlayer) super.getModPlayer();
    }
}
