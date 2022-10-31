package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetCreativeModeSlotPacket extends PacketBuilder {
    @Nonnull
    private TNLItem item;
    private int slot;

    protected SetCreativeModeSlotPacket(int slot, @Nonnull TNLItem item) {
        this.slot = slot;
        this.item = item;
    }

    @Nonnull
    public static SetCreativeModeSlotPacket create(int slot, @Nonnull TNLItem item) {
        return Mapping.get().packetManager().incoming().setCreativeModeSlotPacket(slot, item);
    }
}
