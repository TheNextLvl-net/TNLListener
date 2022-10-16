package net.nonswag.tnl.listener.api.packets.listener;

import net.nonswag.tnl.listener.api.packets.PacketBuilder;

import javax.annotation.Nonnull;

public interface PacketReader {
    void read(@Nonnull PacketBuilder packet);
}
