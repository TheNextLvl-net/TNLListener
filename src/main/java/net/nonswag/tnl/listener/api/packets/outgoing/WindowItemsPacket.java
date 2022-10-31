package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

@Getter
@Setter
public abstract class WindowItemsPacket extends PacketBuilder {

    @Nonnull
    private List<ItemStack> items;
    private int windowId;

    protected WindowItemsPacket(int windowId, @Nonnull List<ItemStack> items) {
        this.windowId = windowId;
        this.items = items;
    }

    @Nonnull
    public static WindowItemsPacket create(int windowId, @Nonnull List<ItemStack> items) {
        return Mapping.get().packetManager().outgoing().windowItemsPacket(windowId, items);
    }

    @Nonnull
    public static WindowItemsPacket create(@Nonnull List<ItemStack> items) {
        return create(1, items);
    }
}
