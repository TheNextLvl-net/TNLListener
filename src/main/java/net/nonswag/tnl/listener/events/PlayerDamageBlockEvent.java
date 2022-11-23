package net.nonswag.tnl.listener.events;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.packets.incoming.PlayerActionPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class PlayerDamageBlockEvent extends PlayerEvent implements Cancellable {
    private final Block block;
    private final PlayerActionPacket.Action action;
    private final ItemStack item;
    private boolean cancelled;

    public PlayerDamageBlockEvent(TNLPlayer player, Block block, PlayerActionPacket.Action action) {
        super(player);
        this.item = getPlayer().inventoryManager().getItemInMainHand();
        this.block = block;
        this.action = action;
    }
}
