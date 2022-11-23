package net.nonswag.tnl.listener.events;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

@Getter
public class PlayerDamageBlockEvent extends PlayerEvent {
    private final Block block;
    private final Type type;
    private final ItemStack item;

    public PlayerDamageBlockEvent(TNLPlayer player, Block block, Type type) {
        super(player);
        this.item = getPlayer().inventoryManager().getItemInMainHand();
        this.block = block;
        this.type = type;
    }

    public enum Type {
        START_DESTROY_BLOCK, ABORT_DESTROY_BLOCK, STOP_DESTROY_BLOCK, UNKNOWN;

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
    }
}
