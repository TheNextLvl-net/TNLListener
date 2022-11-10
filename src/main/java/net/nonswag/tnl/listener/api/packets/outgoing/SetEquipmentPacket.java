package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetEquipmentPacket extends PacketBuilder {
    private int entityId;
    private HashMap<SlotType, TNLItem> equipment;

    public static SetEquipmentPacket create(int entityId, HashMap<SlotType, TNLItem> equipment) {
        return Mapping.get().packetManager().outgoing().setEquipmentPacket(entityId, equipment);
    }

    public static SetEquipmentPacket create(LivingEntity entity, HashMap<SlotType, TNLItem> equipment) {
        return create(entity.getEntityId(), equipment);
    }

    public static SetEquipmentPacket create(int entityId, SlotType slotType, TNLItem item) {
        HashMap<SlotType, TNLItem> map = new HashMap<>();
        map.put(slotType, item);
        return create(entityId, map);
    }

    public static SetEquipmentPacket create(LivingEntity entity) {
        EntityEquipment equipment = entity.getEquipment();
        HashMap<SlotType, TNLItem> map = new HashMap<>();
        if (equipment != null) for (SlotType slot : SlotType.values()) {
            map.put(slot, TNLItem.create(equipment.getItem(slot.bukkit())));
        }
        return create(entity.getEntityId(), map);
    }
}
