package net.nonswag.tnl.holograms.api.event;

import net.nonswag.tnl.holograms.api.Hologram;
import net.nonswag.tnl.listener.api.event.TNLEvent;

import javax.annotation.Nonnull;

public abstract class HologramTNLEvent extends TNLEvent {

    @Nonnull
    private final Hologram hologram;

    public HologramTNLEvent(@Nonnull Hologram hologram) {
        this.hologram = hologram;
    }

    @Nonnull
    public Hologram getHologram() {
        return hologram;
    }
}
