package net.nonswag.tnl.listener.api.packets.listener;

import net.nonswag.tnl.listener.api.packets.incoming.IncomingPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import java.util.concurrent.atomic.AtomicBoolean;

public interface PacketReader<P extends IncomingPacket> {
    void read(TNLPlayer player, P packet, AtomicBoolean cancelled);
}
