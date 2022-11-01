package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetDisplayChatPreviewPacket extends PacketBuilder {

    private boolean enabled;

    protected SetDisplayChatPreviewPacket(boolean enabled) {
        this.enabled = enabled;
    }

    @Nonnull
    public static SetDisplayChatPreviewPacket create(boolean enabled) {
        return Mapping.get().packetManager().outgoing().setDisplayChatPreviewPacket(enabled);
    }

}
