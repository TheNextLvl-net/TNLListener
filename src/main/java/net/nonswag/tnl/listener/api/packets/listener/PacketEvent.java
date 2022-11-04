package net.nonswag.tnl.listener.api.packets.listener;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.packets.MappedPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.event.Cancellable;

@Getter
@Setter
public class PacketEvent<P extends MappedPacket> implements Cancellable {

    private final TNLPlayer player;
    private final P packet;
    private boolean cancelled;

    public PacketEvent(TNLPlayer player, P packet) {
        this.player = player;
        this.packet = packet;
    }
}
