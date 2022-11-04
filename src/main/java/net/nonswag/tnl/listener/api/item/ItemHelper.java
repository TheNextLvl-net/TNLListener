package net.nonswag.tnl.listener.api.item;

import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

public abstract class ItemHelper {

    protected ItemHelper() {
    }

    public abstract void setMaxStackSize(Material material, int maxStackSize);

    public void setMaxStackSize(ItemType item, int maxStackSize) {
        for (Material material : item.all(ItemType.Selector.ANY)) setMaxStackSize(material, maxStackSize);
    }

    public abstract void setDurability(Material material, int durability);

    public void setDurability(ItemType item, int durability) {
        for (Material material : item.all(ItemType.Selector.ANY)) setDurability(material, durability);
    }

    public boolean removeInventoryItems(TNLPlayer player, ItemStack item) {
        int amount = 0;
        int needed = item.getAmount();
        for (@Nullable ItemStack itemStack : player.inventoryManager().getInventory().getContents()) {
            if (itemStack == null) continue;
            if (!itemStack.getType().equals(item.getType())) continue;
            if (!Objects.equals(itemStack.getItemMeta(), item.getItemMeta())) continue;
            int am = itemStack.getAmount();
            if (amount + am <= needed) player.inventoryManager().getInventory().removeItem(itemStack);
            else itemStack.setAmount(am - (needed - amount));
            amount += Math.min(am, needed);
            if (amount >= needed) break;
        }
        if (amount >= needed) return true;
        ItemStack clone = item.clone();
        clone.setAmount(amount);
        player.inventoryManager().getInventory().addItem(clone);
        return false;
    }

    @SuppressWarnings("NullableProblems")
    public boolean removeInventoryItems(TNLPlayer player, Material type, int amount) {
        int ra = amount;
        boolean succeeded = false;
        for (@Nullable ItemStack is : player.inventoryManager().getInventory().getStorageContents()) {
            if (is != null && is.getType() == type) {
                int a = is.getAmount();
                if (a >= ra) {
                    int am = a - ra;
                    player.inventoryManager().getInventory().removeItem(is);
                    player.inventoryManager().getInventory().addItem(new ItemStack(is.getType(), am));
                    succeeded = true;
                    ra = 0;
                    break;
                }
                ra -= a;
                player.inventoryManager().getInventory().removeItem(is);
            }
        }
        if (!succeeded) {
            int back = amount - ra;
            if (back != 0) {
                ItemStack is = new ItemStack(type, back);
                if (is.getAmount() != 0) player.inventoryManager().getInventory().addItem(is);
            }
        }
        return succeeded;
    }

    @SuppressWarnings("NullableProblems")
    public int getAmount(Inventory inventory, Material material) {
        int amount = 0;
        for (@Nullable ItemStack item : inventory.getStorageContents()) {
            if (item != null && item.getType().equals(material)) amount += item.getAmount();
        }
        return amount;
    }

    public abstract Map<Material, Float> getCompostableItems();

    public boolean isCompostable(Material material) {
        return getCompostableItems().containsKey(material);
    }

    public static ItemHelper getInstance() {
        return Mapping.get().itemHelper();
    }
}
