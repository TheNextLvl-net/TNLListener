package net.nonswag.tnl.listener.events.mods.labymod;

import net.nonswag.tnl.listener.api.mods.labymod.LabyPlayer;
import net.nonswag.tnl.listener.events.mods.ModPlayerJoinEvent;

public class LabyPlayerJoinEvent extends ModPlayerJoinEvent<LabyPlayer> {
    public LabyPlayerJoinEvent(LabyPlayer player) {
        super(player);
    }
}
