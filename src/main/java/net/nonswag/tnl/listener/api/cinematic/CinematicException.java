package net.nonswag.tnl.listener.api.cinematic;

import lombok.Getter;
import net.nonswag.core.api.errors.TNLRuntimeException;
import net.nonswag.tnl.listener.api.player.manager.CinematicManger;

import javax.annotation.Nonnull;

@Getter
public abstract class CinematicException extends TNLRuntimeException {

    @Nonnull
    private final Recording recording;
    @Nonnull
    private final CinematicManger cinematicManger;

    protected CinematicException(@Nonnull Recording recording, @Nonnull CinematicManger cinematicManger, @Nonnull String message) {
        super(message);
        this.recording = recording;
        this.cinematicManger = cinematicManger;
    }

    protected CinematicException(@Nonnull Recording recording, @Nonnull CinematicManger cinematicManger) {
        this.recording = recording;
        this.cinematicManger = cinematicManger;
    }
}
