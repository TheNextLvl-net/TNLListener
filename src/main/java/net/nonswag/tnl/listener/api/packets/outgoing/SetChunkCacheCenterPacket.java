package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetChunkCacheCenterPacket extends PacketBuilder {
    private int x, z;

    protected SetChunkCacheCenterPacket(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Nonnull
    public static SetChunkCacheCenterPacket create(int x, int z) {
        return Mapping.get().packetManager().outgoing().setChunkCacheCenterPacket(x, z);
    }
}
