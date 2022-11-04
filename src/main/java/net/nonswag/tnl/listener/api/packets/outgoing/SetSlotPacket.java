package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetSlotPacket extends PacketBuilder {

    private Inventory inventory;
    private int slot;
    @Nullable
    private ItemStack itemStack;

    public static SetSlotPacket create(Inventory inventory, int slot, @Nullable ItemStack itemStack) {
        return Mapping.get().packetManager().outgoing().setSlotPacket(inventory, slot, itemStack);
    }

    @Getter
    @AllArgsConstructor
    public enum Inventory {
        COURSER(-1),
        PLAYER(0),
        TOP(1);

        private final int id;
    }
}
