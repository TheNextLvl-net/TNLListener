package net.nonswag.tnl.listener.api.packets.listener;

import net.nonswag.tnl.listener.api.packets.outgoing.OutgoingPacket;

public interface PacketWriter<P extends OutgoingPacket> {
    void write(PacketEvent<P> event);
}
