package net.nonswag.tnl.listener.listeners;

import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.events.inventory.anvil.AnvilItemCombineEvent;
import net.nonswag.tnl.listener.events.inventory.anvil.AnvilRenameItemEvent;
import net.nonswag.tnl.listener.events.inventory.anvil.AnvilRepairItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.meta.Repairable;

import java.util.Objects;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        TNLPlayer player = TNLPlayer.cast(event.getWhoClicked());
        if (!(event.getClickedInventory() instanceof AnvilInventory anvil)) return;
        if (event.getSlot() != 2) return;
        TNLItem first = TNLItem.nullable(anvil.getItem(0));
        TNLItem second = TNLItem.nullable(anvil.getItem(1));
        TNLItem result = TNLItem.nullable(anvil.getItem(2));
        if (result == null) return;
        if (result.getItemMeta() instanceof Repairable repairable) {
            if (player.bukkit().getLevel() < anvil.getRepairCost()) return;
            AnvilRepairItemEvent repairEvent = new AnvilRepairItemEvent(player, anvil, result);
            if (!repairEvent.call()) event.setCancelled(true);
            else repairable.setRepairCost(repairEvent.getRepairCost());
            return;
        } else if (first != null && second != null) {
            AnvilItemCombineEvent combineEvent = new AnvilItemCombineEvent(player, anvil, first, second, result);
            if (combineEvent.call()) event.setCancelled(true);
            else event.setCurrentItem(combineEvent.getResult());
        }
        if (first == null || second != null) return;
        String old = first.getDisplayName();
        if (Objects.equals(anvil.getRenameText(), old)) return;
        AnvilRenameItemEvent renameEvent = new AnvilRenameItemEvent(player, anvil, result, old);
        if (!renameEvent.call()) event.setCancelled(true);
        else result.setName(renameEvent.getNewName());
    }
}
