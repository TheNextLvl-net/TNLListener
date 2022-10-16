package net.nonswag.tnl.listener.events.inventory.anvil;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.inventory.AnvilInventory;

import javax.annotation.Nonnull;

@Getter
@Setter
public class AnvilRepairItemEvent extends AnvilTakeResultEvent {

    private int repairCost;

    public AnvilRepairItemEvent(@Nonnull TNLPlayer player, @Nonnull AnvilInventory anvil, @Nonnull TNLItem result) {
        super(player, anvil, result);
        this.repairCost = anvil.getRepairCost();
    }
}
