package io.github.itokagimaru.shulkerBoxOpener.Listner;

import io.github.itokagimaru.shulkerBoxOpener.ShulkerBoxOpener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PickupListener implements Listener {
    @EventHandler
    public static void PlayerPickupListener(EntityPickupItemEvent event){
        if(event.getEntity() instanceof Player player){
            Bukkit.getScheduler().runTask(ShulkerBoxOpener.getInstance(), ()->{
                player.updateInventory();
            });
        }
    }
}
