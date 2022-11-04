package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

@Getter
@Setter
public abstract class CustomPayloadPacket extends PacketBuilder {
    private NamespacedKey channel;
    private byte[] data;

    protected CustomPayloadPacket(NamespacedKey channel, byte[] data) {
        this.channel = channel;
        this.data = data;
    }

    public static CustomPayloadPacket create(NamespacedKey channel, byte[] data) {
        return Mapping.get().packetManager().incoming().customPayloadPacket(channel, data);
    }
}
