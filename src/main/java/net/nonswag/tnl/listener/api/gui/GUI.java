package net.nonswag.tnl.listener.api.gui;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.math.MathUtil;
import net.nonswag.core.api.math.Range;
import net.nonswag.core.api.object.Pair;
import net.nonswag.tnl.listener.api.gui.iterators.GUIIterator;
import net.nonswag.tnl.listener.api.item.ItemType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.packets.outgoing.SetSlotPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@Getter
@Setter
public class GUI implements Iterable<GUIItem>, Cloneable {

    @Nonnull
    @Getter(AccessLevel.NONE)
    protected final HashMap<Integer, GUIItem> contentHashMap = new HashMap<>();
    @Nonnull
    private final List<TNLPlayer> viewers = new ArrayList<>();
    @Nonnull
    private String title;
    @Nonnull
    private OpenEvent openListener = player -> true;
    @Nonnull
    private CloseEvent closeListener = (player, server) -> true;
    @Nonnull
    private ClickEvent clickListener = (player, slot, type) -> {};
    @Nullable
    private Sound openSound = Sound.BLOCK_CHEST_OPEN;
    @Nullable
    private Sound closeSound = Sound.BLOCK_CHEST_CLOSE;
    private int size;
    private int maxStackSize;
    @Nonnull
    private InventoryType type = InventoryType.CHEST;

    public GUI(@Range(from = 1, to = 6) int rows, @Nonnull String title) {
        this(rows, 64, title);
    }

    public GUI(@Range(from = 1, to = 6) int rows, int maxStackSize, @Nonnull String title) {
        this.size = Math.min(Math.max(rows, 1), 6) * 9;
        this.maxStackSize = maxStackSize;
        this.title = title;
    }

    public GUI(@Nonnull InventoryType type, @Nonnull String title) throws IllegalArgumentException {
        this(type.getDefaultSize() / 9, title);
        if (!type.isCreatable()) throw new IllegalArgumentException("Invalid inventory type");
        this.type = type;
    }

    @Nonnull
    public ImmutableList<TNLPlayer> getViewers() {
        return ImmutableList.copyOf(viewers);
    }

    public boolean isViewer(@Nonnull TNLPlayer player) {
        return viewers.contains(player);
    }

    public void addViewer(@Nonnull TNLPlayer player) {
        if(!isViewer(player)) viewers.add(player);
    }

    public void removeViewer(@Nonnull TNLPlayer viewer) {
        viewers.remove(viewer);
    }

    @Range(from = 1, to = 6)
    public int getRows() {
        return Math.min(Math.max(size / 9, 1), 6);
    }

    @Nonnull
    public GUI setRows(@Range(from = 1, to = 6) int rows) {
        return setSize(Math.min(Math.max(rows, 1), 6) * 9);
    }

    @Nonnull
    public GUI setSize(@Range(from = 9, to = 54) int size) {
        if (this.size == size) return this;
        if (!MathUtil.isInLine(9, Math.min(Math.max(size, 9), 54))) throw new IllegalArgumentException("size: " + size);
        this.size = size;
        getViewers().forEach(all -> all.interfaceManager().openGUI(this));
        return this;
    }

    @Nonnull
    public GUI setOpenSound(@Nullable Sound openSound) {
        this.openSound = openSound;
        return this;
    }

    @Nonnull
    public GUI setCloseSound(@Nullable Sound closeSound) {
        this.closeSound = closeSound;
        return this;
    }

    @Nonnull
    public GUI formatDefault() {
        TNLItem placeholder1 = TNLItem.create(Material.GRAY_STAINED_GLASS_PANE).setName("§7-§8/§7-");
        TNLItem placeholder2 = TNLItem.create(Material.WHITE_STAINED_GLASS_PANE).setName("§7-§8/§7-");
        setItemIfAbsent(0, placeholder2).setItemIfAbsent(8, placeholder2);
        setItemIfAbsent(getSize() - 9, placeholder2).setItemIfAbsent(getSize() - 1, placeholder2);
        for (int i = 0; i < getSize(); i++) setItemIfAbsent(i, placeholder1);
        return this;
    }

    @Nullable
    public GUIItem getItem(int slot) {
        return contentHashMap.get(slot);
    }

    @Nonnull
    public GUI setItem(int slot, @Nullable GUIItem item) throws IllegalArgumentException {
        if (item != null) {
            if (slot >= 0 && slot < getSize()) contentHashMap.put(slot, item);
            else throw new IllegalArgumentException("Slot '" + slot + "' is outside the gui");
        } else remove(slot);
        getViewers().forEach(player -> {
            if (item != null) SetSlotPacket.create(SetSlotPacket.Inventory.TOP, slot, item.getItem()).send(player);
            else SetSlotPacket.create(SetSlotPacket.Inventory.TOP, slot, new ItemStack(Material.AIR)).send(player);
        });
        return this;
    }

    @Nonnull
    public GUI setItem(int slot, @Nullable TNLItem item) throws IllegalArgumentException {
        return setItem(slot, item == null ? null : item.toGUIItem());
    }

    @Nonnull
    public GUI setItem(int slot, @Nullable ItemStack itemStack) throws IllegalArgumentException {
        return setItem(slot, itemStack == null ? null : TNLItem.create(itemStack));
    }

    @Nonnull
    public GUI setItem(int slot, @Nullable Material material) throws IllegalArgumentException {
        return setItem(slot, material == null ? null : new ItemStack(material));
    }

    @Nonnull
    public GUI setItemIfAbsent(int slot, @Nullable GUIItem item) throws IllegalArgumentException {
        if (getItem(slot) == null) return setItem(slot, item);
        return this;
    }

    @Nonnull
    public GUI setItemIfAbsent(int slot, @Nullable TNLItem item) throws IllegalArgumentException {
        return setItemIfAbsent(slot, item == null ? null : item.toGUIItem());
    }

    @Nonnull
    public GUI setItemIfAbsent(int slot, @Nullable ItemStack itemStack) throws IllegalArgumentException {
        return setItemIfAbsent(slot, itemStack == null ? null : TNLItem.create(itemStack));
    }

    @Nonnull
    public GUI setItemIfAbsent(int slot, @Nullable Material material) throws IllegalArgumentException {
        return setItemIfAbsent(slot, material == null ? null : new ItemStack(material));
    }

    @Nonnull
    public GUI addItem(@Nonnull ItemStack itemStack) throws IllegalArgumentException, GUIOverflowException {
        return addItems(itemStack);
    }

    @Nonnull
    public GUI addItems(@Nonnull ItemStack... items) throws IllegalArgumentException, GUIOverflowException {
        List<TNLItem> guiItems = new ArrayList<>();
        for (ItemStack item : items) guiItems.add(TNLItem.create(item));
        return addItems(guiItems.toArray(new TNLItem[]{}));
    }

    @Nonnull
    public GUI addItem(@Nonnull TNLItem item) throws IllegalArgumentException, GUIOverflowException {
        return addItems(item);
    }

    @Nonnull
    public GUI addItems(@Nonnull TNLItem... items) throws IllegalArgumentException, GUIOverflowException {
        List<GUIItem> guiItems = new ArrayList<>();
        for (TNLItem item : items) guiItems.add(item.toGUIItem());
        return addItems(guiItems.toArray(new GUIItem[]{}));
    }

    @Nonnull
    public GUI addItem(@Nonnull GUIItem item) throws IllegalArgumentException, GUIOverflowException {
        return addItems(item);
    }

    @Nonnull
    public GUI addItems(@Nonnull GUIItem... items) throws IllegalArgumentException, GUIOverflowException {
        int overflow = items.length;
        items:
        for (@Nullable GUIItem item : items) {
            for (int slot = 0; slot < getSize(); slot++) {
                if (ItemType.AIR.matches(getItem(slot))) {
                    setItem(slot, item);
                    overflow--;
                    continue items;
                }
            }
            break;
        }
        if (overflow > 0) {
            if (items.length == overflow) throw new GUIOverflowException(items);
            throw new GUIOverflowException(Arrays.asList(items).subList(items.length - overflow, items.length));
        }
        return this;
    }

    @Nonnull
    public GUI removeItems(@Nonnull TNLItem... items) {
        List<GUIItem> guiItems = new ArrayList<>();
        for (TNLItem item : items) guiItems.add(item.toGUIItem());
        return removeItems(guiItems.toArray(new GUIItem[]{}));
    }

    @Nonnull
    public GUI removeItem(@Nonnull TNLItem item) {
        return removeItems(item);
    }

    @Nonnull
    public GUI removeItems(@Nonnull GUIItem... items) {
        for (@Nullable GUIItem item : items) if (item != null) remove(item);
        return this;
    }

    @Nonnull
    public GUI removeItem(@Nonnull GUIItem item) {
        return removeItems(item);
    }

    @Nonnull
    public GUIItem[] getContents() {
        GUIItem[] items = new GUIItem[getSize()];
        for (int i = 0; i < getSize(); i++) items[i] = getItem(i);
        return items;
    }

    @Nonnull
    public List<TNLItem> items() {
        List<TNLItem> items = new ArrayList<>();
        TNLItem air = TNLItem.create(Material.AIR);
        for (int i = 0; i < getSize(); i++) {
            GUIItem item = getItem(i);
            if (item != null) items.add(item.getItem());
            else items.add(air);
        }
        return items;
    }

    @Nonnull
    public GUI setContents(@Nonnull GUIItem[] items) {
        clear();
        for (int i = 0; i < items.length; i++) setItem(i, items[i]);
        return this;
    }

    @Nonnull
    public GUI setContents(@Nonnull TNLItem[] items) {
        clear();
        for (int i = 0; i < items.length; i++) setItem(i, items[i]);
        return this;
    }

    @Nonnull
    public GUI setContents(@Nonnull ItemStack[] items) {
        clear();
        for (int i = 0; i < items.length; i++) setItem(i, items[i]);
        return this;
    }

    public boolean contains(@Nonnull GUIItem item) {
        return contains(item.getItem());
    }

    public boolean contains(@Nonnull TNLItem item) {
        return contains(item.getItemStack());
    }

    public boolean contains(@Nonnull Material material) {
        for (@Nullable GUIItem content : getContents()) {
            if (ItemType.AIR.matches(material) && ItemType.AIR.matches(content)
                    || (content != null && material.equals(content.getItem().getType()))) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(@Nonnull ItemStack itemStack) {
        return contains(itemStack, false);
    }

    public boolean contains(@Nonnull ItemStack itemStack, boolean ignoreAmount) {
        for (@Nullable GUIItem content : getContents()) {
            if (ItemType.AIR.matches(itemStack) && ItemType.AIR.matches(content)) return true;
            else {
                if (ignoreAmount) {
                    if (content != null) {
                        ItemStack clone = itemStack.clone();
                        clone.setAmount(content.getItem().getAmount());
                        if (clone.equals(content.getItem().getItemStack())) return true;
                    }
                } else if (content != null && itemStack.equals(content.getItem().getItemStack())) return true;
            }
        }
        return false;
    }

    public boolean containsAtLeast(@Nonnull GUIItem item, int amount) {
        return containsAtLeast(item.getItem(), amount);
    }

    public boolean containsAtLeast(@Nonnull TNLItem item, int amount) {
        return containsAtLeast(item.getItemStack(), amount);
    }

    public boolean containsAtLeast(@Nonnull Material material, int amount) {
        int finalAmount = 0;
        for (@Nullable GUIItem content : getContents()) {
            if (ItemType.AIR.matches(material) && ItemType.AIR.matches(content)) return true;
            else {
                if (content != null) {
                    if (material.equals(content.getItem().getType())) {
                        if (content.getItem().getAmount() >= amount) return true;
                        else finalAmount += content.getItem().getAmount();
                    }
                }
            }
        }
        return finalAmount >= amount;
    }

    public boolean containsAtLeast(@Nonnull ItemStack itemStack, int amount) {
        int finalAmount = 0;
        for (@Nullable GUIItem content : getContents()) {
            if (ItemType.AIR.matches(itemStack) && ItemType.AIR.matches(content)) return true;
            else {
                if (content != null) {
                    ItemStack clone = itemStack.clone();
                    clone.setAmount(content.getItem().getAmount());
                    if (clone.equals(content.getItem().getItemStack())) {
                        if (clone.getAmount() >= amount) return true;
                        else finalAmount += clone.getAmount();
                    }
                }
            }
        }
        return finalAmount >= amount;
    }

    @Nonnull
    public HashMap<Integer, GUIItem> all(@Nonnull GUIItem item) {
        return all(item.getItem());
    }

    @Nonnull
    public HashMap<Integer, GUIItem> all(@Nonnull TNLItem item) {
        return all(item.getItemStack());
    }

    @Nonnull
    public HashMap<Integer, GUIItem> all(@Nonnull Material material) {
        HashMap<Integer, GUIItem> items = new HashMap<>();
        GUIItem[] contents = getContents();
        for (int i = 0; i < getSize(); i++) {
            if (ItemType.AIR.matches(contents[i]) && ItemType.AIR.matches(material)
                    || (contents[i] != null && contents[i].getItem().getType().equals(material))) {
                items.put(i, contents[i]);
            }
        }
        return items;
    }

    @Nonnull
    public HashMap<Integer, GUIItem> all(@Nonnull ItemStack itemStack) {
        return all(itemStack, false);
    }

    @Nonnull
    public HashMap<Integer, GUIItem> all(@Nonnull ItemStack itemStack, boolean ignoreAmount) {
        HashMap<Integer, GUIItem> items = new HashMap<>();
        GUIItem[] contents = getContents();
        for (int slot = 0; slot < getSize(); slot++) {
            GUIItem content = contents[slot];
            if (ItemType.AIR.matches(content) && ItemType.AIR.matches(itemStack.getType())) {
                items.put(slot, content);
            } else {
                if (itemStack.getType().equals(content.getItem().getType())) {
                    if (ignoreAmount) {
                        ItemStack clone = itemStack.clone();
                        clone.setAmount(content.getItem().getAmount());
                        if (clone.equals(content.getItem().getItemStack())) items.put(slot, content);
                    } else if (itemStack.equals(content.getItem().getItemStack())) items.put(slot, content);
                }
            }
        }
        return items;
    }

    public int first(@Nonnull GUIItem item) {
        return first(item.getItem());
    }

    public int first(@Nonnull TNLItem item) {
        return first(item.getItemStack());
    }

    public int first(@Nonnull Material material) {
        GUIItem[] contents = getContents();
        for (int slot = 0; slot < getSize(); slot++) {
            GUIItem content = contents[slot];
            if (ItemType.AIR.matches(content) && ItemType.AIR.matches(material)) return slot;
            if (material.equals(content.getItem().getType())) return slot;
        }
        return -1;
    }

    public int first(@Nonnull ItemStack itemStack) {
        return first(itemStack, false);
    }

    public int first(@Nonnull ItemStack itemStack, boolean ignoreAmount) {
        GUIItem[] contents = getContents();
        for (int slot = 0; slot < getSize(); slot++) {
            GUIItem content = contents[slot];
            if (ItemType.AIR.matches(content) && ItemType.AIR.matches(itemStack.getType())) {
                return slot;
            } else {
                if (itemStack.getType().equals(content.getItem().getType())) {
                    if (ignoreAmount) {
                        ItemStack clone = itemStack.clone();
                        clone.setAmount(content.getItem().getAmount());
                        if (clone.equals(content.getItem().getItemStack())) return slot;
                    } else if (itemStack.equals(content.getItem().getItemStack())) return slot;
                }
            }
        }
        return -1;
    }

    public int firstEmpty() {
        GUIItem[] contents = getContents();
        for (int i = 0; i < contents.length; ++i) {
            GUIItem content = contents[i];
            if (content == null || content.getItem().isAir()) return i;
        }
        return -1;
    }

    @Nonnull
    public Pair<Boolean, Integer> remove(@Nonnull GUIItem item) {
        return remove(item.getItem().getItemStack());
    }

    @Nonnull
    public Pair<Boolean, Integer> remove(@Nonnull TNLItem item) {
        return remove(item.getItemStack());
    }

    @Nonnull
    public Pair<Boolean, Integer> remove(@Nonnull ItemStack item) {
        boolean removed = false;
        int amount = 0;
        GUIItem[] contents = getContents();
        for (int i = 0; i < contents.length; i++) {
            GUIItem content = contents[i];
            if ((ItemType.AIR.matches(item) && ItemType.AIR.matches(content)) ||
                    (content != null && content.getItem().getItemStack().equals(item))) {
                remove(i);
                removed = true;
                amount++;
            }
        }
        return new Pair<>(removed, amount);
    }

    @Nonnull
    public Pair<Boolean, Integer> remove(@Nonnull Material material) {
        boolean removed = false;
        int amount = 0;
        GUIItem[] contents = getContents();
        for (int slot = 0; slot < contents.length; slot++) {
            GUIItem content = contents[slot];
            if (ItemType.AIR.matches(material) && ItemType.AIR.matches(content) && content.getItem().getType().equals(material)) {
                remove(slot);
                removed = true;
                amount++;
            }
        }
        return new Pair<>(removed, amount);
    }

    @Nonnull
    public GUI remove(int i) {
        contentHashMap.remove(i);
        return this;
    }

    @Nonnull
    public GUI clear() {
        contentHashMap.clear();
        return this;
    }

    @Nonnull
    @Override
    public Iterator<GUIItem> iterator() {
        return new GUIIterator(this);
    }

    @Nonnull
    public ListIterator<GUIItem> iterator(int i) {
        return new GUIIterator(this, i);
    }

    private int firstPartial(@Nonnull GUIItem item) {
        return firstPartial(item.getItem());
    }

    private int firstPartial(@Nonnull TNLItem item) {
        return firstPartial(item.getItemStack());
    }

    private int firstPartial(@Nonnull ItemStack itemStack) {
        GUIItem[] contents = getContents();
        for (int slot = 0; slot < contents.length; slot++) {
            GUIItem item = contents[slot];
            if (ItemType.AIR.matches(itemStack) && ItemType.AIR.matches(item)) return slot;
            if (item != null && item.getItem().getAmount() < item.getItem().getMaxStackSize() &&
                    item.getItem().getItemStack().equals(itemStack)) return slot;
        }
        return -1;
    }

    public int firstPartial(@Nonnull Material material) {
        GUIItem[] contents = getContents();
        for (int slot = 0; slot < contents.length; slot++) {
            GUIItem item = contents[slot];
            if (ItemType.AIR.matches(material) && ItemType.AIR.matches(item)) return slot;
            if (item != null && item.getItem().getType() == material &&
                    item.getItem().getAmount() < item.getItem().getMaxStackSize()) return slot;
        }
        return -1;
    }

    @Nonnull
    @Override
    public GUI clone() {
        GUI gui = new GUI(getRows(), getMaxStackSize(), getTitle());
        gui.setContents(getContents());
        gui.setOpenListener(getOpenListener());
        gui.setOpenSound(getOpenSound());
        gui.setCloseSound(getCloseSound());
        return gui;
    }

    public interface OpenEvent {
        boolean onOpen(@Nonnull TNLPlayer player);
    }

    public interface CloseEvent {
        boolean onClose(@Nonnull TNLPlayer player, boolean server);
    }

    public interface ClickEvent {
        void onClick(@Nonnull TNLPlayer player, int slot, @Nonnull Interaction.Type type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GUI gui)) return false;
        if (size != gui.size) return false;
        if (maxStackSize != gui.maxStackSize) return false;
        if (!contentHashMap.equals(gui.contentHashMap)) return false;
        if (!title.equals(gui.title)) return false;
        return type == gui.type;
    }

    @Override
    public int hashCode() {
        int result = contentHashMap.hashCode();
        result = 31 * result + viewers.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + openListener.hashCode();
        result = 31 * result + closeListener.hashCode();
        result = 31 * result + clickListener.hashCode();
        result = 31 * result + (openSound != null ? openSound.hashCode() : 0);
        result = 31 * result + (closeSound != null ? closeSound.hashCode() : 0);
        result = 31 * result + size;
        result = 31 * result + maxStackSize;
        result = 31 * result + type.hashCode();
        return result;
    }
}
