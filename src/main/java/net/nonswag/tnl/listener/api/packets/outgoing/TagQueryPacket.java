package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.nbt.NBTTag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public abstract class TagQueryPacket extends PacketBuilder {

    @Nullable
    private NBTTag tag;
    private int transactionId;

    protected TagQueryPacket(int transactionId, @Nullable NBTTag tag) {
        this.transactionId = transactionId;
        this.tag = tag;
    }

    @Nonnull
    public static TagQueryPacket create(int transactionId, @Nullable NBTTag tag) {
        return Mapping.get().packetManager().outgoing().tagQueryPacket(transactionId, tag);
    }
}
