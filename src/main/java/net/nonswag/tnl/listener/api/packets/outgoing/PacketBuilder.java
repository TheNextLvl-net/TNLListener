package net.nonswag.tnl.listener.api.packets.outgoing;

import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public abstract class PacketBuilder implements OutgoingPacket {

    protected PacketBuilder() {
    }

    @Nonnull
    public abstract <P> P build();

    public void send(@Nonnull TNLPlayer... players) {
        for (TNLPlayer player : players) player.pipeline().sendPacket(this.build());
    }

    public void send(@Nonnull List<TNLPlayer> players) {
        players.forEach(this::send);
    }

    public void broadcast(@Nonnull Predicate<TNLPlayer> condition) {
        for (TNLPlayer all : Listener.getOnlinePlayers()) if (condition.test(all)) send(all);
    }

    public void broadcast() {
        broadcast(player -> true);
    }

    @Nonnull
    public static <P> PacketBuilder of(@Nonnull P packet) {
        return new PacketBuilder() {
            @Nonnull
            @Override
            public P build() {
                return packet;
            }

            @Override
            public void send(@Nonnull TNLPlayer... players) {
                for (TNLPlayer player : players) player.pipeline().sendPacket(build());
            }
        };
    }
}
