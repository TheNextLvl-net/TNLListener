package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.HumanEntity;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class NamedEntitySpawnPacket extends PacketBuilder {

    @Nonnull
    private HumanEntity human;

    protected NamedEntitySpawnPacket(@Nonnull HumanEntity human) {
        this.human = human;
    }

    @Nonnull
    public static NamedEntitySpawnPacket create(@Nonnull HumanEntity human) {
        return Mapping.get().packetManager().outgoing().namedEntitySpawnPacket(human);
    }

    @Nonnull
    public static NamedEntitySpawnPacket create(@Nonnull TNLEntityPlayer player) {
        return create(player.bukkit());
    }
}
