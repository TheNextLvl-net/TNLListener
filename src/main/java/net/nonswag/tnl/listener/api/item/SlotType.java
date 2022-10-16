package net.nonswag.tnl.listener.api.item;

import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.Nonnull;

public enum SlotType {
    MAIN_HAND,
    OFF_HAND,
    BOOTS,
    LEGGINGS,
    CHESTPLATE,
    HELMET;

    @Nonnull
    private static final SlotType[] legacySlots  = new SlotType[]{SlotType.HELMET, SlotType.CHESTPLATE, SlotType.LEGGINGS, SlotType.BOOTS};

    @Nonnull
    public EquipmentSlot bukkit() {
        if (equals(MAIN_HAND)) return EquipmentSlot.HAND;
        else if (equals(OFF_HAND)) return EquipmentSlot.OFF_HAND;
        else if (equals(BOOTS)) return EquipmentSlot.FEET;
        else if (equals(LEGGINGS)) return EquipmentSlot.LEGS;
        else if (equals(CHESTPLATE)) return EquipmentSlot.CHEST;
        else if (equals(HELMET)) return EquipmentSlot.HEAD;
        throw new UnsupportedOperationException("The filed <'" + name() + "'> is not supported in this version");
    }

    @Nonnull
    private static SlotType[] getLegacySlots() {
        return legacySlots;
    }

    @Nonnull
    public static SlotType[] values(boolean legacy) {
        return legacy ? getLegacySlots() : values();
    }
}
