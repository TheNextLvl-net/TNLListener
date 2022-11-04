package net.nonswag.tnl.listener.api.cinematic;

import net.nonswag.tnl.listener.api.player.manager.CinematicManger;

public class CinematicPlayException extends CinematicException {

    public CinematicPlayException(Recording recording, CinematicManger cinematicManger, String message) {
        super(recording, cinematicManger, message);
    }

    public CinematicPlayException(Recording recording, CinematicManger cinematicManger) {
        super(recording, cinematicManger);
    }
}
