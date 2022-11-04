package net.nonswag.tnl.listener.api.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.nonswag.tnl.listener.api.mods.ModPlayer;

@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class ModPlayerEvent extends PlayerEvent {

    @Getter
    private final ModPlayer modPlayer;

    protected ModPlayerEvent(ModPlayer modPlayer) {
        super(modPlayer.getPlayer());
        this.modPlayer = modPlayer;
    }
}
