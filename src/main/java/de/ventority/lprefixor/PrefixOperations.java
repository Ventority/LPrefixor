package de.ventority.lprefixor;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;

public class PrefixOperations {
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
