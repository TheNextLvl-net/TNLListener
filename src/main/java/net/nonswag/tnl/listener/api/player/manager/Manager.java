package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;

public abstract class Manager {

    protected Manager() {
        define();
    }

    @Nonnull
    public abstract TNLPlayer getPlayer();

    public void define() {
    }

    public void override() {
    }
}
