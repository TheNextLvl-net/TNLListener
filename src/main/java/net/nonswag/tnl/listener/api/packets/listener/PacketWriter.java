package net.nonswag.tnl.listener.api.packets.listener;

import net.nonswag.tnl.listener.api.packets.outgoing.OutgoingPacket;

import javax.annotation.Nonnull;

public interface PacketWriter<P extends OutgoingPacket> {
    void write(@Nonnull PacketEvent<P> event);
}
