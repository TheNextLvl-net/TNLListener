package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import java.util.HashMap;

@Getter
@Setter
public abstract class WindowClickPacket extends PacketBuilder {
    @Nonnull
    private ClickType clickType;
    @Nonnull
    private TNLItem item;
    @Nonnull
    private HashMap<Integer, TNLItem> changedSlots;
    private int containerId;
    private int stateId;
    private int slot;
    private int buttonId;

    protected WindowClickPacket(int containerId, int stateId, int slot, int buttonId, @Nonnull ClickType clickType,
                                @Nonnull TNLItem item, @Nonnull HashMap<Integer, TNLItem> changedSlots) {
        this.containerId = containerId;
        this.stateId = stateId;
        this.slot = slot;
        this.buttonId = buttonId;
        this.clickType = clickType;
        this.item = item;
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

    @Nonnull
    public static WindowClickPacket create(int containerId, int stateId, int slot, int buttonId, @Nonnull ClickType clickType, @Nonnull TNLItem item, @Nonnull HashMap<Integer, TNLItem> changedSlots) {
        return Mapping.get().packetManager().incoming().windowClickPacket(containerId, stateId, slot, buttonId, clickType, item, changedSlots);
    }
}
