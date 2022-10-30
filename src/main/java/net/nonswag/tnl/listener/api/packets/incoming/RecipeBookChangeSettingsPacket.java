package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class RecipeBookChangeSettingsPacket implements IncomingPacket {
    @Nonnull
    private RecipeBookType category;
    private boolean guiOpen;
    private boolean filteringCraftable;

    public RecipeBookChangeSettingsPacket(@Nonnull RecipeBookType category, boolean guiOpen, boolean filteringCraftable) {
        this.category = category;
        this.guiOpen = guiOpen;
        this.filteringCraftable = filteringCraftable;
    }

    public enum RecipeBookType {
        CRAFTING, FURNACE, BLAST_FURNACE, SMOKER
    }
}
