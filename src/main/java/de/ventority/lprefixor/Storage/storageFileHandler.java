package de.ventority.lprefixor.Storage;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class storageFileHandler {
    private static final String file = "LPrefixor.txt";
    private static HashMap<String, ItemColorCombo> readColors = new HashMap<>();

    public void parseFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String curLine = reader.readLine();
            while (curLine != null) {
                ChatColor chatColor;
                String name;
                ItemStack item;
                String[] parts = curLine.split("color='");
                String color = parts[1].split("'")[0];
                chatColor = ChatColor.getByChar(color.charAt(1));
                parts = curLine.split("name='");
                name = parts[1].split("'")[0];
                parts = curLine.split("item='");
                String itemString = parts[1].split("'")[0];
                try {
                    item = new ItemStack(Objects.requireNonNull(Material.matchMaterial(itemString)), 1);
                    readColors.put(name, new ItemColorCombo(chatColor, item));
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
                System.out.println("Prefixe funktionieren gerade nicht, es gab ein Problem mit der Speicher-Datei!" + '\n');
                e2.printStackTrace();
            }
        }
    }

    public void addColor(String name, String chatColor, String item) {
        
    }

    private boolean checkForCorrectColor(String name, ChatColor color) throws ColorNotCorrectException {
        if (!readColors.containsKey(name))
            throw new ColorNotCorrectException(0);
        if(!readColors.containsValue(color))
            throw new ColorNotCorrectException(1);
        return true;
    }

    public void writeListToFile() {
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));) {
            for (Map.Entry<String, ItemColorCombo> entry : readColors.entrySet()) {
                bw.write("name='" + entry.getKey() + "',color='" + entry.getValue().getColor() + "',item"
                        + Objects.requireNonNull(entry.getValue().getItem().getData()).getItemType().toString());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("There has been an error saving the LPrefixor storage file! Newly added colors might be lost.");
            e.printStackTrace();
        }
    }
}
