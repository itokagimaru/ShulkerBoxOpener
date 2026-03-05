package io.github.itokagimaru.shulkerboxopener;

import io.github.itokagimaru.shulkerboxopener.Listner.ClickInventoryListener;
import io.github.itokagimaru.shulkerboxopener.Listner.PickupListener;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShulkerBoxOpener extends JavaPlugin {
    public static final String PLUGIN_NAME = "ShulkerBoxOpener";
    public static final String PLUGIN_ID = "shulker_box_opener";

    public static NamespacedKey getKey(String key) {
        return new NamespacedKey(ShulkerBoxOpener.PLUGIN_ID, key);
    }

    public static ShulkerBoxOpener instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        registerListeners(
            new ClickInventoryListener(),
            new PickupListener()
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ShulkerBoxOpener getInstance() {
        return instance;
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pluginManager = getServer().getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }
}
