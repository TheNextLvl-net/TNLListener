package net.nonswag.tnl.listener.api.enchantment;

import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
@Setter
@Accessors(chain = true)
public class Enchant extends Enchantment {

    @Getter
    private static final List<Enchant> enchants = new ArrayList<>();

    private final String id;
    private final String name;
    private final EnchantmentTarget itemTarget;
    @Nullable
    private Predicate<Enchantment> conflicting;
    @Nullable
    private Predicate<ItemStack> enchanting;
    private EnchantmentRarity rarity = EnchantmentRarity.COMMON;
    private int maxLevel = 1;
    private int startLevel = 1;
    private boolean treasure = false, cursed = false, tradeable = false, discoverable = false;

    @SuppressWarnings("deprecation")
    public static Enchant create(String name) {
        return create(name, EnchantmentTarget.ALL);
    }

    @SuppressWarnings("deprecation")
    public static Enchant create(String id, String name) {
        return create(id, name, EnchantmentTarget.ALL);
    }

    public static Enchant create(String name, EnchantmentTarget target) {
        return create(name.toLowerCase().replace(" ", "_").replaceAll("[^a-z_]", ""), name, target);
    }

    public static Enchant create(String id, String name, EnchantmentTarget target) {
        return create(new NamespacedKey(Bootstrap.getInstance(), id.toLowerCase()), name, target);
    }

    @SuppressWarnings("deprecation")
    public static Enchant create(NamespacedKey key, String name) {
        return create(key, name, EnchantmentTarget.ALL);
    }

    public static Enchant create(NamespacedKey key, String name, EnchantmentTarget target) {
        return Mapping.get().createEnchant(key, name, target);
    }

    protected Enchant(NamespacedKey key, String name, EnchantmentTarget target) {
        super(key);
        this.id = key.getKey();
        this.name = name;
        this.itemTarget = target;
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

    @Nonnull
    @Override
    public String translationKey() {
        return getKey().toString();
    }

    public enum Rarity {
        COMMON, UNCOMMON, RARE, VERY_RARE
    }

    public static final class Defaults {
        public static final Enchant SHINY = create("Shiny");
    }
}
