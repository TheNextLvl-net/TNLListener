package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.HumanEntity;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NamedEntitySpawnPacket extends PacketBuilder {

    private HumanEntity human;

    public static NamedEntitySpawnPacket create(HumanEntity human) {
        return Mapping.get().packetManager().outgoing().namedEntitySpawnPacket(human);
    }

    public static NamedEntitySpawnPacket create(TNLEntityPlayer player) {
        return create(player.bukkit());
    }
}
