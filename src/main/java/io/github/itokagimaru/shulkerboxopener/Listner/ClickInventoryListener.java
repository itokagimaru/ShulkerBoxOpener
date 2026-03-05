package io.github.itokagimaru.shulkerboxopener.Listner;

import io.github.itokagimaru.shulkerboxopener.gui.BaseGuiHolder;
import io.github.itokagimaru.shulkerboxopener.gui.ShulkerOpenGUI;
import java.util.Set;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickInventoryListener implements Listener {
    // supported inventory types
    private static final Set<InventoryType> SUPPORTED_TYPES = Set.of(
            InventoryType.PLAYER,
            InventoryType.CRAFTING,
            InventoryType.CHEST,
            InventoryType.BARREL
    );

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack clicked = event.getCurrentItem();

        // check if top inventory is GUI
        if ((event.getView().getTopInventory().getHolder() instanceof BaseGuiHolder baseGuiHolder)) {
            if (clicked != null && clicked.getType() != Material.AIR) {
                baseGuiHolder.onClick(event);
            }
            return;
        }

        // check if supported inventory to open shulker box
        if(shouldOpenShulkerBoxGUI(event)) {
            event.setCancelled(true);

            // clone stack and remove original stack to prevent item duplication bug
            ItemStack clickedClone = clicked.clone();
            clicked.setAmount(0);

            // open shulker box GUI
            openShulkerBoxGUI(player, event.getInventory(), clickedClone);
            return;
        }
    }

    /**
     * check if the player can open shulker box GUI
     * @param event target inventory click event
     * @return whether the player can open shulker box GUI or not
     */
    protected static boolean shouldOpenShulkerBoxGUI(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        InventoryType type = event.getView().getTopInventory().getType();

        return (event.isRightClick() && event.isShiftClick())
                && clicked != null
                && clicked.getType() == Material.SHULKER_BOX
                && SUPPORTED_TYPES.contains(type);
    }

    /**
     * open shulker box GUI
     * @param player target player
     * @param beforeInv current inventory
     * @param shulkerBoxItem clicked shulker box item
     * @return open success or not
     */
    protected static boolean openShulkerBoxGUI(Player player, Inventory beforeInv, ItemStack shulkerBoxItem) {
        ShulkerOpenGUI shulkerOpenGUI = new ShulkerOpenGUI(beforeInv, shulkerBoxItem);
        boolean setupResult = shulkerOpenGUI.setup();
        if (setupResult) {
            player.openInventory(shulkerOpenGUI.getInventory());
        } else {
            player.sendMessage(Component.text("シュルカーボックスの読み込みに失敗しました").color(NamedTextColor.RED));
        }
        return setupResult;
    }

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
