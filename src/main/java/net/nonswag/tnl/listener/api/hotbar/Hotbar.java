package net.nonswag.tnl.listener.api.hotbar;

import lombok.Getter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.item.interactive.InteractiveItem;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public abstract class Hotbar {

    @Getter
    @Nonnull
    private final HashMap<Integer, InteractiveItem> items = new HashMap<>();

    protected Hotbar() {
    }

    @Nonnull
    protected abstract TNLPlayer getPlayer();

    @Nullable
    public InteractiveItem getSlot(int slot) {
        return getItems().get(slot);
    }

    @Nonnull
    public Hotbar setSlot(int slot, @Nonnull InteractiveItem item) {
        getItems().put(slot, item);
        getPlayer().inventoryManager().getInventory().setItem(slot, item.getItem());
        return this;
    }

    @Nonnull
    public Hotbar setSlot(int slot, @Nonnull TNLItem item) {
        return setSlot(slot, item.toInteractiveItem());
    }

    @Nullable
    public InteractiveItem getCurrentHotbarItem() {
        return getSlot(getHeldItemSlot());
    }

    public int getHeldItemSlot() {
        return getPlayer().inventoryManager().getInventory().getHeldItemSlot();
    }
}
