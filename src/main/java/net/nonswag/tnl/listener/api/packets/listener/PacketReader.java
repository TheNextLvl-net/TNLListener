package net.nonswag.tnl.listener.api.packets.listener;

import net.nonswag.tnl.listener.api.packets.MappedPacket;

import javax.annotation.Nonnull;

public interface PacketReader<P extends MappedPacket> {
    void read(@Nonnull PacketEvent<P> event);
}
