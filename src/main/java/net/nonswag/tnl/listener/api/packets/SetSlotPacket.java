package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public abstract class SetSlotPacket extends PacketBuilder {

    @Nonnull
    private Inventory inventory;
    private int slot;
    @Nullable
    private ItemStack itemStack;

    protected SetSlotPacket(@Nonnull Inventory inventory, int slot, @Nullable ItemStack itemStack) {
        this.inventory = inventory;
        this.slot = slot;
        this.itemStack = itemStack;
    }

    @Nonnull
    public SetSlotPacket setInventory(@Nonnull Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    @Nonnull
    public SetSlotPacket setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    @Nonnull
    public SetSlotPacket setItemStack(@Nullable ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    @Nonnull
    public static SetSlotPacket create(@Nonnull Inventory inventory, int slot, @Nullable ItemStack itemStack) {
        return Mapping.get().packets().setSlotPacket(inventory, slot, itemStack);
    }

    public enum Inventory {
        COURSER(-1),
        PLAYER(0),
        TOP(1);

        private final int id;

        Inventory(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
