package net.nonswag.tnl.listener.events.inventory.anvil;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.inventory.AnvilInventory;

import javax.annotation.Nullable;

@Getter
@Setter
@FieldsAreNullableByDefault
public class AnvilRenameItemEvent extends AnvilTakeResultEvent {
    private final String oldName;
    private String newName;

    public AnvilRenameItemEvent(TNLPlayer player, AnvilInventory anvil, TNLItem result, @Nullable String oldName) {
        super(player, anvil, result);
        this.oldName = oldName;
        this.newName = getAnvil().getRenameText();
    }
}
