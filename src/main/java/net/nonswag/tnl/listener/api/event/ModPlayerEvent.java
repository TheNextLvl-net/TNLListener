package net.nonswag.tnl.listener.api.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.nonswag.tnl.listener.api.mods.ModPlayer;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class ModPlayerEvent<P extends ModPlayer> extends PlayerEvent {
    private final P modPlayer;

    protected ModPlayerEvent(P modPlayer) {
        super(modPlayer.getPlayer());
        this.modPlayer = modPlayer;
    }
}
