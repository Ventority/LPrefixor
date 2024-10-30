package de.ventority.lprefixor.Storage;

import de.ventority.lprefixor.LPrefixor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.awt.*;
import java.io.*;
import java.util.*;

public class StorageFileHandler {
    private static final String file = System.getProperty("os.name").contains("Windows") ?
            ".\\plugins\\LPrefixor.txt" : "./plugins/LPrefixor.txt";
    private static HashMap<String, ColorProps> readColors = new HashMap<>();

    public void parseFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String curLine = reader.readLine();
            while (curLine != null) {
                String[] parts = curLine.split("color='");
                String color = parts[1].split("'")[0];
                parts = curLine.split("name='");
                String name = parts[1].split("'")[0];
                parts = curLine.split("item='");
                String itemString = parts[1].split("'")[0];
                try {
                    addColor(name, color, Material.valueOf(itemString.toUpperCase()));
                }catch(Exception e) {
                    System.out.println("A Material ('" + itemString + "could not be loaded.");
                }
                curLine = reader.readLine();
            }
        } catch (Exception e1) {
            File f = new File(file);
            try {
                f.createNewFile();
            } catch (Exception e2) {
                System.out.println("Prefixes currently dont work, there's something wrong with the storage file. " +
                        "Worst-case: Save your custom colors and delete the file." + '\n');
                e2.printStackTrace();
            }
        }
    }

    public void addColor(String name, String color, Material material) {
        try {
            checkForCorrectColor(name, color);
            ItemStack item = new ItemStack(material, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.of(color) + name);
            addNBTData(meta, "lprefixor.type", "color");
            addNBTData(meta, "color", color);
            item.setItemMeta(meta);
            readColors.put(name, new ColorProps(ChatColor.of(Color.decode(color)), item, new Permission("lprefixor.color." + name)));
        } catch (ColorNotCorrectException e) {
            System.out.println("Adding color failed. Stacktrace: ");
            e.printStackTrace();
        }
        writeListToFile();
    }

    public void removeColor(String name) {
        readColors.remove(name);
        writeListToFile();
    }

    public void writeListToFile() {
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));) {
            for (Map.Entry<String, ColorProps> entry : readColors.entrySet()) {
                bw.write("name='" + entry.getKey() + "',color='" + entry.getValue().getColor() + "',item='"
                        + Objects.requireNonNull(entry.getValue().getItem().getData()).getItemType().toString() + "'");
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("There has been an error saving the LPrefixor storage file! Newly added colors might be lost.");
            e.printStackTrace();
        }
    }

    private void checkForCorrectColor(String name, String color) throws ColorNotCorrectException {
        if (readColors.containsKey(name))
            throw new ColorNotCorrectException(0);

        for (ColorProps colorProps : readColors.values()) {
            if (colorProps.getColor().equals(ChatColor.of(Color.decode(color))))
                throw new ColorNotCorrectException(1);
        }
    }

    private void addNBTData(ItemMeta meta, String name, String value) {
        NamespacedKey key = new NamespacedKey(LPrefixor.serverHandler.getPlugin(), name);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, PersistentDataType.STRING, value);
    }

    public HashMap<String, ColorProps> getColors() {
        return readColors;
    }
}
