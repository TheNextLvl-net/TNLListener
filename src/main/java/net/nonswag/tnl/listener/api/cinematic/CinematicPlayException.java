package net.nonswag.tnl.listener.api.cinematic;

import net.nonswag.tnl.listener.api.player.manager.CinematicManger;

import javax.annotation.Nonnull;

public class CinematicPlayException extends CinematicException {

    public CinematicPlayException(@Nonnull Recording recording, @Nonnull CinematicManger cinematicManger, @Nonnull String message) {
        super(recording, cinematicManger, message);
    }

    public CinematicPlayException(@Nonnull Recording recording, @Nonnull CinematicManger cinematicManger) {
        super(recording, cinematicManger);
    }
}
