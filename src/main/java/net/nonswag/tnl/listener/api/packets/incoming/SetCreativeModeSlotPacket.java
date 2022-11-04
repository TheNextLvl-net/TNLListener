package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class SetCreativeModeSlotPacket extends PacketBuilder {
    private TNLItem item;
    private int slot;

    protected SetCreativeModeSlotPacket(int slot, TNLItem item) {
        this.slot = slot;
        this.item = item;
    }

    public static SetCreativeModeSlotPacket create(int slot, TNLItem item) {
        return Mapping.get().packetManager().incoming().setCreativeModeSlotPacket(slot, item);
    }
}
