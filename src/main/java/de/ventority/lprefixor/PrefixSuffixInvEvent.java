package de.ventority.lprefixor;


import de.ventority.lprefixor.PrefixOperations.PrefixOperations;
import net.luckperms.api.LuckPerms;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.awt.*;

public class PrefixSuffixInvEvent implements Listener {
    @EventHandler
    public void clickEvent(InventoryClickEvent click) {
        if (click.getView().getTitle().contains(LPrefixor.serverHandler.getServerName() + ChatColor.RESET + ChatColor.DARK_GRAY + " Prefix")) {
            Player curPlayer = (Player) click.getWhoClicked();
            if (click.getCurrentItem() == null)
                return;
            click.setCancelled(true);
            curPlayer.updateInventory();
            if((!(click.getCurrentItem().getData().getItemType() == Material.BLACK_STAINED_GLASS
                    || click.getCurrentItem().getData().getItemType() == Material.BLACK_STAINED_GLASS_PANE))
                    && click.getClickedInventory() != curPlayer.getInventory()) {
                ItemStack clickedItem = click.getCurrentItem();
                LuckPerms lp = LPrefixor.serverHandler.getLuckPerms();

                if (hasNBTData(clickedItem, "lprefixor.type", "button")) {
                    //Checking for Reset button
                    if (hasNBTData(clickedItem, "lprefixor.action", "reset")) {
                        PrefixOperations.clearPrefix(lp.getUserManager().getUser(curPlayer.getUniqueId()));
                    }

                    //Checking for Next button
                    if (hasNBTData(clickedItem, "lprefixor.action", "next")) {
                        return;
                    }

                    //Checking for Previous Button
                    if (hasNBTData(clickedItem, "lprefixor.action", "previous")) {
                        return;
                    }


                }
                if (hasNBTData(clickedItem, "lprefixor.type", "color")) {
                    NamespacedKey key = new NamespacedKey(LPrefixor.serverHandler.getPlugin(), "color");
                    PersistentDataContainer container = clickedItem.getItemMeta().getPersistentDataContainer();
                    ChatColor color = ChatColor.of(Color.decode(container.get(key, PersistentDataType.STRING)));
                    PrefixOperations.setColor(color, color, curPlayer);
                }
            }
            click.setCancelled(true);
            curPlayer.closeInventory();
            curPlayer.updateInventory();
        }
    }

    private boolean hasNBTData(ItemStack item, String name, String expectedValue) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(LPrefixor.serverHandler.getPlugin(), name);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return expectedValue.equals(data.get(key, PersistentDataType.STRING));
    }
}
