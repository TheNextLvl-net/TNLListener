package net.nonswag.tnl.listener.events.inventory.anvil;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.inventory.AnvilInventory;

@Getter
@Setter
public class AnvilRepairItemEvent extends AnvilTakeResultEvent {
    private int repairCost;

    public AnvilRepairItemEvent(TNLPlayer player, AnvilInventory anvil, TNLItem result) {
        super(player, anvil, result);
        this.repairCost = anvil.getRepairCost();
    }
}
