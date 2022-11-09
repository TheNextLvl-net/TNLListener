package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetCreativeModeSlotPacket extends PacketBuilder {
    private int slot;
    private TNLItem item;

    public static SetCreativeModeSlotPacket create(int slot, TNLItem item) {
        return Mapping.get().packetManager().incoming().setCreativeModeSlotPacket(slot, item);
    }
}
