package net.nonswag.tnl.listener.api.packets.listener;

import net.nonswag.tnl.listener.api.packets.incoming.IncomingPacket;

import javax.annotation.Nonnull;

public interface PacketReader<P extends IncomingPacket> {
    void read(@Nonnull PacketEvent<P> event);
}
