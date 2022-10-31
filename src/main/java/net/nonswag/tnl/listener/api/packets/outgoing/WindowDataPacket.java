package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class WindowDataPacket extends PacketBuilder {

    private int windowId, property, value;

    protected WindowDataPacket(int windowId, int property, int value) {
        this.windowId = windowId;
        this.property = property;
        this.value = value;
    }

    @Nonnull
    public static WindowDataPacket create(int windowId, int property, int value) {
        return Mapping.get().packetManager().outgoing().windowDataPacket(windowId, property, value);
    }

    @Nonnull
    public static WindowDataPacket create(int property, int value) {
        return create(1, property, value);
    }

    public static class Furnace {
        public static final int FUEL_LEFT = 0;
        public static final int MAX_BURN_TIME = 1;
        public static final int PROGRESS = 2;
        public static final int MAX_PROGRESS = 3;
    }

    public static class Enchanter {
        public static final int EXP_TOP_ITEM = 0;
        public static final int EXP_MIDDLE_ITEM = 1;
        public static final int EXP_BOTTOM_ITEM = 2;

        public static final int ENCHANTMENT_SEED = 3;

        public static final int ID_TOP_ITEM = 4;
        public static final int ID_MIDDLE_ITEM = 5;
        public static final int ID_BOTTOM_ITEM = 6;

        public static final int LEVEL_TOP_ITEM = 7;
        public static final int LEVEL_MIDDLE_ITEM = 8;
        public static final int LEVEL_BOTTOM_ITEM = 9;

        public static class Enchantment {
            public static final int PROTECTION = 0;
            public static final int FIRE_PROTECTION = 1;
            public static final int FEATHER_FALLING = 2;
            public static final int BLAST_PROTECTION = 3;
            public static final int PROJECTILE_PROTECTION = 4;
            public static final int RESPIRATION = 5;
            public static final int AQUA_AFFINITY = 6;
            public static final int THORNS = 7;
            public static final int DEPTH_STRIDER = 8;
            public static final int FROST_WALKER = 9;
            public static final int BINDING_CURSE = 10;
            public static final int SOUL_SPEED = 11;
            public static final int SHARPNESS = 12;
            public static final int SMITE = 13;
            public static final int BANE_OF_ARTHROPODS = 14;
            public static final int KNOCKBACK = 15;
            public static final int FIRE_ASPECT = 16;
            public static final int LOOTING = 17;
            public static final int SWEEPING_EDGE = 18;
            public static final int EFFICIENCY = 19;
            public static final int SILK_TOUCH = 20;
            public static final int UNBREAKING = 21;
            public static final int FORTUNE = 22;
            public static final int POWER = 23;
            public static final int PUNCH = 24;
            public static final int FLAME = 25;
            public static final int INFINITY = 26;
            public static final int LUCK_OF_THE_SEA = 27;
            public static final int LURE = 28;
            public static final int LOYALTY = 29;
            public static final int IMPALING = 30;
            public static final int RIPTIDE = 31;
            public static final int CHANNELING = 32;
            public static final int MULTISHOT = 33;
            public static final int QUICK_CHARGE = 34;
            public static final int PIERCING = 35;
            public static final int MENDING = 36;
            public static final int CURSE_OF_VANISHING = 37;
        }
    }

    public static class Beacon {
        public static final int POWER_LEVEL = 0;
        public static final int FIRST_EFFECT = 1;
        public static final int SECOND_EFFECT = 2;
    }

    public static class Anvil {
        public static final int REPAIR_COST = 0;
    }

    public static class Brewing {
        public static final int BREW_TIME = 0;
        public static final int FUEL_TIME = 1;
    }

    public static class Stonecutter {
        public static final int SELECTED_RECIPE = 0;
    }

    public static class Loom {
        public static final int SELECTED_PATTERN = 0;
    }

    public static class Lectern {
        public static final int PAGE_NUMBER = 0;
    }
}
