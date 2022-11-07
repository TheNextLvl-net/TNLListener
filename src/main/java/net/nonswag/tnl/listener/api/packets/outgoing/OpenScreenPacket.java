package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.math.Range;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.event.inventory.InventoryType;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class OpenScreenPacket extends PacketBuilder {
    private int containerId;
    private Type type;
    private Component title;

    public static OpenScreenPacket create(int containerId, Type type, Component title) {
        return Mapping.get().packetManager().outgoing().openScreenPacket(containerId, type, title);
    }

    public static OpenScreenPacket create(int containerId, int size, Component title) {
        return create(containerId, Type.chest(size), title);
    }

    public static OpenScreenPacket create(Type type, Component title) {
        return create(1, type, title);
    }

    public static OpenScreenPacket create(int size, Component title) {
        return create(Type.chest(size), title);
    }

    public enum Type {
        CHEST_9X1,
        CHEST_9X2,
        CHEST_9X3,
        CHEST_9X4,
        CHEST_9X5,
        CHEST_9X6,
        DISPENSER,
        ANVIL,
        BEACON,
        BLAST_FURNACE,
        BREWING_STAND,
        WORKBENCH,
        ENCHANTER,
        FURNACE,
        GRINDSTONE,
        HOPPER,
        LECTERN,
        LOOM,
        MERCHANT,
        SHULKER_BOX,
        SMITHING_TABLE,
        SMOKER,
        CARTOGRAPHY_TABLE,
        STONECUTTER;

        public static Type chest(@Range(from = 1, to = 6) int size) {
            int validate = Math.min(Math.max(size, 1), 6);
            if (validate == 1) return CHEST_9X1;
            else if (validate == 2) return CHEST_9X2;
            else if (validate == 3) return CHEST_9X3;
            else if (validate == 4) return CHEST_9X4;
            else if (validate == 5) return CHEST_9X5;
            else return CHEST_9X6;
        }

        public static Type valueOf(InventoryType type) {
            return switch (type) {
                case CHEST, ENDER_CHEST, BARREL -> CHEST_9X3;
                case DISPENSER, DROPPER -> DISPENSER;
                case FURNACE -> FURNACE;
                case WORKBENCH -> WORKBENCH;
                case ENCHANTING -> ENCHANTER;
                case BREWING -> BREWING_STAND;
                case MERCHANT -> MERCHANT;
                case ANVIL -> ANVIL;
                case SMITHING -> SMITHING_TABLE;
                case BEACON -> BEACON;
                case HOPPER -> HOPPER;
                case SHULKER_BOX -> SHULKER_BOX;
                case BLAST_FURNACE -> BLAST_FURNACE;
                case LECTERN -> LECTERN;
                case SMOKER -> SMOKER;
                case LOOM -> LOOM;
                case CARTOGRAPHY -> CARTOGRAPHY_TABLE;
                case GRINDSTONE -> GRINDSTONE;
                case STONECUTTER -> STONECUTTER;
                default -> throw new IllegalArgumentException();
            };
        }
    }
}
