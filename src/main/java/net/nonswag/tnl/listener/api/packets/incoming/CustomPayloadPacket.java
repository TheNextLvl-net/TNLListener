package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CustomPayloadPacket extends PacketBuilder {
    private NamespacedKey channel;
    private byte[] data;

    public static CustomPayloadPacket create(NamespacedKey channel, byte[] data) {
        return Mapping.get().packetManager().incoming().customPayloadPacket(channel, data);
    }
}
