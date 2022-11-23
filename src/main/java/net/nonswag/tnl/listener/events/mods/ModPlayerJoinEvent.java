package net.nonswag.tnl.listener.events.mods;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.tnl.listener.api.event.ModPlayerEvent;
import net.nonswag.tnl.listener.api.mods.ModPlayer;

@Getter
@Setter
@FieldsAreNullableByDefault
public abstract class ModPlayerJoinEvent<P extends ModPlayer> extends ModPlayerEvent<P> {
    private String disconnectReason;

    protected ModPlayerJoinEvent(P player) {
        super(player);
    }
}
