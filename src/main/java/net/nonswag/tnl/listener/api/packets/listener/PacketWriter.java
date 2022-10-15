package net.nonswag.tnl.listener.api.packets.listener;

import net.nonswag.tnl.listener.api.packets.PacketBuilder;

import javax.annotation.Nonnull;

public interface PacketWriter {
    void write(@Nonnull PacketBuilder packet);
}
