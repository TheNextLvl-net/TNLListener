package net.nonswag.tnl.listener.api.enchantment;

import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.reflection.Reflection;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

@Getter
public class Enchant extends Enchantment {

    @Getter
    @Nonnull
    private static final List<Enchant> enchants = new ArrayList<>();

    @Nonnull
    private final String id;
    @Nonnull
    private final String name;
    @Nonnull
    private final EnchantmentTarget itemTarget;
    @Nullable
    private Predicate<Enchantment> conflicting;
    @Nullable
    private Predicate<ItemStack> enchanting;
    @Nonnull
    private EnchantmentRarity rarity = EnchantmentRarity.COMMON;
    private int maxLevel = 1;
    private int startLevel = 1;
    private boolean treasure = false, cursed = false, tradeable = false, discoverable = false;

    @Nonnull
    @SuppressWarnings("deprecation")
    public static Enchant create(@Nonnull String name) {
        return create(name, EnchantmentTarget.ALL);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public static Enchant create(@Nonnull String id, @Nonnull String name) {
        return create(id, name, EnchantmentTarget.ALL);
    }

    @Nonnull
    public static Enchant create(@Nonnull String name, @Nonnull EnchantmentTarget target) {
        return create(name.toLowerCase().replace(" ", "_").replaceAll("[^a-z_]", ""), name, target);
    }

    @Nonnull
    public static Enchant create(@Nonnull String id, @Nonnull String name, @Nonnull EnchantmentTarget target) {
        return create(new NamespacedKey(Bootstrap.getInstance(), id.toLowerCase()), name, target);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public static Enchant create(@Nonnull NamespacedKey key, @Nonnull String name) {
        return create(key, name, EnchantmentTarget.ALL);
    }

    @Nonnull
    public static Enchant create(@Nonnull NamespacedKey key, @Nonnull String name, @Nonnull EnchantmentTarget target) {
        return Mapping.get().createEnchant(key, name, target);
    }

    protected Enchant(@Nonnull NamespacedKey key, @Nonnull String name, @Nonnull EnchantmentTarget target) {
        super(key);
        this.id = key.getKey();
        this.name = name;
        this.itemTarget = target;
    }

    @Nonnull
    public Enchant setEnchanting(@Nullable Predicate<ItemStack> enchanting) {
        this.enchanting = enchanting;
        return this;
    }

    @Nonnull
    public Enchant setConflicting(@Nullable Predicate<Enchantment> conflicting) {
        this.conflicting = conflicting;
        return this;
    }

    @Nonnull
    public Enchant setCursed(boolean cursed) {
        this.cursed = cursed;
        return this;
    }

    @Nonnull
    public Enchant setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    @Nonnull
    public Enchant setStartLevel(int startLevel) {
        this.startLevel = startLevel;
        return this;
    }

    @Nonnull
    public Enchant setTreasure(boolean treasure) {
        this.treasure = treasure;
        return this;
    }

    @Nonnull
    public Enchant setDiscoverable(boolean discoverable) {
        this.discoverable = discoverable;
        return this;
    }

    @Nonnull
    public Enchant setTradeable(boolean tradeable) {
        this.tradeable = tradeable;
        return this;
    }

    @Nonnull
    public Enchant setRarity(@Nonnull EnchantmentRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    @Override
    public boolean conflictsWith(@Nonnull Enchantment enchantment) {
        return getConflicting() != null && getConflicting().test(enchantment);
    }

    @Override
    public boolean canEnchantItem(@Nonnull ItemStack itemStack) {
        return getItemTarget().includes(itemStack) && getEnchanting() != null && getEnchanting().test(itemStack);
    }

    @Nonnull
    @Override
    public Component displayName(int level) {
        return Component.text(getName());
    }

    @Override
    public float getDamageIncrease(int level, @Nonnull EntityCategory entityCategory) {
        return 0;
    }

    @Nonnull
    @Override
    public Set<EquipmentSlot> getActiveSlots() {
        return Set.of(EquipmentSlot.values());
    }

    @Nonnull
    protected Enchant register() {
        Objects.requireNonNull(Reflection.Field.Static.<Map<NamespacedKey, Enchantment>>get(Enchantment.class, "byKey")).put(getKey(), this);
        Objects.requireNonNull(Reflection.Field.Static.<Map<String, Enchantment>>get(Enchantment.class, "byName")).put(getName(), this);
        getEnchants().remove(this);
        getEnchants().add(this);
        return this;
    }

    protected void unregister() {
        Objects.requireNonNull(Reflection.Field.Static.<Map<NamespacedKey, Enchantment>>get(Enchantment.class, "byKey")).remove(getKey());
        Objects.requireNonNull(Reflection.Field.Static.<Map<String, Enchantment>>get(Enchantment.class, "byName")).remove(getName());
    }

    public enum Rarity {
        COMMON, UNCOMMON, RARE, VERY_RARE
    }

    public static final class Defaults {
        @Nonnull
        public static final Enchant SHINY = create("Shiny");
    }
}
