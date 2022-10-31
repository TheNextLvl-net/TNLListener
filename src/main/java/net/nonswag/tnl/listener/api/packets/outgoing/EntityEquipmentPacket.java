package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;

import javax.annotation.Nonnull;
import java.util.HashMap;

@Getter
@Setter
public abstract class EntityEquipmentPacket extends PacketBuilder {

    @Nonnull
    private HashMap<SlotType, TNLItem> equipment;
    private int entityId;

    protected EntityEquipmentPacket(int entityId, @Nonnull HashMap<SlotType, TNLItem> equipment) {
        this.entityId = entityId;
        this.equipment = equipment;
    }

    @Nonnull
    public static EntityEquipmentPacket create(int entityId, @Nonnull HashMap<SlotType, TNLItem> equipment) {
        return Mapping.get().packetManager().outgoing().entityEquipmentPacket(entityId, equipment);
    }

    @Nonnull
    public static EntityEquipmentPacket create(@Nonnull LivingEntity entity, @Nonnull HashMap<SlotType, TNLItem> equipment) {
        return create(entity.getEntityId(), equipment);
    }

    @Nonnull
    public static EntityEquipmentPacket create(int entityId, @Nonnull SlotType slotType, @Nonnull TNLItem item) {
        HashMap<SlotType, TNLItem> map = new HashMap<>();
        map.put(slotType, item);
        return create(entityId, map);
    }

    @Nonnull
    public static EntityEquipmentPacket create(@Nonnull LivingEntity entity) {
        EntityEquipment equipment = entity.getEquipment();
        HashMap<SlotType, TNLItem> map = new HashMap<>();
        if (equipment != null) for (SlotType slot : SlotType.values()) {
            map.put(slot, TNLItem.create(equipment.getItem(slot.bukkit())));
        }
        return create(entity.getEntityId(), map);
    }
}
