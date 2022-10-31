package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.math.Range;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.event.inventory.InventoryType;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class OpenWindowPacket extends PacketBuilder {

    private int windowId;
    @Nonnull
    private Type type;
    @Nonnull
    private String title;

    protected OpenWindowPacket(int windowId, @Nonnull Type type, @Nonnull String title) {
        this.windowId = windowId;
        this.type = type;
        this.title = title;
    }

    @Nonnull
    public static OpenWindowPacket create(int windowId, @Nonnull Type type, @Nonnull String title) {
        return Mapping.get().packetManager().outgoing().openWindowPacket(windowId, type, title);
    }

    @Nonnull
    public static OpenWindowPacket create(int windowId, int size, @Nonnull String title) {
        return create(windowId, Type.chest(size), title);
    }

    @Nonnull
    public static OpenWindowPacket create(@Nonnull Type type, @Nonnull String title) {
        return create(1, type, title);
    }

    @Nonnull
    public static OpenWindowPacket create(int size, @Nonnull String title) {
        return create(Type.chest(size), title);
    }

    @Getter
    public enum Type {
        CHEST_9X1(0),
        CHEST_9X2(1),
        CHEST_9X3(2),
        CHEST_9X4(3),
        CHEST_9X5(4),
        CHEST_9X6(5),
        DISPENSER(6),
        ANVIL(7),
        BEACON(8),
        BLAST_FURNACE(9),
        BREWING_STAND(10),
        WORKBENCH(11),
        ENCHANTER(12),
        FURNACE(13),
        GRINDSTONE(14),
        HOPPER(15),
        LECTERN(16),
        LOOM(17),
        MERCHANT(18),
        SHULKER_BOX(19),
        SMITHING_TABLE(20),
        SMOKER(21),
        CARTOGRAPHY_TABLE(22),
        STONECUTTER(23);

        private final int id;

        Type(int id) {
            this.id = id;
        }

        @Nonnull
        public static Type chest(@Range(from = 1, to = 6) int size) {
            int validate = Math.min(Math.max(size, 1), 6);
            if (validate == 1) return CHEST_9X1;
            else if (validate == 2) return CHEST_9X2;
            else if (validate == 3) return CHEST_9X3;
            else if (validate == 4) return CHEST_9X4;
            else if (validate == 5) return CHEST_9X5;
            else return CHEST_9X6;
        }

        @Nonnull
        public static Type valueOf(@Nonnull InventoryType type) {
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
