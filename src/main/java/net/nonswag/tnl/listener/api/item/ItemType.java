package net.nonswag.tnl.listener.api.item;

import net.nonswag.core.api.math.MathUtil;
import net.nonswag.tnl.listener.api.gui.GUIItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public record ItemType(Predicate<Material> predicate) {

    public static final ItemType AIR = new ItemType(material -> material == null || material.equals(Material.AIR) || material.equals(Material.VOID_AIR) || material.equals(Material.CAVE_AIR));
    public static final ItemType BED = new ItemType(material -> material != null && Bed.class.equals(material.data));
    public static final ItemType GLASS_PANE = new ItemType(material -> material != null && GlassPane.class.equals(material.data));
    public static final ItemType GLASS_BLOCK = new ItemType(material -> material != null && (material.equals(Material.GLASS) || material.name().endsWith("STAINED_GLASS")));
    public static final ItemType GENERAL_GLASS = new ItemType(material -> GLASS_BLOCK.matches(material) || GLASS_PANE.matches(material));
    public static final ItemType LIQUID = new ItemType(material -> material != null && (material.equals(Material.WATER) || material.equals(Material.LAVA)));
    public static final ItemType TRANSPARENT = new ItemType(material -> GENERAL_GLASS.matches(material) || AIR.matches(material) || material.equals(Material.WATER));
    public static final ItemType ORE = new ItemType(material -> material != null && (material.name().endsWith("_ORE")));
    public static final ItemType HELMET = new ItemType(material -> material != null && (material.name().endsWith("_HELMET")));
    public static final ItemType CHESTPLATE = new ItemType(material -> material != null && (material.name().endsWith("_CHESTPLATE")));
    public static final ItemType LEGGINGS = new ItemType(material -> material != null && (material.name().endsWith("_LEGGINGS")));
    public static final ItemType BOOTS = new ItemType(material -> material != null && (material.name().endsWith("_BOOTS")));
    public static final ItemType SWORD = new ItemType(material -> material != null && (material.name().endsWith("_SWORD")));
    public static final ItemType PICKAXE = new ItemType(material -> material != null && (material.name().endsWith("_PICKAXE")));
    public static final ItemType AXE = new ItemType(material -> material != null && (material.name().endsWith("_AXE")));
    public static final ItemType HOE = new ItemType(material -> material != null && (material.name().endsWith("_HOE")));
    public static final ItemType SHOVEL = new ItemType(material -> material != null && (material.name().endsWith("_SHOVEL")));
    public static final ItemType ARMOR = new ItemType(material -> HELMET.matches(material) || CHESTPLATE.matches(material) || LEGGINGS.matches(material) || BOOTS.matches(material));
    public static final ItemType TOOL = new ItemType(material -> SWORD.matches(material) || PICKAXE.matches(material) || AXE.matches(material) || HOE.matches(material) || SHOVEL.matches(material));
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

    public List<Material> all(Selector selector) {
        List<Material> materials = new ArrayList<>();
        for (Material material : Material.values()) {
            if (matches(material)) {
                if (selector.equals(Selector.ITEMS) && material.isItem()) materials.add(material);
                else if (selector.equals(Selector.BLOCKS) && material.isBlock()) materials.add(material);
            }
        }
        return materials;
    }

    public static Material random(Selector selector) {
        Material random = null;
        while (random == null) {
            random = Material.values()[MathUtil.randomInteger(0, Material.values().length - 1)];
            if (AIR.matches(random)) random = null;
            else if (selector.equals(Selector.ITEMS) && !random.isItem()) random = null;
            else if (selector.equals(Selector.BLOCKS) && !random.isBlock()) random = null;
        }
        return random;
    }

    public record Selector(Predicate<Material> predicate) {
        public static final Selector ITEMS = new Selector(Material::isItem);
        public static final Selector BLOCKS = new Selector(Material::isBlock);
        public static final Selector ANY = new Selector(material -> true);
    }
}
