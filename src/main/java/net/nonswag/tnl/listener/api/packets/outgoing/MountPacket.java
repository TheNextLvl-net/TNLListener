package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
public abstract class MountPacket extends PacketBuilder {

    private int holderId;
    private int[] mounts;

    protected MountPacket(int holderId, int[] mounts) {
        this.holderId = holderId;
        this.mounts = mounts;
    }

    @Nonnull
    public MountPacket setHolderId(int holderId) {
        this.holderId = holderId;
        return this;
    }

    @Nonnull
    public MountPacket setMounts(int[] mounts) {
        this.mounts = mounts;
        return this;
    }

    @Nonnull
    public static MountPacket create(int holderId, int[] mounts) {
        return Mapping.get().packets().mountPacket(holderId, mounts);
    }

    @Nonnull
    public static MountPacket create(@Nonnull Entity holder, @Nonnull Entity... mounts) {
        int[] ids = new int[mounts.length];
        for (int i = 0; i < mounts.length; i++) ids[i] = mounts[i].getEntityId();
        return create(holder.getEntityId(), ids);
    }

    @Nonnull
    public static MountPacket create(@Nonnull Entity holder) {
        return create(holder, holder.getPassengers().toArray(new Entity[]{}));
    }
}
