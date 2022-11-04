package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.nbt.NBTTag;
import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class TagQueryPacket extends PacketBuilder {
    private int transactionId;
    @Nullable
    private NBTTag tag;

    public static TagQueryPacket create(int transactionId, @Nullable NBTTag tag) {
        return Mapping.get().packetManager().outgoing().tagQueryPacket(transactionId, tag);
    }
}
