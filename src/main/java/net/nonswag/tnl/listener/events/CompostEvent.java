package net.nonswag.tnl.listener.events;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.TNLEvent;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@Getter
public class CompostEvent extends TNLEvent {

    @Nonnull
    private final Block composter;
    @Nonnull
    private final Levelled level;
    @Nonnull
    private final ItemStack item;

    public CompostEvent(@Nonnull Block composter, @Nonnull ItemStack item) {
        if (!(composter.getBlockData() instanceof Levelled)) throw new IllegalArgumentException();
        this.composter = composter;
        this.level = ((Levelled) composter.getBlockData());
        this.item = item;
    }
}
