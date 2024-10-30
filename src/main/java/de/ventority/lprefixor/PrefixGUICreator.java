package de.ventority.lprefixor;

import de.ventority.lprefixor.Storage.ColorProps;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class PrefixGUICreator {
    private final Player p;
    private final User u;

    private final Inventory gui;
    private String lastColor = null;

    private int colorCounter = 0;
    private int column = 1;
    private int row = 1;

    public PrefixGUICreator(Player p) {
        this.p = p;
        u = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(p);
        gui = Bukkit.createInventory(p, 54, LPrefixor.serverHandler.getServerName()
                + ChatColor.RESET + ChatColor.DARK_GRAY + " Prefix");
    }
    public void colorSelector() {
        fillBorder();
        addReset();
        addSiteButtons();
        fillInventoryWithColors();
        p.openInventory(gui);
    }

    private void fillInventoryWithColors() {
        TreeMap<String, ColorProps> sortedMap = new TreeMap<>(LPrefixor.serverHandler.getFileHandler().getColors());
        sortedMap = lastColor == null ? sortedMap : (TreeMap<String, ColorProps>) sortedMap.tailMap(lastColor);
        Iterator<Map.Entry<String, ColorProps>> iterator = sortedMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, ColorProps> entry = iterator.next();
            addToInv(entry);
        }
    }

    private void addToInv(Map.Entry<String, ColorProps> entry) {
        gui.addItem(entry.getValue().getItem());
        row = (row == 4 ? 1 : row + 1);
        column = (column == 7 ? 1 : column + 1);
    }

    private boolean endIsReached() {
        return colorCounter == 27;
    }

    private void fillBorder() {
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

    private void addReset() {
        ItemStack reset = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = reset.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Zurücksetzen");
        addNBTData(meta, "lprefixor.type", "button");
        addNBTData(meta, "lprefixor.action", "reset");
        reset.setItemMeta(meta);
        gui.setItem(49, reset);
    }

    private void addSiteButtons() {
        ItemStack next = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta meta = next.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Next");
        addNBTData(meta, "lprefixor.type", "button");
        addNBTData(meta, "lprefixor.action", "next");
        next.setItemMeta(meta);
        gui.setItem(50, next);

        ItemStack prev = new ItemStack(Material.RED_DYE, 1);
        meta = next.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Previous");
        addNBTData(meta, "lprefixor.type", "button");
        addNBTData(meta, "lprefixor.action", "previous");
        prev.setItemMeta(meta);
        gui.setItem(48, prev);
    }

    private void addNBTData(ItemMeta meta, String name, String value) {
        NamespacedKey key = new NamespacedKey(LPrefixor.serverHandler.getPlugin(), name);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, PersistentDataType.STRING, value);
    }
}
