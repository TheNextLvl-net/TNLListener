package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetEntityLinkPacket extends PacketBuilder {
    private int leashHolderId, leashedEntityId;

    public static SetEntityLinkPacket create(int leashHolderId, int leashedEntityId) {
        return Mapping.get().packetManager().outgoing().setEntityLinkPacket(leashHolderId, leashedEntityId);
    }

    public static SetEntityLinkPacket create(Entity leashHolder, @Nullable Entity leasedEntity) {
        return create(leashHolder.getEntityId(), leasedEntity != null ? leasedEntity.getEntityId() : 0);
    }
}
