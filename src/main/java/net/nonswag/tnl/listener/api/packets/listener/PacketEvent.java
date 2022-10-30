package net.nonswag.tnl.listener.api.packets.listener;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.packets.MappedPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.event.Cancellable;

import javax.annotation.Nonnull;

@Getter
@Setter
public class PacketEvent<P extends MappedPacket> implements Cancellable {

    @Nonnull
    private final TNLPlayer player;
    @Nonnull
    private final P packet;
    private boolean cancelled;

    public PacketEvent(@Nonnull TNLPlayer player, @Nonnull P packet) {
        this.player = player;
        this.packet = packet;
    }
}
