package net.nonswag.tnl.listener.events;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.TNLEvent;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.inventory.ItemStack;

@Getter
public class CompostEvent extends TNLEvent {
    private final Block composter;
    private final Levelled data;
    private final ItemStack item;

    public CompostEvent(Block composter, ItemStack item) {
        if (!(composter.getBlockData() instanceof Levelled data)) throw new IllegalArgumentException();
        this.composter = composter;
        this.data = data;
        this.item = item;
    }
}
