package net.nonswag.tnl.listener.api.cinematic;

import lombok.Getter;
import net.nonswag.tnl.listener.api.player.manager.CinematicManger;

@Getter
public abstract class CinematicException extends RuntimeException {

    private final Recording recording;
    private final CinematicManger cinematicManger;

    protected CinematicException(Recording recording, CinematicManger cinematicManger, String message) {
        super(message);
        this.recording = recording;
        this.cinematicManger = cinematicManger;
    }

    protected CinematicException(Recording recording, CinematicManger cinematicManger) {
        this.recording = recording;
        this.cinematicManger = cinematicManger;
    }
}
