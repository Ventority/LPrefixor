package de.ventority.lprefixor.PrefixOptions;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;

public enum fadePermissionsWithItemStack {
    green("Grün", new ItemStack(Material.GREEN_CONCRETE, 1), "cpdisplay.prefix.greenFade", ChatColor.GREEN, ChatColor.of(new Color(0, 79, 0))),
    blueRed("Blau-Rot", new ItemStack(Material.BLUE_CONCRETE, 1), "cpdisplay.prefix.blueRedFade",
            ChatColor.of(new Color(0, 164, 244)), ChatColor.RED),
    redBlue("Rot-Blau", new ItemStack(Material.BLUE_CONCRETE, 1), "cpdisplay.prefix.redBlueFade",
            ChatColor.of(new Color(251, 8, 8)), ChatColor.of(new Color(103, 222, 239))),
    yellowRed("Gelb-Rot", new ItemStack(Material.YELLOW_CONCRETE, 1), "cpdisplay.prefix.yellowRedFade",
            ChatColor.of(new Color( 222,255,7)), ChatColor.of(new Color(255, 69, 0))),
    redYellow("Rot-Gelb", new ItemStack(Material.RED_CONCRETE, 1), "cpdisplay.prefix.redYellowFade",
            ChatColor.of(new Color(255, 69, 0)), ChatColor.YELLOW),
    dBlueBLue("Darkblue-Blue", new ItemStack(Material.LIGHT_BLUE_CONCRETE, 1), "cpdisplay.prefix.DarkBlueFade",
            ChatColor.of(new Color(55, 60, 255)),ChatColor.of(new Color(84, 248, 255))),
    BluedBLue("Blue-DarkBlue", new ItemStack(Material.LIGHT_BLUE_CONCRETE, 1), "cpdisplay.prefix.Blue-bluedarkfade",
            ChatColor.of(new Color(84, 248, 255)),ChatColor.of(new Color(55, 60, 255))),
    BlueWhite("blue-white", new ItemStack(Material.WHITE_CONCRETE_POWDER, 1), "cpdisplay.prefix.bluewhitefade",
            ChatColor.of(new Color(55, 60, 255)), ChatColor.WHITE),
    whiteblue("white-blue", new ItemStack(Material.WHITE_CONCRETE, 1), "cpdisplay.prefix.whitebluefade",
            ChatColor.WHITE, ChatColor.of(new Color(55, 60, 255))),
    aquaGold("Aqua-Gold", new ItemStack(Material.GOLD_BLOCK, 1), "cpdisplay.prefix.aquaGoldFade",
            ChatColor.of(new Color(103, 222, 244)), ChatColor.GOLD),
    Goldaqua("Gold-Aqua", new ItemStack(Material.GOLD_BLOCK, 1), "cpdisplay.prefix.goldaquaFade",
            ChatColor.GOLD, ChatColor.of(new Color(103, 222, 244))),
    Pinkwhite("Pink-White", new ItemStack(Material.PINK_CONCRETE, 1), "cpdisplay.prefix.PinkwhiteFade",
            ChatColor.LIGHT_PURPLE, ChatColor.WHITE),
    whitePink("Pink-White", new ItemStack(Material.PINK_CONCRETE, 1), "cpdisplay.prefix.whitepinkfade",
            ChatColor.WHITE, ChatColor.LIGHT_PURPLE),
    Bluegreen("Blau-Grün", new ItemStack(Material.GREEN_CONCRETE, 1), "cpdisplay.prefix.bluegreenfade",
            ChatColor.AQUA, ChatColor.of(new Color(107, 250, 55))),
    GrünBlau("Grün-Blau", new ItemStack(Material.GREEN_CONCRETE, 1), "cpdisplay.prefix.greenbluefade",
            ChatColor.of(new Color(107, 250, 55)) ,ChatColor.AQUA),
    BlackWhite("Black-white", new ItemStack(Material.BLACK_CONCRETE, 1), "cpdisplay.prefix.WhiteBlackFade",
            ChatColor.BLACK ,ChatColor.WHITE),;

    /*
    Wenn du eigen Farbverläufe hinzufügen möchtest:
    Suche dir die Hex-Codes der Farben, die du haben willst. Die musst du dann von Hexadezimal zu Dezimal umrechnen,
    sodass du die RGB-Werte bekommst. So kann man ChatColors mit beliebigen Farben erstellen. Du kannst aber auch die
    von Bukkit vorgefertigten ChatColors benutzen, dafür einfach ChatColor.XYZ, wobei XYZ die gegebene Farbe ist, einsetzen.

    fadeName(new ItemStack(Material.MATERIAL_IM_GUI, 1), "cpdisplay.color.permissionName",
            ChatColor.of(new Color(rotwert, grünwert, blauwert)), ChatColor.of(new Color(rot, grün, blau)));

    Die erste Farbe ist die, bei der angefangen wird, die zweite die, bei der aufgehört wird. Den bearbeiteten Code-block
    dann einfach mit einem Komma getrennt oben anhängen. Wenn du eine eigene einfügst, musst du die Permission noch in
    der plugin.yml im package resources registrieren. Schau einfach mal rein, ist eigentlich selbsterklärend.
     */

    public final ChatColor c1;
    public final ChatColor c2;
    public final String permission;
    public final ItemStack item;
    fadePermissionsWithItemStack(String name, ItemStack item, String permission, ChatColor c1, ChatColor c2) {
        this.permission = permission;
        this.item = item;
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(SetColor.removeEnding(SetColor.fadeBetweenColors(c1.getColor(), c2.getColor(), name)));
        item.setItemMeta(meta);
        this.c1 = c1;
        this.c2 = c2;
    }
}
