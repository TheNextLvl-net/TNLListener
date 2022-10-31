package net.nonswag.tnl.listener.api.packets.incoming;

import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

public abstract class PacketBuilder implements IncomingPacket {

    protected PacketBuilder() {
    }

    @Nonnull
    public abstract <P> P build();

    @Nonnull
    public static <P> PacketBuilder of(@Nonnull P packet) {
        return Mapping.get().packetManager().incoming().map(packet);
    }
}
