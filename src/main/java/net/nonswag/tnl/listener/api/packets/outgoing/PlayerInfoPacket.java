package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class PlayerInfoPacket extends PacketBuilder {

    @Nonnull
    private Player player;
    @Nonnull
    private Action action;

    protected PlayerInfoPacket(@Nonnull Player player, @Nonnull Action action) {
        this.player = player;
        this.action = action;
    }

    @Nonnull
    public static PlayerInfoPacket create(@Nonnull Player player, @Nonnull Action action) {
        return Mapping.get().packetManager().outgoing().playerInfoPacket(player, action);
    }

    @Nonnull
    public static PlayerInfoPacket create(@Nonnull TNLEntityPlayer player, @Nonnull Action action) {
        return create(player.bukkit(), action);
    }

    @Getter
    public enum Action {
        ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER
    }
}