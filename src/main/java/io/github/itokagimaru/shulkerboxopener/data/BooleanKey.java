package io.github.itokagimaru.shulkerboxopener.data;

import java.util.function.Supplier;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class BooleanKey extends UsefulKey<Boolean> {
    public BooleanKey(NamespacedKey key, Supplier<Boolean> defaultValue) {
        super(key, PersistentDataType.BOOLEAN, defaultValue);
    }
}
