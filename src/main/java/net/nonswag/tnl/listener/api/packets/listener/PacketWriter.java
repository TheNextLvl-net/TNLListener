package net.nonswag.tnl.listener.api.packets.listener;

import net.nonswag.tnl.listener.api.packets.outgoing.OutgoingPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import java.util.concurrent.atomic.AtomicBoolean;

public interface PacketWriter<P extends OutgoingPacket> {
    void write(TNLPlayer player, P packet, AtomicBoolean cancelled);
}
