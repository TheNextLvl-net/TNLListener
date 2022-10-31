package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class RecipeBookSeenRecipePacket extends PacketBuilder {
    @Nonnull
    private NamespacedKey recipe;

    protected RecipeBookSeenRecipePacket(@Nonnull NamespacedKey recipe) {
        this.recipe = recipe;
    }

    @Nonnull
    public static RecipeBookSeenRecipePacket create(@Nonnull NamespacedKey recipe) {
        return Mapping.get().packetManager().incoming().recipeBookSeenRecipePacket(recipe);
    }
}
