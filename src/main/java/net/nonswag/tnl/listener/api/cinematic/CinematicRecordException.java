package net.nonswag.tnl.listener.api.cinematic;

import net.nonswag.tnl.listener.api.player.manager.CinematicManger;

public class CinematicRecordException extends CinematicException {

    public CinematicRecordException(Recording recording, CinematicManger cinematicManger, String message) {
        super(recording, cinematicManger, message);
    }

    public CinematicRecordException(Recording recording, CinematicManger cinematicManger) {
        super(recording, cinematicManger);
    }
}
