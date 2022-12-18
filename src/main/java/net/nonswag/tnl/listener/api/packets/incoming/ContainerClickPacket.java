package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.gui.Interaction;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ContainerClickPacket extends PacketBuilder {
    private int containerId, stateId, slot, buttonId;
    private ClickType clickType;
    private TNLItem item;
    private HashMap<Integer, TNLItem> changedSlots;

    public abstract Interaction.Type getInteractionType();

    public enum ClickType {
        PICKUP,
        QUICK_MOVE,
        SWAP,
        CLONE,
        THROW,
        QUICK_CRAFT,
        PICKUP_ALL
    }

    public static ContainerClickPacket create(int containerId, int stateId, int slot, int buttonId, ClickType clickType, TNLItem item, HashMap<Integer, TNLItem> changedSlots) {
        return Mapping.get().packetManager().incoming().containerClickPacket(containerId, stateId, slot, buttonId, clickType, item, changedSlots);
    }
}
