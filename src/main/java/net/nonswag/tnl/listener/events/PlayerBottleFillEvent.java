package net.nonswag.tnl.listener.events;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.Hand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public class PlayerBottleFillEvent extends PlayerEvent {

    @Nullable
    private TNLItem replacement = null;
    @Nonnull
    private final TNLItem itemStack;
    @Nonnull
    private final Block block;
    @Nonnull
    private final Hand hand;

    public PlayerBottleFillEvent(@Nonnull TNLPlayer player, @Nonnull TNLItem itemStack, @Nonnull Block block, @Nonnull Hand hand) {
        super(player);
        this.itemStack = itemStack;
        this.block = block;
        this.hand = hand;
    }
}
