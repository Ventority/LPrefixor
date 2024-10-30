package de.ventority.lprefixor.PrefixOperations;

import de.ventority.lprefixor.ExecuteSetPrefix;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class PrefixOperations {

    public static void setColor(ChatColor c1, ChatColor c2, Player p) {
        User u = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(p);
        clearPrefix(u);
        String prefix = u.getCachedData().getMetaData().getPrefix();
        if(prefix == null)
            return;
        prefix = formatPrefix(prefix);
        String newPrefix = ColorModifiers.fadeBetweenColors(c1.getColor(), c2.getColor(), prefix);
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
}
