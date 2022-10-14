package net.nonswag.tnl.listener.events.mods.labymod;

import net.nonswag.tnl.listener.api.mods.ModMessage;
import net.nonswag.tnl.listener.api.mods.labymod.LabyPlayer;
import net.nonswag.tnl.listener.events.mods.ModPlayerMessageEvent;

import javax.annotation.Nonnull;

public class LabyPlayerMessageEvent extends ModPlayerMessageEvent<LabyPlayer> {

    public LabyPlayerMessageEvent(@Nonnull LabyPlayer modPlayer, @Nonnull ModMessage message) {
        super(modPlayer, message);
    }

    @Nonnull
    @Override
    public String getNamespace() {
        return "labymod3";
    }

    @Nonnull
    @Override
    public LabyPlayer getModPlayer() {
        return (LabyPlayer) super.getModPlayer();
    }
}
