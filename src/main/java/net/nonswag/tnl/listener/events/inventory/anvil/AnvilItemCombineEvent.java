package net.nonswag.tnl.listener.events.inventory.anvil;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.inventory.AnvilInventory;

@Getter
@Setter
public class AnvilItemCombineEvent extends AnvilTakeResultEvent {
    private final TNLItem first, second;
    private TNLItem result;

    public AnvilItemCombineEvent(TNLPlayer player, AnvilInventory anvil, TNLItem first, TNLItem second, TNLItem result) {
        super(player, anvil, result);
        this.first = first;
        this.second = second;
        this.result = result;
    }
}
