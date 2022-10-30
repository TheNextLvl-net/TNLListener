package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;

@Getter
@Setter
public class WindowClickPacket implements IncomingPacket {
    @Nonnull
    private ClickType clickType;
    @Nonnull
    private ItemStack itemStack;
    @Nonnull
    private HashMap<Integer, ItemStack> changedSlots;
    private int containerId;
    private int stateId;
    private int slot;
    private int buttonId;

    public WindowClickPacket(int containerId, int stateId, int slot, int buttonId, @Nonnull ClickType clickType,
                             @Nonnull ItemStack itemStack, @Nonnull HashMap<Integer, ItemStack> changedSlots) {
        this.containerId = containerId;
        this.stateId = stateId;
        this.slot = slot;
        this.buttonId = buttonId;
        this.clickType = clickType;
        this.itemStack = itemStack;
        this.changedSlots = changedSlots;
    }

    public enum ClickType {
        PICKUP,
        QUICK_MOVE,
        SWAP,
        CLONE,
        THROW,
        QUICK_CRAFT,
        PICKUP_ALL
    }
}
