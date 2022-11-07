package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SystemChatPacket extends PacketBuilder {
    private Component message;
    private boolean overlay;

    public static SystemChatPacket create(Component message, boolean overlay) {
        return Mapping.get().packetManager().outgoing().systemChatPacket(message, overlay);
    }
}
