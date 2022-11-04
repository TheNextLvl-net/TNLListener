package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.api.player.TNLPlayer;

public abstract class Manager {

    protected Manager() {
    }

    public abstract TNLPlayer getPlayer();
}
