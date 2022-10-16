package net.nonswag.tnl.listener.events.inventory.anvil;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.inventory.AnvilInventory;

import javax.annotation.Nonnull;

@Getter
@Setter
public class AnvilItemCombineEvent extends AnvilTakeResultEvent {

    @Nonnull
    private final TNLItem first, second;
    @Nonnull
    private TNLItem result;

    public AnvilItemCombineEvent(@Nonnull TNLPlayer player, @Nonnull AnvilInventory anvil, @Nonnull TNLItem first, @Nonnull TNLItem second, @Nonnull TNLItem result) {
        super(player, anvil, result);
        this.first = first;
        this.second = second;
        this.result = result;
    }
}
