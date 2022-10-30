package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

@Getter
public abstract class ItemTakePacket extends PacketBuilder {

    private int itemId, collectorId, amount;

    protected ItemTakePacket(int itemId, int collectorId, int amount) {
        this.itemId = itemId;
        this.collectorId = collectorId;
        this.amount = amount;
    }

    @Nonnull
    public ItemTakePacket setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    @Nonnull
    public ItemTakePacket setCollectorId(int collectorId) {
        this.collectorId = collectorId;
        return this;
    }

    @Nonnull
    public ItemTakePacket setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    @Nonnull
    public static ItemTakePacket create(@Nonnull Item item, @Nonnull Player player, int amount) {
        return create(item.getEntityId(), player.getEntityId(), amount);
    }

    @Nonnull
    public static ItemTakePacket create(int itemId, int collectorId, int amount) {
        return Mapping.get().packets().itemTakePacket(itemId, collectorId, amount);
    }
}
