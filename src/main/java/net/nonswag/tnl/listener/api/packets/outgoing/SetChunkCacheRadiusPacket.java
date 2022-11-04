package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetChunkCacheRadiusPacket extends PacketBuilder {
    private int radius;

    public static SetChunkCacheRadiusPacket create(int radius) {
        return Mapping.get().packetManager().outgoing().setChunkCacheRadiusPacket(radius);
    }
}
