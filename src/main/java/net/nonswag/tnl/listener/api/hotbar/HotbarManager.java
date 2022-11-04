package net.nonswag.tnl.listener.api.hotbar;

import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.item.interactive.InteractiveItem;
import net.nonswag.tnl.listener.api.player.manager.Manager;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class HotbarManager extends Manager {

    protected final InteractiveItem[] hotbar = new InteractiveItem[9];

    protected HotbarManager() {
    }

    @Nullable
    public InteractiveItem getItem(int slot) {
        return hotbar[slot];
    }

    public HotbarManager setItem(int slot, InteractiveItem item) {
        hotbar[slot] = item;
        getPlayer().inventoryManager().getInventory().setItem(slot, item.getItem());
        return this;
    }

    public HotbarManager setItem(int slot, TNLItem item) {
        return setItem(slot, item.toInteractiveItem());
    }

    @Nullable
    public InteractiveItem getCurrentHotbarItem() {
        return getItem(getHeldItemSlot());
    }

    public int getHeldItemSlot() {
        return getPlayer().inventoryManager().getInventory().getHeldItemSlot();
    }
}
