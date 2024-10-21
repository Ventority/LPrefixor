package de.ventority.lprefixor.Storage;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class storageFileHandler {
    private static final String file = "colorfulPrefixes.txt";
    private static List<String> playerList = new ArrayList<>();

    public static boolean hasLine(Player p) {
        boolean b = false;
        for (int i = 0; i < playerList.size(); i++) {
            String element = playerList.get(i);
            if (element.contains("uuid='" + p.getUniqueId() + "'")) {
                b = true;
            }
        }
        return b;
    }

    public static void parseFile() {
        try {
            playerList = Files.readAllLines(Paths.get(file));
        } catch (Exception e1) {
            File f = new File(file);
            try {
                f.createNewFile();
            }catch (Exception e2) {
                System.out.println("Prefixe funktionieren gerade nicht, es gab ein Problem mit der Speicher-Datei!" + '\n');
                e2.printStackTrace();
            }
        }
    }

    public static ChatColor getPrimaryColor(Player p) {
        String line = getLineByUUID(p);
        String[] parts = line.split("color1='");
        String oldColor = parts[1].split("'")[0];
        return ChatColor.getByChar(oldColor.charAt(1));
    }

    public static ChatColor getSecondaryColor(Player p) {
        String line = getLineByUUID(p);
        String[] parts = line.split("color2='");
        String oldColor = parts[1].split("'")[0];
        return ChatColor.getByChar(oldColor.charAt(1));
    }

    public static int getFadignIndex(Player p) {
        String line = getLineByUUID(p);
        String[] parts = line.split("fade='");
        String fade = parts[1].split("'")[0];
        return fade.charAt(0)-48;
    }

    public static void replacePrimaryColor(Player p, ChatColor c) {
        String element = getLineByUUID(p);
        String[] parts = element.split("color1='");
        String oldColor = parts[1].split("'")[0];
        String newElement = element.replace("color1='" + oldColor + "'", "color1='" + c + "'");
        playerList.set(getLineNumByUUID(p), newElement);
    }

    public static void replaceSecondaryColor(Player p, ChatColor c) {
        String element = getLineByUUID(p);
        String[] parts = element.split("color2='");
        String oldColor = parts[1].split("'")[0];
        String newElement = element.replace("color2='" + oldColor + "'", "color2='" + c + "'");
        playerList.set(getLineNumByUUID(p), newElement);
    }

    public static void setFadingIndex(Player p, int i) {
        String element = getLineByUUID(p);
        String[] parts = element.split("fade='");
        String olfFade = parts[1].split("'")[0];
        String newElement = element.replace("fade='" + olfFade + "'", "fade='" + (char)(i+48) + "'");
        playerList.set(getLineNumByUUID(p), newElement);
    }

    public static void createNewLine(Player p) {
        String newLine = "uuid='" + p.getUniqueId() + "', color1='&8', color2='&8', fade='0'";
        playerList.add(newLine);
    }

    public static void resetLine(Player p) {
        String newLine = "uuid='" + p.getUniqueId() + "', color1='&8', color2='&8', fade='0'";
        playerList.set(getLineNumByUUID(p), newLine);
    }

    private static String getLineByUUID(Player p) {
        for (int i = 0; i < playerList.size(); i++) {
            String element = playerList.get(i);
            if (element.contains("uuid='" + p.getUniqueId() + "'")) {
                return element;
            }
        }
        return playerList.get(playerList.size()-1);
    }

    private static int getLineNumByUUID(Player p) {
        for (int i = 0; i < playerList.size(); i++) {
            String element = playerList.get(i);
            if (element.contains("uuid='" + p.getUniqueId() + "'")) {
                return i;
            }
        }
        return playerList.size()-1;
    }

    public static void writeListToFile() {
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));) {
            for (int i = 0; i < playerList.size(); i++) {
                String line = playerList.get(i);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException ignored) {

        }
    }
}
