package net.nonswag.tnl.listener.events;

import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class PlayerInteractEvent extends PlayerEvent {

    @Nonnull
    private final Block clickedBlock;
    @Nonnull
    private final BlockFace direction;
    @Nonnull
    private final ItemStack heldItem;

    public PlayerInteractEvent(@Nonnull TNLPlayer player, @Nonnull Block clickedBlock, @Nonnull BlockFace direction, @Nonnull ItemStack heldItem) {
        super(player);
        this.clickedBlock = clickedBlock;
        this.direction = direction;
        this.heldItem = heldItem;
    }

    @Nonnull
    public Block getClickedBlock() {
        return clickedBlock;
    }

    @Nonnull
    public BlockFace getDirection() {
        return direction;
    }

    @Nonnull
    public ItemStack getHeldItem() {
        return heldItem;
    }
}
