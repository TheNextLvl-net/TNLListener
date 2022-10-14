package net.nonswag.tnl.listener.events.mods;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.ModPlayerEvent;
import net.nonswag.tnl.listener.api.mods.ModMessage;
import net.nonswag.tnl.listener.api.mods.ModPlayer;

import javax.annotation.Nonnull;

@Getter
public abstract class ModPlayerMessageEvent<P extends ModPlayer> extends ModPlayerEvent {

    @Nonnull
    private final ModMessage message;

    protected ModPlayerMessageEvent(@Nonnull P modPlayer, @Nonnull ModMessage message) {
        super(modPlayer);
        this.message = message;
    }

    @Nonnull
    public abstract String getNamespace();

    @Override
    public String toString() {
        return getNamespace() + ":" + getMessage().channel();
    }
}
