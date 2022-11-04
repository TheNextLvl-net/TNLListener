package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetDisplayChatPreviewPacket extends PacketBuilder {
    private boolean enabled;

    public static SetDisplayChatPreviewPacket create(boolean enabled) {
        return Mapping.get().packetManager().outgoing().setDisplayChatPreviewPacket(enabled);
    }
}
