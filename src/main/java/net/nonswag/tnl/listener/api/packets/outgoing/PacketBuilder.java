package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.packets.PacketSendListener;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import java.util.List;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PacketBuilder implements OutgoingPacket {

    public abstract <P> P build();

    public void send(PacketSendListener after, TNLPlayer... players) {
        for (TNLPlayer player : players) player.pipeline().sendPacket(build(), after);
    }

    public void send(TNLPlayer... players) {
        send(player -> {
        }, players);
    }

    public void send(List<TNLPlayer> players, PacketSendListener after) {
        players.forEach(player -> send(after, player));
    }

    public void send(List<TNLPlayer> players) {
        send(players, player -> {
        });
    }

    public void broadcast(Predicate<TNLPlayer> condition, PacketSendListener after) {
        for (TNLPlayer all : Listener.getOnlinePlayers()) if (condition.test(all)) send(after, all);
    }

    public void broadcast(Predicate<TNLPlayer> condition) {
        broadcast(condition, player -> {
        });
    }

    public void broadcast(PacketSendListener after) {
        broadcast(player -> true, after);
    }

    public void broadcast() {
        broadcast(player -> true, player -> {
        });
    }

    public static <P> PacketBuilder of(P packet) {
        return Mapping.get().packetManager().outgoing().map(packet);
    }
}
