package io.github.itokagimaru.shulkerBoxOpener.data;

import org.bukkit.NamespacedKey;

public class ItemData {
    private static final String NAMESPACE = "shulker_box_opener";

    /**
     * Get key instance for pdc container id
     *
     * @param key key
     * @return New instance of NamespacedKey
     */
    private static NamespacedKey getKey(String key) {
        return new NamespacedKey(NAMESPACE, key);
    }
    public static ByteKey isOpen = new ByteKey(getKey("is_open"), () -> (byte) 0);
}
