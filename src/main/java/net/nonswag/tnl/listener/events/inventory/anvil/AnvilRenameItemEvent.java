package net.nonswag.tnl.listener.events.inventory.anvil;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.inventory.AnvilInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public class AnvilRenameItemEvent extends AnvilTakeResultEvent {

    @Nullable
    private final String oldName;
    @Nullable
    private String newName;

    public AnvilRenameItemEvent(@Nonnull TNLPlayer player, @Nonnull AnvilInventory anvil, @Nonnull TNLItem result, @Nullable String oldName) {
        super(player, anvil, result);
        this.oldName = oldName;
        this.newName = getAnvil().getRenameText();
    }
}
