package io.github.itokagimaru.shulkerBoxOpener.gui;

import io.github.itokagimaru.shulkerBoxOpener.ShulkerBoxOpener;
import io.github.itokagimaru.shulkerBoxOpener.data.ItemData;
import org.bukkit.Bukkit;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ShulkerOpenGUI extends BaseGuiHolder {
    private final Inventory beforeInv;
    ItemStack shulkerBoxItem;

    public ShulkerOpenGUI(Inventory beforeInv, ItemStack shulkerBoxItem) {
        this.beforeInv = beforeInv;
        this.shulkerBoxItem = shulkerBoxItem;
        inv = Bukkit.createInventory(this, InventoryType.SHULKER_BOX, shulkerBoxItem.displayName());
    }

    public boolean setup() {
        if (shulkerBoxItem.getItemMeta() instanceof BlockStateMeta meta) {
            if (meta.getBlockState() instanceof ShulkerBox shulkerBox) {
                Inventory shulkerInv = shulkerBox.getInventory();
                // 中身を取得
                ItemStack[] contents = shulkerInv.getContents().clone();
                inv.setContents(contents);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem().clone();
        if (ItemData.isOpen.get(clicked) == (byte) 1) event.setCancelled(true);
    }

    @Override
    public void onClose(Player player) {
        if (closeFlag) {
            closeFlag = false;
            ItemData.isOpen.set(shulkerBoxItem, (byte) 0);
            ItemMeta meta = shulkerBoxItem.getItemMeta();
            BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
            ShulkerBox shulkerBoxMeta = (ShulkerBox) blockStateMeta.getBlockState();
            shulkerBoxMeta.getInventory().setContents(inv.getContents().clone());
            blockStateMeta.setBlockState(shulkerBoxMeta);
            shulkerBoxItem.setItemMeta(blockStateMeta);

            Bukkit.getScheduler().runTask(ShulkerBoxOpener.getInstance(), () -> {
                player.give(shulkerBoxItem);
                player.updateInventory();
                if (beforeInv.getType() != InventoryType.CRAFTING) {
                    player.openInventory(beforeInv);
                }

            });
        }
    }


}
