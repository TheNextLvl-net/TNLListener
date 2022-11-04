package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Player;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerInfoPacket extends PacketBuilder {

    private Player player;
    private Action action;

    public static PlayerInfoPacket create(Player player, Action action) {
        return Mapping.get().packetManager().outgoing().playerInfoPacket(player, action);
    }

    public static PlayerInfoPacket create(TNLEntityPlayer player, Action action) {
        return create(player.bukkit(), action);
    }

    public enum Action {
        ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER
    }
}
