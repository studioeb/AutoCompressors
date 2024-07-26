package io.github.rainpaw.autocompressors.guis;

import io.github.rainpaw.autocompressors.utils.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BaseGUI {
    private final Inventory inventory;
    private final UUID uuid;
    private final Map<Integer, InputAction> actions = new HashMap<>();

    // CONSTRUCTOR //
    public BaseGUI(int invSize, String invName) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, invSize, invName);
        GUIUtils.inventoriesByUUID.put(uuid, this);
    }

    public interface InputAction {
        void click(Player player);
    }

    // ADDING ITEMS //
    public void setItem(int slot, ItemStack itemStack, InputAction action) {
        inventory.setItem(slot, itemStack);
        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void setItem(int slot, ItemStack itemStack) {
        setItem(slot, itemStack, null);
    }

    public Map<Integer, ItemStack> addItem(ItemStack itemStack, InputAction action) {
        if (action != null && inventory.firstEmpty() > -1) {
            actions.put(inventory.firstEmpty(), action);
        }
        return inventory.addItem(itemStack);
    }

    public Map<Integer, ItemStack> addItem(ItemStack itemStack) {
        return addItem(itemStack, null);
    }

    // OPENING AND CLOSING INVENTORY //
    public void open(Player player) {
        player.openInventory(inventory);
        GUIUtils.openInventories.put(player.getUniqueId(), uuid);
    }

    public void close(Player player) {
        player.closeInventory();
        GUIUtils.openInventories.remove(player.getUniqueId());
    }

    public void delete() {
        for (Player player : Bukkit.getOnlinePlayers()){
            UUID u = GUIUtils.openInventories.get(player.getUniqueId());
            if (u.equals(getUuid())){
                player.closeInventory();
            }
        }
        GUIUtils.inventoriesByUUID.remove(getUuid());
    }

    // GETTERS //
    public Inventory getInventory() {
        return inventory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Map<Integer, InputAction> getActions() {
        return actions;
    }
}
