package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.core.api.file.helper.FileHelper;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.events.inventory.player.InventoryLoadEvent;
import net.nonswag.tnl.listener.events.inventory.player.InventorySaveEvent;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.*;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class InventoryManager extends Manager {

    public PlayerInventory getInventory() {
        return getPlayer().bukkit().getInventory();
    }

    public InventoryView getOpenInventory() {
        return getPlayer().bukkit().getOpenInventory();
    }

    public Inventory getEnderChest() {
        return getPlayer().bukkit().getEnderChest();
    }

    @Nullable
    public InventoryView openInventory(Inventory inventory) {
        return getPlayer().bukkit().openInventory(inventory);
    }

    public void openInventory(InventoryView view) {
        getPlayer().bukkit().openInventory(view);
    }

    @Nullable
    public InventoryView openWorkbench(@Nullable Location location, boolean force) {
        return getPlayer().bukkit().openWorkbench(location, force);
    }

    @Nullable
    public InventoryView openEnchanting(@Nullable Location location, boolean force) {
        return getPlayer().bukkit().openEnchanting(location, force);
    }

    @Nullable
    public InventoryView openMerchant(Villager villager, boolean force) {
        return getPlayer().bukkit().openMerchant(villager, force);
    }

    @Nullable
    public InventoryView openMerchant(Merchant merchant, boolean force) {
        return getPlayer().bukkit().openMerchant(merchant, force);
    }

    public void closeInventory() {
        Bootstrap.getInstance().sync(() -> {
            getPlayer().bukkit().closeInventory();
            getPlayer().interfaceManager().closeGUI(false);
        });
    }

    public MainHand getMainHand() {
        return getPlayer().bukkit().getMainHand();
    }

    public void dropItem(boolean dropAll) {
        Bootstrap.getInstance().sync(() -> getPlayer().bukkit().dropItem(false));
    }

    public abstract void dropItem(ItemStack item, Consumer<Item> after);

    public boolean setWindowProperty(InventoryView.Property property, int value) {
        return getPlayer().bukkit().setWindowProperty(property, value);
    }

    public void saveInventoryContents(String id) {
        saveInventoryContents(id, getInventory().getContents());
    }

    public void saveInventoryContents(String id, ItemStack[] contents) {
        try {
            if (!new InventorySaveEvent(getPlayer(), id).call()) return;
            File file = new File(new File("plugins/Listener/Inventories/"), getPlayer().getUniqueId() + ".yml");
            FileHelper.create(file);
            YamlConfiguration inventory = YamlConfiguration.loadConfiguration(file);
            inventory.set(id, Arrays.asList(contents));
            inventory.save(file);
        } catch (IOException e) {
            Logger.error.println(e.getMessage());
        }
    }

    public void loadInventoryContents(String id) {
        ItemStack[] contents = getInventoryContents(id);
        if (contents.length == 0 || !new InventoryLoadEvent(getPlayer(), id).call()) return;
        getInventory().clear();
        getInventory().setContents(contents);
    }

    public ItemStack[] getInventoryContents(String id) {
        ItemStack[] items = new ItemStack[]{};
        File file = new File(new File("plugins/Listener/Inventories/"), getPlayer().getUniqueId() + ".yml");
        if (!file.exists()) return items;
        try {
            YamlConfiguration inventory = YamlConfiguration.loadConfiguration(file);
            if (!inventory.isSet(id)) return items;
            List<?> contents = inventory.getList(id);
            if (contents == null) return items;
            items = new ItemStack[contents.size()];
            for (int i = 0; i < contents.size(); i++) items[i] = (ItemStack) contents.get(i);
        } catch (Exception e) {
            Logger.error.println(e);
        }
        return items;
    }

    public ItemStack getItemInMainHand() {
        return getPlayer().bukkit().getInventory().getItemInMainHand();
    }

    public ItemStack getItemInOffHand() {
        return getPlayer().bukkit().getInventory().getItemInOffHand();
    }

    public int getHotbarSlot() {
        return getPlayer().bukkit().getInventory().getHeldItemSlot();
    }

    public void setHotbarSlot(int slot) {
        getPlayer().bukkit().getInventory().setHeldItemSlot(slot);
    }

    public void updateInventory() {
        getPlayer().bukkit().updateInventory();
    }
}
