package net.nonswag.tnl.listener.api.packets.incoming;

import net.nonswag.tnl.listener.api.mapper.Mapping;

public abstract class PacketBuilder implements IncomingPacket {

    protected PacketBuilder() {
    }

    public abstract <P> P build();

    public static <P> PacketBuilder of(P packet) {
        return Mapping.get().packetManager().incoming().map(packet);
    }
}
