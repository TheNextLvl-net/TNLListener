package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class EntityAttachPacket extends PacketBuilder {

    private int holderId;
    private int leashedId;

    protected EntityAttachPacket(int holderId, int leashedId) {
        this.holderId = holderId;
        this.leashedId = leashedId;
    }

    @Nonnull
    public static EntityAttachPacket create(int holderId, int leashedId) {
        return Mapping.get().packetManager().outgoing().entityAttachPacket(holderId, leashedId);
    }

    @Nonnull
    public static EntityAttachPacket create(int holderId) {
        return create(holderId, 0);
    }

    @Nonnull
    public static EntityAttachPacket create(@Nonnull Entity holder, @Nonnull Entity leashed) {
        return create(holder.getEntityId(), leashed.getEntityId());
    }

    @Nonnull
    public static EntityAttachPacket create(@Nonnull Entity holder) {
        return create(holder.getEntityId());
    }
}