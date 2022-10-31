package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class RecipeBookChangeSettingsPacket extends PacketBuilder {
    @Nonnull
    private RecipeBookType category;
    private boolean guiOpen;
    private boolean filteringCraftable;

    protected RecipeBookChangeSettingsPacket(@Nonnull RecipeBookType category, boolean guiOpen, boolean filteringCraftable) {
        this.category = category;
        this.guiOpen = guiOpen;
        this.filteringCraftable = filteringCraftable;
    }

    public enum RecipeBookType {
        CRAFTING, FURNACE, BLAST_FURNACE, SMOKER
    }

    @Nonnull
    public static RecipeBookChangeSettingsPacket create(@Nonnull RecipeBookType category, boolean guiOpen, boolean filteringCraftable) {
        return Mapping.get().packetManager().incoming().recipeBookChangeSettingsPacket(category, guiOpen, filteringCraftable);
    }
}
