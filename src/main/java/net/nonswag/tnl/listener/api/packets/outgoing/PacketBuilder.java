package net.nonswag.tnl.listener.api.packets.outgoing;

import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.packets.PacketSendListener;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public abstract class PacketBuilder implements OutgoingPacket {

    protected PacketBuilder() {
    }

    @Nonnull
    public abstract <P> P build();

    public void send(@Nonnull PacketSendListener after, @Nonnull TNLPlayer... players) {
        for (TNLPlayer player : players) player.pipeline().sendPacket(build(), after);
    }

    public void send(@Nonnull TNLPlayer... players) {
        send(player -> {
        }, players);
    }

    public void send(@Nonnull List<TNLPlayer> players, @Nonnull PacketSendListener after) {
        players.forEach(player -> send(after, player));
    }

    public void send(@Nonnull List<TNLPlayer> players) {
        send(players, player -> {
        });
    }

    public void broadcast(@Nonnull Predicate<TNLPlayer> condition, @Nonnull PacketSendListener after) {
        for (TNLPlayer all : Listener.getOnlinePlayers()) if (condition.test(all)) send(after, all);
    }

    public void broadcast(@Nonnull Predicate<TNLPlayer> condition) {
        broadcast(condition, player -> {
        });
    }

    public void broadcast(@Nonnull PacketSendListener after) {
        broadcast(player -> true, after);
    }

    public void broadcast() {
        broadcast(player -> true, player -> {
        });
    }

    @Nonnull
    public static <P> PacketBuilder of(@Nonnull P packet) {
        return Mapping.get().packetManager().outgoing().map(packet);
    }
}
