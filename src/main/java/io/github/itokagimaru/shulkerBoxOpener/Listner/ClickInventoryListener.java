package io.github.itokagimaru.shulkerBoxOpener.Listner;

import io.github.itokagimaru.shulkerBoxOpener.ShulkerBoxOpener;
import io.github.itokagimaru.shulkerBoxOpener.data.ItemData;
import io.github.itokagimaru.shulkerBoxOpener.gui.BaseGuiHolder;
import io.github.itokagimaru.shulkerBoxOpener.gui.ShulkerOpenGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class ClickInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        if (inv == null) return;
        InventoryView view = event.getView();
        InventoryType type = view.getTopInventory() == null
                ? InventoryType.PLAYER
                : view.getTopInventory().getType();
        if ((event.getView().getTopInventory().getHolder() instanceof BaseGuiHolder baseGuiHolder)) {
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType() == Material.AIR) return;
            baseGuiHolder.onClick(event);
        } else if (type == InventoryType.PLAYER || type == InventoryType.CRAFTING || type == InventoryType.CHEST || type == InventoryType.BARREL) {
            openShulkerBox(event);
        }
    }

    private void openShulkerBox(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        ItemStack clicked = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (clicked == null || clicked.getType() == Material.AIR) return;
        if (!(event.isRightClick() && event.isShiftClick())) return;
        if (clicked.getType() != Material.SHULKER_BOX) return;
        event.setCancelled(true);
        ItemData.isOpen.set(clicked, (byte) 1);
        ShulkerOpenGUI shulkerOpenGUI = new ShulkerOpenGUI(inv, clicked);
        if (shulkerOpenGUI.setup()) player.openInventory(shulkerOpenGUI.getInventory());
        else player.sendMessage(Component.text("shulkerboxの読み込みに失敗しました").color(NamedTextColor.RED));
    }
}
