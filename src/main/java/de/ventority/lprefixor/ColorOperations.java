package de.ventority.lprefixor;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public class ColorOperations {
    public static String fadeBetweenColors(Color startColor, Color endColor, String prefix) {
        StringBuilder sb = new StringBuilder();
        String extra = prefix.charAt(0) == '&' ? "&l" : "";
        prefix = prefix.charAt(0) == '&' ? prefix.substring(2) : prefix;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            float ratio = (float) i / (prefix.length()-1);
            int red = (int) (startColor.getRed() * (1 - ratio) + endColor.getRed() * ratio);
            int green = (int) (startColor.getGreen() * (1 - ratio) + endColor.getGreen() * ratio);
            int blue = (int) (startColor.getBlue() * (1 - ratio) + endColor.getBlue() * ratio);
            Color intermediateColor = new Color(red, green, blue);
            String temp = ChatColor.of(intermediateColor).toString();
            sb.append(temp);
            sb.append(extra);
            sb.append(ch);
        }
        sb.append(ChatColor.RESET).append(ChatColor.GRAY).append(" | ");
        return sb.toString();
    }
}
