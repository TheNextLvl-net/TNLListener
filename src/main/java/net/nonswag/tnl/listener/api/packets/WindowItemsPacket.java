package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

@Getter
public abstract class WindowItemsPacket extends PacketBuilder {

    private int windowId;
    @Nonnull
    private List<ItemStack> items;

    protected WindowItemsPacket(int windowId, @Nonnull List<ItemStack> items) {
        this.windowId = windowId;
        this.items = items;
    }

    @Nonnull
    public WindowItemsPacket setWindowId(int windowId) {
        this.windowId = windowId;
        return this;
    }

    @Nonnull
    public WindowItemsPacket setItems(@Nonnull List<ItemStack> items) {
        this.items = items;
        return this;
    }

    @Nonnull
    public static WindowItemsPacket create(int windowId, @Nonnull List<ItemStack> items) {
        return Mapping.get().packets().windowItemsPacket(windowId, items);
    }

    @Nonnull
    public static WindowItemsPacket create(@Nonnull List<ItemStack> items) {
        return create(1, items);
    }
}
