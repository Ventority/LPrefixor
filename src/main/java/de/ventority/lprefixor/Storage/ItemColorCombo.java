package de.ventority.lprefixor.Storage;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;

public class ItemColorCombo {
    private ChatColor color;
    private ItemStack item;

    public ItemColorCombo(ChatColor color, ItemStack item) {
        this.color = color;
        this.item = item;
    }

    public ChatColor getColor() {
        return color;
    }

    public ItemStack getItem() {
        return item;
    }
}
