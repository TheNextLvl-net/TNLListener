package net.nonswag.tnl.listener.api.item;

import com.google.common.annotations.Beta;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.object.Condition;
import net.nonswag.tnl.listener.api.enchantment.Enchant;
import net.nonswag.tnl.listener.api.gui.GUIItem;
import net.nonswag.tnl.listener.api.item.interactive.InteractiveItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.nbt.NBTTag;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URI;
import java.util.*;

public abstract class TNLItem extends ItemStack {

    @Nullable
    private GUIItem guiItem = null;
    @Nullable
    private InteractiveItem interactiveItem = null;

    protected TNLItem(@Nonnull ItemStack item) {
        this(item.getType());
        setItemMeta(item.getItemMeta());
        amount(item.getAmount());
    }

    protected TNLItem(@Nonnull Material type) {
        super(type);
    }

    @Nonnull
    public GUIItem toGUIItem() {
        if (guiItem == null) this.guiItem = new GUIItem() {
            @Nonnull
            @Override
            public TNLItem getItem() {
                return TNLItem.this;
            }
        };
        return guiItem;
    }

    @Beta
    @Nonnull
    public InteractiveItem toInteractiveItem() {
        if (interactiveItem == null) interactiveItem = new InteractiveItem() {
            @Nonnull
            @Override
            public TNLItem getItem() {
                return TNLItem.this;
            }
        };
        return interactiveItem;
    }

    @Deprecated
    @Nonnull
    public ItemStack build() {
        return getItemStack();
    }

    @Nonnull
    public ItemStack getItemStack() {
        return this;
    }

    @Nonnull
    public String getName() {
        String displayName = getDisplayName();
        return displayName != null ? displayName : "";
    }

    @Nullable
    @SuppressWarnings("deprecation")
    public String getDisplayName() {
        ItemMeta itemMeta = getItemMeta();
        return itemMeta != null ? itemMeta.getDisplayName() : null;
    }

    public boolean hasDisplayName() {
        ItemMeta itemMeta = getItemMeta();
        return itemMeta != null && itemMeta.hasDisplayName();
    }

    @Nonnull
    public TNLItem amount(int amount) {
        setAmount(amount);
        return this;
    }

    @Nonnull
    public TNLItem type(@Nonnull Material material) {
        setType(material);
        return this;
    }

    @Nonnull
    public TNLItem add(int quantity) {
        return amount(Math.min(getMaxStackSize(), getAmount() + quantity));
    }

    @Nonnull
    public TNLItem subtract(int quantity) {
        return amount(Math.max(0, getAmount() - quantity));
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public List<String> getLore() {
        ItemMeta meta = getItemMeta();
        return meta != null ? (meta.getLore() != null ? meta.getLore() : new ArrayList<>()) : new ArrayList<>();
    }

    @Nullable
    public Color getColor() {
        if (getItemMeta() instanceof PotionMeta potionMeta) return potionMeta.getColor();
        else if (getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta) return leatherArmorMeta.getColor();
        else if (getItemMeta() instanceof MapMeta mapMeta) return mapMeta.getColor();
        else if (getItemStack() instanceof BannerMeta bannerMeta) {
            if (!bannerMeta.getPatterns().isEmpty()) {
                return bannerMeta.getPatterns().get(0).getColor().getColor();
            }
        } else if (getItemMeta() instanceof FireworkEffectMeta effectMeta) {
            if (effectMeta.getEffect() != null && !effectMeta.getEffect().getColors().isEmpty()) {
                return effectMeta.getEffect().getColors().get(0);
            }
        }
        return null;
    }

    @Nonnull
    public TNLItem setName(@Nullable String name) {
        if (name != null) name = Message.format(name);
        ItemMeta meta = getItemMeta();
        if (meta != null) meta.displayName(name != null ? Component.text(name) : null);
        if (meta instanceof BookMeta bookMeta) bookMeta.setTitle(name);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem setAuthor(@Nonnull String author) {
        author = Message.format(author);
        ItemMeta meta = getItemMeta();
        if (meta instanceof BookMeta) ((BookMeta) meta).setAuthor(author);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem setGeneration(@Nonnull BookMeta.Generation generation) {
        ItemMeta meta = getItemMeta();
        if (meta instanceof BookMeta) ((BookMeta) meta).setGeneration(generation);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem addPage(@Nonnull String... text) {
        ItemMeta meta = getItemMeta();
        if (meta instanceof BookMeta book) for (String s : text) book.addPages(Component.text(Message.format(s)));
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem setShiny(@Nonnull Condition condition) {
        if (condition.check()) return enchant(Enchant.Defaults.SHINY);
        return unEnchant(Enchant.Defaults.SHINY);
    }

    @Nonnull
    public TNLItem setShiny(boolean shiny) {
        return setShiny(() -> shiny);
    }

    @Nonnull
    public TNLItem enchant(@Nonnull Enchantment enchantment) {
        return enchant(enchantment, enchantment.getStartLevel());
    }

    @Nonnull
    public TNLItem enchant(@Nonnull Enchantment enchantment, int level) {
        ItemMeta meta = getItemMeta();
        if (meta != null) meta.addEnchant(enchantment, level, true);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem unEnchant(@Nonnull Enchantment enchantment) {
        ItemMeta meta = getItemMeta();
        if (meta != null && meta.hasEnchant(enchantment)) meta.removeEnchant(enchantment);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem unEnchant() {
        ItemMeta meta = getItemMeta();
        if (meta != null) for (Enchantment enchantment : meta.getEnchants().keySet()) unEnchant(enchantment);
        return this;
    }

    @Nonnull
    public TNLItem setDamage(float damage) {
        ItemMeta meta = getItemMeta();
        if (meta instanceof Damageable damageable) damageable.setDamage(((short) damage));
        setItemMeta(meta);
        return this;
    }

    public int setDamage() {
        ItemMeta meta = getItemMeta();
        if (meta instanceof Damageable damageable) return damageable.getDamage();
        return 0;
    }

    @Nonnull
    public TNLItem hideFlag(@Nonnull ItemFlag itemFlag) {
        ItemMeta meta = getItemMeta();
        if (meta != null) meta.addItemFlags(itemFlag);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem setSkullOwner(@Nullable OfflinePlayer owner) {
        ItemMeta meta = getItemMeta();
        if (meta instanceof SkullMeta) ((SkullMeta) meta).setOwningPlayer(owner);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem setSkullOwner(@Nullable UUID uuid) {
        if (uuid == null) return this;
        return setSkullOwner(Bukkit.getOfflinePlayer(uuid));
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    public TNLItem setSkullOwner(@Nonnull String name) {
        return setSkullOwner(Bukkit.getOfflinePlayer(name));
    }

    @Nonnull
    public TNLItem setSkullImgURL(@Nonnull String url) {
        try {
            setSkullValue(Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + new URI(url) + "\"}}}").getBytes()));
        } catch (Exception e) {
            Logger.error.println(e.getMessage());
        }
        return this;
    }

    @Nonnull
    public TNLItem setSkullValue(@Nonnull String base64) {
        try {
            modifyNBT("{SkullOwner:{Id:\"" + new UUID(base64.hashCode(), base64.hashCode()) + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}");
        } catch (Exception e) {
            Logger.error.println(e.getMessage());
        }
        return this;
    }

    @Nonnull
    public TNLItem setColor(@Nullable Color color) {
        ItemMeta meta = getItemMeta();
        if (meta instanceof PotionMeta potionMeta) potionMeta.setColor(color);
        else if (meta instanceof LeatherArmorMeta leatherArmorMeta) leatherArmorMeta.setColor(color);
        else if (meta instanceof MapMeta mapMeta) mapMeta.setColor(color);
        else if (meta instanceof BannerMeta bannerMeta) {
            if (color != null) {
                DyeColor dyeColor = DyeColor.getByColor(color);
                if (dyeColor != null) bannerMeta.addPattern(new Pattern(dyeColor, PatternType.BASE));
                else bannerMeta.getPatterns().clear();
            } else bannerMeta.getPatterns().clear();
        } else if (meta instanceof FireworkEffectMeta) {
            if (color != null) return setEffect(FireworkEffect.builder().withColor(color).build());
        }
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem addBannerPattern(@Nonnull Pattern pattern) {
        ItemMeta meta = getItemMeta();
        if (meta instanceof BannerMeta) ((BannerMeta) meta).addPattern(pattern);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public List<Pattern> getBannerPatterns() {
        ItemMeta meta = getItemMeta();
        if (meta instanceof BannerMeta) ((BannerMeta) meta).getPatterns();
        return new ArrayList<>();
    }

    @Nonnull
    public TNLItem setCustomModelData(int customModelData) {
        modifyNBT("{CustomModelData:" + customModelData + "}");
        return this;
    }

    public int getDamage() {
        ItemMeta meta = getItemMeta();
        if (meta instanceof Damageable) return ((Damageable) meta).getDamage();
        return 0;
    }

    public abstract int getMaxDurability();

    public int getCustomModelData() {
        return getNBT().getInt("{CustomModelData}");
    }

    @Nonnull
    public abstract NBTTag getNBT();

    @Nonnull
    public abstract TNLItem setNBT(@Nonnull NBTTag nbt);

    @Nonnull
    public abstract TNLItem modifyNBT(@Nonnull String nbt);

    @Nonnull
    public TNLItem setPower(int power) {
        ItemMeta meta = getItemMeta();
        if (meta instanceof FireworkMeta) ((FireworkMeta) meta).setPower(power);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem setEffect(@Nonnull FireworkEffect effect) {
        ItemMeta meta = getItemMeta();
        if (meta instanceof FireworkEffectMeta) ((FireworkEffectMeta) meta).setEffect(effect);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem addEffects(@Nonnull PotionEffect... effects) {
        ItemMeta meta = getItemMeta();
        if (meta instanceof PotionMeta) {
            for (PotionEffect effect : effects) ((PotionMeta) meta).addCustomEffect(effect, false);
        }
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem hideFlags() {
        ItemMeta meta = getItemMeta();
        if (meta != null) meta.addItemFlags(ItemFlag.values());
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem addAttribute(@Nonnull Attribute attribute, @Nonnull AttributeModifier modifier) {
        ItemMeta meta = getItemMeta();
        if (meta != null) meta.addAttributeModifier(attribute, modifier);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem removeAttributes(@Nonnull Attribute... attributes) {
        ItemMeta meta = getItemMeta();
        if (meta != null) for (Attribute attribute : attributes) meta.removeAttributeModifier(attribute);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem addFlags(@Nonnull ItemFlag... itemFlags) {
        ItemMeta meta = getItemMeta();
        if (meta != null) meta.addItemFlags(itemFlags);
        setItemMeta(meta);
        return this;
    }

    @Nullable
    public abstract FoodProperties getFoodProperties();

    public abstract boolean isFood();

    public boolean hasEnchantments() {
        ItemMeta meta = getItemMeta();
        return meta != null && meta.hasEnchants();
    }

    public boolean hasEnchantment(@Nonnull Enchantment enchantment) {
        ItemMeta meta = getItemMeta();
        return meta != null && meta.hasEnchant(enchantment);
    }

    @Nonnull
    public TNLItem addStoredEnchantment(@Nonnull Enchantment enchantment) {
        return addStoredEnchantment(enchantment, 1);
    }

    @Nonnull
    public TNLItem addStoredEnchantment(@Nonnull Enchantment enchantment, int level) {
        return addStoredEnchantment(enchantment, level, false);
    }

    @Nonnull
    public TNLItem addStoredEnchantment(@Nonnull Enchantment enchantment, int level, boolean restricted) {
        ItemMeta itemMeta = getItemMeta();
        if (itemMeta instanceof EnchantmentStorageMeta meta) meta.addStoredEnchant(enchantment, level, !restricted);
        setItemMeta(itemMeta);
        return this;
    }

    @Nonnull
    public TNLItem removeStoredEnchantment(@Nonnull Enchantment enchantment) {
        ItemMeta itemMeta = getItemMeta();
        if (itemMeta instanceof EnchantmentStorageMeta meta) meta.removeStoredEnchant(enchantment);
        setItemMeta(itemMeta);
        return this;
    }

    @Nonnull
    public TNLItem withLore(@Nonnull String... lore) {
        return withLore(Arrays.asList(lore));
    }

    @Nonnull
    public TNLItem withLore(@Nonnull List<String> lore) {
        if (lore.isEmpty()) return removeLore();
        ItemMeta meta = getItemMeta();
        List<Component> l = new ArrayList<>();
        for (String s : lore) for (String s1 : s.split("\n")) l.add(Component.text(Message.format(s1)));
        if (meta != null) meta.lore(l);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem addLore(@Nonnull String... lore) {
        List<String> list = getLore();
        for (String s : lore) list.addAll(Arrays.asList(s.split("\n")));
        return withLore(list);
    }

    @Nonnull
    public TNLItem removeLore() {
        ItemMeta meta = getItemMeta();
        if (meta != null) meta.lore(null);
        setItemMeta(meta);
        return this;
    }

    @Nonnull
    public TNLItem setUnbreakable(boolean unbreakable) {
        ItemMeta meta = getItemMeta();
        if (meta != null) meta.setUnbreakable(unbreakable);
        setItemMeta(meta);
        return this;
    }

    public boolean isAir() {
        return ItemType.AIR.matches(this);
    }

    @Nonnull
    @Override
    public TNLItem clone() {
        return create(super.clone());
    }

    @Nullable
    public static TNLItem nullable(@Nullable ItemStack itemStack) {
        return itemStack == null ? null : create(itemStack);
    }

    @Nonnull
    public static TNLItem create(@Nonnull ItemStack itemStack) {
        return Mapping.get().createItem(itemStack);
    }

    @Nonnull
    public static TNLItem create(@Nonnull Material material) {
        return create(new ItemStack(material));
    }

    @Nonnull
    public static TNLItem create(@Nonnull OfflinePlayer player) {
        return create(Material.PLAYER_HEAD).setSkullOwner(player);
    }

    @Nonnull
    public static TNLItem create(@Nonnull TNLPlayer player) {
        return create(player.bukkit());
    }

    @Nonnull
    public static TNLItem create(@Nullable UUID uuid) {
        return create(Material.PLAYER_HEAD).setSkullOwner(uuid);
    }
}
