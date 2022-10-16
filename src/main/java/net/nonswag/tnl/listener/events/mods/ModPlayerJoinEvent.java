package net.nonswag.tnl.listener.events.mods;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.event.ModPlayerEvent;
import net.nonswag.tnl.listener.api.mods.ModPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public abstract class ModPlayerJoinEvent extends ModPlayerEvent {

    @Nullable
    private String disconnectReason;

    protected ModPlayerJoinEvent(@Nonnull ModPlayer modPlayer) {
        super(modPlayer);
    }
}
