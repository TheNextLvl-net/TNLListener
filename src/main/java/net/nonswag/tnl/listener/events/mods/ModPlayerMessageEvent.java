package net.nonswag.tnl.listener.events.mods;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.ModPlayerEvent;
import net.nonswag.tnl.listener.api.mods.ModMessage;
import net.nonswag.tnl.listener.api.mods.ModPlayer;

@Getter
public abstract class ModPlayerMessageEvent<P extends ModPlayer> extends ModPlayerEvent<P> {
    private final ModMessage message;

    protected ModPlayerMessageEvent(P modPlayer, ModMessage message) {
        super(modPlayer);
        this.message = message;
    }

    public abstract String getNamespace();

    @Override
    public String toString() {
        return getNamespace() + ":" + getMessage().channel();
    }
}
