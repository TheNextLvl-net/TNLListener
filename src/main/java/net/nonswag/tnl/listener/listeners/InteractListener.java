package net.nonswag.tnl.listener.listeners;

import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.item.ItemType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.Hand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.events.CompostEvent;
import net.nonswag.tnl.listener.events.PlayerBottleFillEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class InteractListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onWaterBottleFill(@Nonnull CauldronLevelChangeEvent event) {
        TNLPlayer player = TNLPlayer.cast(event.getEntity());
        if (!event.getReason().equals(CauldronLevelChangeEvent.ChangeReason.BOTTLE_FILL) || player == null) return;
        ItemStack itemStack = player.inventoryManager().getInventory().getItemInMainHand();
        ItemStack itemStack1 = player.inventoryManager().getInventory().getItemInOffHand();
        Hand hand;
        if (itemStack.getType().equals(Material.GLASS_BOTTLE)) hand = Hand.MAIN_HAND;
        else if (itemStack1.getType().equals(Material.GLASS_BOTTLE)) hand = Hand.OFF_HAND;
        else return;
        var fillEvent = new PlayerBottleFillEvent(player, TNLItem.create(itemStack), event.getBlock(), hand);
        if (fillEvent.getHand().isMainHand()) {
            player.inventoryManager().getInventory().setItemInMainHand(fillEvent.getItemStack());
        } else player.inventoryManager().getInventory().setItemInOffHand(fillEvent.getItemStack());
        if (!fillEvent.call()) event.setCancelled(true);
        if (fillEvent.getReplacement() == null) return;
        var leftover = player.inventoryManager().getInventory().addItem(fillEvent.getReplacement());
        if (leftover.isEmpty()) return;
        Bootstrap.getInstance().sync(() -> leftover.values().forEach(item ->
                player.worldManager().getWorld().dropItemNaturally(player.worldManager().getLocation(), item)));
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(@Nonnull PlayerInteractEvent event) {
        TNLPlayer player = TNLPlayer.cast(event.getPlayer());
        Block block = event.getClickedBlock();
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || player.worldManager().isSneaking()) return;
        if (event.getItem() == null || block == null || !block.getType().equals(Material.COMPOSTER)) return;
        if (!ItemType.COMPOSTABLE.matches(event.getItem().getType())) return;
        if (!new CompostEvent(block, event.getItem()).call()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryMove(@Nonnull InventoryMoveItemEvent event) {
        if (!event.getSource().getType().equals(InventoryType.HOPPER)) return;
        Location location = event.getSource().getLocation();
        if (location == null) return;
        Block block = location.getBlock().getRelative(BlockFace.DOWN);
        if (!block.getType().equals(Material.COMPOSTER)) return;
        if (!new CompostEvent(block, event.getItem()).call()) event.setCancelled(true);
    }
}
