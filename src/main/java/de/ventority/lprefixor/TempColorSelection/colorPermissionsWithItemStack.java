package de.ventority.lprefixor.TempColorSelection;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum colorPermissionsWithItemStack {
    yellow(new ItemStack(Material.YELLOW_DYE, 1),   "cpdisplay.prefix.yellow",    ChatColor.YELLOW, ChatColor.YELLOW + "Gelb"),
    orange(new ItemStack(Material.ORANGE_DYE, 1),   "cpdisplay.prefix.orange",    ChatColor.GOLD, ChatColor.GOLD + "Orange"),
    red(new ItemStack(Material.RED_DYE, 1),         "cpdisplay.prefix.red",       ChatColor.RED, ChatColor.RED + "Hellrot"),
    lightGreen(new ItemStack(Material.LIME_DYE, 1), "cpdisplay.prefix.lightGreen",ChatColor.GREEN, ChatColor.GREEN + "Grün"),
    darkGreen(new ItemStack(Material.GREEN_DYE, 1), "cpdisplay.prefix.darkGreen", ChatColor.DARK_GREEN, ChatColor.DARK_GREEN + "Dunkelgrün"),
    lightBlue(new ItemStack(Material.LIGHT_BLUE_DYE, 1), "cpdisplay.prefix.lightBlue", ChatColor.BLUE, ChatColor.BLUE + "Blau"),
    darkBlue(new ItemStack(Material.BLUE_DYE, 1),   "cpdisplay.prefix.darkBlue",  ChatColor.DARK_BLUE, ChatColor.DARK_BLUE + "Dunkelblau"),
    aqua(new ItemStack(Material.PRISMARINE_CRYSTALS, 1),       "cpdisplay.prefix.aqua",      ChatColor.AQUA, ChatColor.AQUA + "Hellblau"),
    white(new ItemStack(Material.WHITE_DYE, 1),     "cpdisplay.prefix.white",     ChatColor.WHITE, ChatColor.WHITE + "Weiß"),
    black(new ItemStack(Material.BLACK_DYE, 1),     "cpdisplay.prefix.black",     ChatColor.BLACK, ChatColor.BLACK + "Schwarz"),
    lightPurple(new ItemStack(Material.PINK_DYE, 1), "cpdisplay.prefix.lightPurple", ChatColor.LIGHT_PURPLE, ChatColor.LIGHT_PURPLE + "Rosa"),
    purple(new ItemStack(Material.PURPLE_DYE, 1), "cpdisplay.prefix.purple", ChatColor.DARK_PURPLE, ChatColor.DARK_PURPLE + "Purple"),
    DARKAQUA(new ItemStack(Material.BLUE_DYE, 1), "cpdisplay.prefix.DarkAqua", ChatColor.DARK_AQUA, ChatColor.DARK_AQUA + "Dunekelaqua"),
    darkRed(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1), "cpdisplay.prefix.darkRed", ChatColor.DARK_RED, ChatColor.DARK_RED + "Dunkelrot");

    public final ItemStack item;
    public final String permission;
    public final ChatColor color;
    public final String name;
    colorPermissionsWithItemStack(ItemStack item, String permission, ChatColor color, String name) {
        this.permission = permission;
        this.color = color;
        this.name = name;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color + name);
        item.setItemMeta(meta);
        this.item = item;
    }
}
