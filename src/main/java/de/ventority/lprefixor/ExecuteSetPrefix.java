package de.ventority.lprefixor;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class ExecuteSetPrefix implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            PrefixGUICreator invCreator = new PrefixGUICreator(sender);
            invCreator.colorSelector(0);
        }
        return true;
    }

    public static void setColor(ChatColor c1, ChatColor c2, Player p) {
        User u = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(p);
        clearPrefix(u);
        String prefix = u.getCachedData().getMetaData().getPrefix();
        if(prefix == null)
            return;
        prefix = formatPrefix(prefix);
        String newPrefix = fadeBetweenColors(c1.getColor(), c2.getColor(), prefix);
        PrefixNode node = PrefixNode.builder(newPrefix, 150).build();
        u.data().add(node);
        LuckPermsProvider.get().getUserManager().saveUser(u);
    }

    public static void clearPrefix(User u) {
        u.data().clear(NodeType.PREFIX::matches);
        LuckPermsProvider.get().getUserManager().saveUser(u);

    }

    private static String formatPrefix(String prefix) {
        prefix = removeColors(prefix);
        prefix = removeEnding(prefix);
        return prefix;
    }

    public static String removeEnding(String prefix) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while(prefix.charAt(i) != ' ') {
            sb.append(prefix.charAt(i));
            i++;
        }
        return sb.toString();
    }

    private static String removeColors(String prefix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prefix.length(); i++) {
            if (prefix.charAt(i) == '&') {
                if (prefix.charAt(i+1) == 'l')
                    sb.append("&l");
                i++;
            } else
                sb.append(prefix.charAt(i));
        }
        return sb.toString();
    }



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
