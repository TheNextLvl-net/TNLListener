package net.nonswag.tnl.listener.events;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@Getter
public class PlayerDamageBlockEvent extends PlayerEvent {

    @Nonnull
    private final Block block;
    @Nonnull
    private final BlockDamageType blockDamageType;

    public PlayerDamageBlockEvent(@Nonnull TNLPlayer player, @Nonnull Block block, @Nonnull BlockDamageType blockDamageType) {
        super(player);
        this.block = block;
        this.blockDamageType = blockDamageType;
    }

    @Nonnull
    public ItemStack getItem() {
        return getPlayer().inventoryManager().getItemInMainHand();
    }

    public enum BlockDamageType {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        UNKNOWN;

        BlockDamageType() {
        }

        public boolean isUnknown() {
            return equals(UNKNOWN);
        }

        public boolean isInteraction() {
            return equals(ABORT_DESTROY_BLOCK) || equals(STOP_DESTROY_BLOCK) || equals(START_DESTROY_BLOCK);
        }

        public boolean isInteraction(boolean full) {
            return (!full || !equals(ABORT_DESTROY_BLOCK)) && isInteraction();
        }

        public boolean isItemAction() {
            return !(isUnknown() && isInteraction());
        }

        @Nonnull
        public static BlockDamageType fromString(@Nonnull String string) {
            try {
                return valueOf(string.toUpperCase());
            } catch (Exception ignored) {
                return UNKNOWN;
            }
        }

        @Nonnull
        public static BlockDamageType fromObject(@Nonnull Object object) {
            return fromString(object.toString());
        }
    }
}
