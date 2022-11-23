package net.nonswag.tnl.listener.events.mods.labymod;

import net.nonswag.tnl.listener.api.mods.ModMessage;
import net.nonswag.tnl.listener.api.mods.labymod.LabyPlayer;
import net.nonswag.tnl.listener.events.mods.ModPlayerMessageEvent;

public class LabyPlayerMessageEvent extends ModPlayerMessageEvent<LabyPlayer> {

    public LabyPlayerMessageEvent(LabyPlayer modPlayer, ModMessage message) {
        super(modPlayer, message);
    }

    @Override
    public String getNamespace() {
        return "labymod3";
    }
}
