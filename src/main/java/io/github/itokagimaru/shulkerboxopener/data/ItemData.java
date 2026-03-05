package io.github.itokagimaru.shulkerboxopener.data;

import io.github.itokagimaru.shulkerboxopener.ShulkerBoxOpener;

public class ItemData {
    public static BooleanKey IS_OPEN = new BooleanKey(ShulkerBoxOpener.getKey("is_open"), () -> false);
}
