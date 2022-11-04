package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ForgetLevelChunkPacket extends PacketBuilder {

    private int x, z;

    public static ForgetLevelChunkPacket create(int x, int z) {
        return Mapping.get().packetManager().outgoing().forgetLevelChunkPacket(x, z);
    }
}
