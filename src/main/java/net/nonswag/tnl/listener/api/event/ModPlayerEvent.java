package net.nonswag.tnl.listener.api.event;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mods.ModPlayer;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class ModPlayerEvent extends PlayerEvent {

    @Getter
    @Nonnull
    private final ModPlayer modPlayer;

    protected ModPlayerEvent(@Nonnull ModPlayer modPlayer) {
        super(modPlayer.getPlayer());
        this.modPlayer = modPlayer;
    }

    @Override
    public String toString() {
        return "ModPlayerEvent{" +
                "modPlayer=" + modPlayer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ModPlayerEvent that = (ModPlayerEvent) o;
        return modPlayer.equals(that.modPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), modPlayer);
    }
}
