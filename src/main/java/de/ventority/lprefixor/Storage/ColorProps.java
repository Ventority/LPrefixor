package de.ventority.lprefixor.Storage;

import de.ventority.lprefixor.LPrefixor;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.messaging.MessagingService;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;


public class ColorProps {
    private ChatColor color;
    private ItemStack item;
    private Permission permission;


    public ColorProps(ChatColor color, ItemStack item, Permission permission) {
        this.color = color;
        this.item = item;
        this.permission = permission;
        LPrefixor.serverHandler.getPluginManager().addPermission(permission);
        LPrefixor.serverHandler.getLuckPerms().getMessagingService().ifPresent(MessagingService::pushUpdate);
    }

    public ChatColor getColor() {
        return color;
    }

    public ItemStack getItem() {
        return item;
    }
}
