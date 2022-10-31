package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public abstract class SetSlotPacket extends PacketBuilder {

    @Nonnull
    private Inventory inventory;
    @Nullable
    private ItemStack itemStack;
    private int slot;

    protected SetSlotPacket(@Nonnull Inventory inventory, int slot, @Nullable ItemStack itemStack) {
        this.inventory = inventory;
        this.slot = slot;
        this.itemStack = itemStack;
    }

    @Nonnull
    public static SetSlotPacket create(@Nonnull Inventory inventory, int slot, @Nullable ItemStack itemStack) {
        return Mapping.get().packetManager().outgoing().setSlotPacket(inventory, slot, itemStack);
    }

    @Getter
    public enum Inventory {
        COURSER(-1),
        PLAYER(0),
        TOP(1);

        private final int id;

        Inventory(int id) {
            this.id = id;
        }
    }
}
