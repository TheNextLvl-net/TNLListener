package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CustomPayloadPacket extends PacketBuilder {

    private String channel;
    private byte[][] bytes;

    public static CustomPayloadPacket create(String channel, byte[]... bytes) {
        return Mapping.get().packetManager().outgoing().customPayloadPacket(channel, bytes);
    }
}
