package net.nonswag.tnl.listener.api.item;

import org.bukkit.inventory.EquipmentSlot;

public enum SlotType {
    MAIN_HAND, OFF_HAND, BOOTS, LEGGINGS, CHESTPLATE, HELMET;

    public EquipmentSlot bukkit() {
        return switch (this) {
            case MAIN_HAND -> EquipmentSlot.HAND;
            case OFF_HAND -> EquipmentSlot.OFF_HAND;
            case BOOTS -> EquipmentSlot.FEET;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            case HELMET -> EquipmentSlot.HEAD;
        };
    }
}
