package net.nonswag.tnl.listener.api.item;

import net.nonswag.core.api.math.MathUtil;
import net.nonswag.tnl.listener.api.gui.GUIItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public record ItemType(@Nonnull Predicate<Material> predicate) {

    @Nonnull
    public static final ItemType AIR = new ItemType(material -> material == null || material.equals(Material.AIR) || material.equals(Material.VOID_AIR) || material.equals(Material.CAVE_AIR));
    @Nonnull
    public static final ItemType BED = new ItemType(material -> material != null && Bed.class.equals(material.data));
    @Nonnull
    public static final ItemType GLASS_PANE = new ItemType(material -> material != null && GlassPane.class.equals(material.data));
    @Nonnull
    public static final ItemType GLASS_BLOCK = new ItemType(material -> material != null && (material.equals(Material.GLASS) || material.name().endsWith("STAINED_GLASS")));
    @Nonnull
    public static final ItemType GENERAL_GLASS = new ItemType(material -> GLASS_BLOCK.matches(material) || GLASS_PANE.matches(material));
    @Nonnull
    public static final ItemType LIQUID = new ItemType(material -> material != null && (material.equals(Material.WATER) || material.equals(Material.LAVA)));
    @Nonnull
    public static final ItemType TRANSPARENT = new ItemType(material -> GENERAL_GLASS.matches(material) || AIR.matches(material) || material.equals(Material.WATER));
    @Nonnull
    public static final ItemType ORE = new ItemType(material -> material != null && (material.name().endsWith("_ORE")));
    @Nonnull
    public static final ItemType HELMET = new ItemType(material -> material != null && (material.name().endsWith("_HELMET")));
    @Nonnull
    public static final ItemType CHESTPLATE = new ItemType(material -> material != null && (material.name().endsWith("_CHESTPLATE")));
    @Nonnull
    public static final ItemType LEGGINGS = new ItemType(material -> material != null && (material.name().endsWith("_LEGGINGS")));
    @Nonnull
    public static final ItemType BOOTS = new ItemType(material -> material != null && (material.name().endsWith("_BOOTS")));
    @Nonnull
    public static final ItemType SWORD = new ItemType(material -> material != null && (material.name().endsWith("_SWORD")));
    @Nonnull
    public static final ItemType PICKAXE = new ItemType(material -> material != null && (material.name().endsWith("_PICKAXE")));
    @Nonnull
    public static final ItemType AXE = new ItemType(material -> material != null && (material.name().endsWith("_AXE")));
    @Nonnull
    public static final ItemType HOE = new ItemType(material -> material != null && (material.name().endsWith("_HOE")));
    @Nonnull
    public static final ItemType SHOVEL = new ItemType(material -> material != null && (material.name().endsWith("_SHOVEL")));
    @Nonnull
    public static final ItemType ARMOR = new ItemType(material -> HELMET.matches(material) || CHESTPLATE.matches(material) || LEGGINGS.matches(material) || BOOTS.matches(material));
    @Nonnull
    public static final ItemType TOOL = new ItemType(material -> SWORD.matches(material) || PICKAXE.matches(material) || AXE.matches(material) || HOE.matches(material) || SHOVEL.matches(material));
    @Nonnull
    public static final ItemType COMPOSTABLE = new ItemType(material -> ItemHelper.getInstance().isCompostable(material));

    public boolean matches(@Nullable Material material) {
        return predicate().test(material);
    }

    public boolean matches(@Nullable ItemStack item) {
        return matches(item == null ? null : item.getType());
    }

    public boolean matches(@Nullable TNLItem item) {
        return matches(item == null ? null : item.getType());
    }

    public boolean matches(@Nullable GUIItem item) {
        return matches(item == null ? null : item.getItem());
    }

    public boolean matches(@Nullable Block block) {
        return matches(block == null ? null : block.getType());
    }

    public boolean matches(@Nullable Location location) {
        return matches(location == null || location.getWorld() == null ? null : location.getBlock());
    }

    @Nonnull
    public List<Material> all(@Nonnull Selector selector) {
        List<Material> materials = new ArrayList<>();
        for (Material material : Material.values()) {
            if (matches(material)) {
                if (selector.equals(Selector.ITEMS) && material.isItem()) materials.add(material);
                else if (selector.equals(Selector.BLOCKS) && material.isBlock()) materials.add(material);
            }
        }
        return materials;
    }

    @Nonnull
    public static Material random(@Nonnull Selector selector) {
        Material random = null;
        while (random == null) {
            random = Material.values()[MathUtil.randomInteger(0, Material.values().length - 1)];
            if (AIR.matches(random)) random = null;
            else if (selector.equals(Selector.ITEMS) && !random.isItem()) random = null;
            else if (selector.equals(Selector.BLOCKS) && !random.isBlock()) random = null;
        }
        return random;
    }

    public record Selector(@Nonnull Predicate<Material> predicate) {
        @Nonnull
        public static final Selector ITEMS = new Selector(Material::isItem);
        @Nonnull
        public static final Selector BLOCKS = new Selector(Material::isBlock);
        @Nonnull
        public static final Selector ANY = new Selector(material -> true);
    }
}
