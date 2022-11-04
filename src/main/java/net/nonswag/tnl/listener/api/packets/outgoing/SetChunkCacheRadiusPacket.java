package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetChunkCacheRadiusPacket extends PacketBuilder {

    private int radius;

    protected SetChunkCacheRadiusPacket(int radius) {
        this.radius = radius;
    }

    @Nonnull
    public static SetChunkCacheRadiusPacket create(int radius) {
        return Mapping.get().packetManager().outgoing().setChunkCacheRadiusPacket(radius);
    }
}
