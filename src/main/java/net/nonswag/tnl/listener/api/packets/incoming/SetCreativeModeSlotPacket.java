package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@Getter
@Setter
public class SetCreativeModeSlotPacket implements IncomingPacket {
    @Nonnull
    private ItemStack stack;
    private int slot;

    public SetCreativeModeSlotPacket(int slot, @Nonnull ItemStack stack) {
        this.slot = slot;
        this.stack = stack;
    }
}
