package de.ventority.lprefixor;

import de.ventority.lprefixor.PrefixOptions.colorPermissionsWithItemStack;
import de.ventority.lprefixor.PrefixOptions.fadePermissionsWithItemStack;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.material.
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PrefixGUICreator {
    private final Player p;
    private final colorPermissionsWithItemStack[] personalColors;
    private final fadePermissionsWithItemStack[] personalFades;
    private final User u;
    public PrefixGUICreator(Player p) {
        this.p = p;
        u = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(p);
        personalFades = generateFadeArray();
        personalColors = generateColorArray();
    }
    public void colorSelector(int site) {
        String suffix = personalFadeAmount()+personalColorAmount() >= 28 ? (ChatColor.GRAY + " | S: " + (site + 1)) : "";
        String titleText = LPrefixor.serverHandler.getServerName() + ChatColor.RESET + ChatColor.DARK_GRAY + " Prefix" + suffix;
        Inventory gui = Bukkit.createInventory(p, 54, titleText);
        fillInventoryWithPerms(gui, site);
        addSiteButtons(gui, site);
        addReset(gui);
        p.openInventory(gui);
    }

    private void fillInventoryWithPerms(Inventory gui, int site) {
        fillBorder(gui);
        int spalte = 0;
        int zeile = 1;
        int colorsInGUI = 0;
        int sumOfColorsInGUI = 28 * site;
        for (int j = 0; j < 28; j++) {
            if (colorsInGUI >= 28)
                break;
            if (sumOfColorsInGUI >= personalColors.length)
                break;
            colorPermissionsWithItemStack color = personalColors[sumOfColorsInGUI];
            if (u.getCachedData().getPermissionData().checkPermission(color.permission).asBoolean()) {
                gui.setItem(spalte + 1 + zeile * 9, color.item);
                colorsInGUI++;
                sumOfColorsInGUI++;
                spalte++;
                if (spalte % 7 == 0) {
                    zeile++;
                    spalte = 0;
                }
            }
        }

        for (int j = 0; j < 28; j++) {
            if (colorsInGUI >= 28)
                break;
            if (sumOfColorsInGUI - personalColors.length >= personalFades.length)
                break;
            fadePermissionsWithItemStack fade = personalFades[sumOfColorsInGUI - personalColors.length];
            if (u.getCachedData().getPermissionData().checkPermission(fade.permission).asBoolean()) {
                gui.setItem(spalte + 1 + zeile * 9, fade.item);
                colorsInGUI++;
                sumOfColorsInGUI++;
                spalte++;
                if (spalte % 7 == 0) {
                    zeile++;
                    spalte = 0;
                }
            }
        }
    }

    private void fillBorder(Inventory gui) {
        ItemStack stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(" ");
        for (int i = 0; i < 9; i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            gui.getItem(i).setItemMeta(meta);
        }
        for (int i = 1; i < 5; i++) {
            gui.setItem(9 * i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            gui.getItem(9 * i).setItemMeta(meta);
            gui.setItem(8 + 9 * i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            gui.getItem(8 + 9 * i).setItemMeta(meta);
        }
        for (int i = 0; i < 9; i++) {
            gui.setItem(i + 5 * 9, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            gui.getItem(i + 5 * 9).setItemMeta(meta);
        }
    }

    private void addReset(Inventory gui) {
        ItemStack reset = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = reset.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Zurücksetzen");
        reset.setItemMeta(meta);
        gui.setItem(49, reset);
    }

    private void addSiteButtons(Inventory gui, int site) {
        if (personalColorAmount()+personalFadeAmount() >= 28 + 28*site) {
            ItemStack next = new ItemStack(Material.LIME_DYE, 1);
            ItemMeta metaNext = next.getItemMeta();
            metaNext.setDisplayName(ChatColor.GREEN + "Nächste Seite");
            next.setItemMeta(metaNext);
            gui.setItem(50, next);
        }
        if (site != 0) {
            ItemStack previous = new ItemStack(Material.LIME_DYE, 1);
            ItemMeta metaPrevious = previous.getItemMeta();
            metaPrevious.setDisplayName(ChatColor.GREEN + "Vorherige Seite");
            previous.setItemMeta(metaPrevious);
            gui.setItem(48, previous);
        }
    }

    private int personalColorAmount() {
        int counter = 0;
        for (int i = 0; i < colorPermissionsWithItemStack.values().length; i++) {
            colorPermissionsWithItemStack color = colorPermissionsWithItemStack.values()[i];
            if (u.getCachedData().getPermissionData().checkPermission(color.permission).asBoolean())
                counter++;
        }
        return counter;
    }

    private int personalFadeAmount() {
        int counter = 0;
        for (fadePermissionsWithItemStack fade : fadePermissionsWithItemStack.values())
            if (u.getCachedData().getPermissionData().checkPermission(fade.permission).asBoolean())
                counter++;
        return counter;
    }

    private colorPermissionsWithItemStack[] generateColorArray() {
        colorPermissionsWithItemStack[] arr = new colorPermissionsWithItemStack[personalColorAmount()];
        int pos = 0;
        for (colorPermissionsWithItemStack color : colorPermissionsWithItemStack.values()) {
            if (u.getCachedData().getPermissionData().checkPermission(color.permission).asBoolean()) {
                arr[pos] = color;
                pos++;
            }
        }
        return arr;
    }

    private fadePermissionsWithItemStack[] generateFadeArray() {
        fadePermissionsWithItemStack[] arr = new fadePermissionsWithItemStack[personalFadeAmount()];
        int pos = 0;
        for (fadePermissionsWithItemStack fade : fadePermissionsWithItemStack.values()) {
            if (u.getCachedData().getPermissionData().checkPermission(fade.permission).asBoolean()) {
                arr[pos] = fade;
                pos++;
            }
        }
        return arr;
    }
}
