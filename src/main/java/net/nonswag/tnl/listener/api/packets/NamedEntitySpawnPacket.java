package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.HumanEntity;

import javax.annotation.Nonnull;

@Getter
public abstract class NamedEntitySpawnPacket extends PacketBuilder {

    @Nonnull
    private HumanEntity human;

    protected NamedEntitySpawnPacket(@Nonnull HumanEntity human) {
        this.human = human;
    }

    @Nonnull
    public NamedEntitySpawnPacket setPlayer(@Nonnull HumanEntity human) {
        this.human = human;
        return this;
    }

    @Nonnull
    public static NamedEntitySpawnPacket create(@Nonnull HumanEntity human) {
        return Mapping.get().packets().namedEntitySpawnPacket(human);
    }

    @Nonnull
    public static NamedEntitySpawnPacket create(@Nonnull TNLEntityPlayer player) {
        return create(player.bukkit());
    }
}
