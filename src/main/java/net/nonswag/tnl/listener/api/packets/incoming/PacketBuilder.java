package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PacketBuilder implements IncomingPacket {
    public abstract <P> P build();

    public static <P> PacketBuilder of(P packet) {
        return Mapping.get().packetManager().incoming().map(packet);
    }
}
