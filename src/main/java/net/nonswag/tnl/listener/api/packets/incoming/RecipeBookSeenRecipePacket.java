package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

@Getter
@Setter
public class RecipeBookSeenRecipePacket implements IncomingPacket {
    @Nonnull
    private NamespacedKey recipe;

    public RecipeBookSeenRecipePacket(@Nonnull NamespacedKey recipe) {
        this.recipe = recipe;
    }
}
