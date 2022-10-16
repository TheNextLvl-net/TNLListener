package net.nonswag.tnl.listener.events.inventory.anvil;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.inventory.AnvilInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public abstract class AnvilEvent extends PlayerEvent {

    @Nonnull
    private final AnvilInventory anvil;

    protected AnvilEvent(@Nonnull TNLPlayer player, @Nonnull AnvilInventory anvil) {
        super(player);
        this.anvil = anvil;
    }

    @Nullable
    public TNLItem getFirst() {
        return TNLItem.nullable(anvil.getItem(0));
    }

    @Nullable
    public TNLItem getSecond() {
        return TNLItem.nullable(anvil.getItem(1));
    }

    @Nullable
    public TNLItem getResult() {
        return TNLItem.nullable(anvil.getItem(2));
    }

    public void setFirst(@Nullable TNLItem item) {
        anvil.setItem(0, item);
    }

    public void setSecond(@Nullable TNLItem item) {
        anvil.setItem(1, item);
    }

    public void setResult(@Nullable TNLItem item) {
        anvil.setItem(2, item);
    }
}
