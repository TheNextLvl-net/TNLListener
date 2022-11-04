package net.nonswag.tnl.listener.api.packets.listener;

import net.nonswag.tnl.listener.api.packets.incoming.IncomingPacket;

public interface PacketReader<P extends IncomingPacket> {
    void read(PacketEvent<P> event);
}
