package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.HumanEntity;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AddPlayerPacket extends PacketBuilder {
    private int entityId;
    private UUID uniqueId;
    private Position position;

    public static AddPlayerPacket create(int entityId, UUID uniqueId, Position position) {
        return Mapping.get().packetManager().outgoing().addPlayerPacket(entityId, uniqueId, position);
    }

    public static AddPlayerPacket create(HumanEntity human) {
        return create(human.getEntityId(), human.getUniqueId(), new Position(human.getLocation()));
    }

    public static AddPlayerPacket create(TNLEntityPlayer player) {
        return create(player.bukkit());
    }
}
