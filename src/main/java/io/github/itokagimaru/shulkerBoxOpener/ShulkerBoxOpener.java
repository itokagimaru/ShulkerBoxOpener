package io.github.itokagimaru.shulkerBoxOpener;

import io.github.itokagimaru.shulkerBoxOpener.Listner.ClickInventoryListener;
import io.github.itokagimaru.shulkerBoxOpener.Listner.CloseInventoryListeners;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShulkerBoxOpener extends JavaPlugin {
    public static ShulkerBoxOpener instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        registerListeners(
            new ClickInventoryListener(),
            new CloseInventoryListeners()
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
