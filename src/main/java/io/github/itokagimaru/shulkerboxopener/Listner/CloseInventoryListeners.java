package io.github.itokagimaru.shulkerboxopener.Listner;


import io.github.itokagimaru.shulkerboxopener.gui.BaseGuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class CloseInventoryListeners implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // is player
        if (!(event.getPlayer() instanceof Player player)) return;

        // get inventory and check
        Inventory inv = event.getInventory();
        if (!(inv.getHolder() instanceof BaseGuiHolder guiHolder)) return;
        // call onClose
        guiHolder.onClose(player);
    }
}
