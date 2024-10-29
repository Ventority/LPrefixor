package de.ventority.lprefixor;


import de.ventority.lprefixor.TempColorSelection.colorPermissionsWithItemStack;
import de.ventority.lprefixor.TempColorSelection.fadePermissionsWithItemStack;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class PrefixSuffixInvEvent implements Listener {
    @EventHandler
    public void clickEvent(InventoryClickEvent click) {
        if (click.getView().getTitle().contains(LPrefixor.serverHandler.getServerName() + ChatColor.RESET + ChatColor.DARK_GRAY + " Prefix")) {
            Player curPlayer = (Player) click.getWhoClicked();
            if (click.getCurrentItem() == null)
                return;
            click.setCancelled(true);
            curPlayer.updateInventory();
            if((!(click.getCurrentItem().getData().getItemType() == Material.LEGACY_STAINED_GLASS_PANE || click.getCurrentItem().getData().getItemType() == Material.BLACK_STAINED_GLASS_PANE)) && click.getClickedInventory() != curPlayer.getInventory()) {
                for (colorPermissionsWithItemStack color : colorPermissionsWithItemStack.values()) {
                    if (Objects.requireNonNull(click.getCurrentItem().getItemMeta()).getDisplayName().equals(color.item.getItemMeta().getDisplayName())) {
                        ExecuteSetPrefix.setColor(color.color, color.color, curPlayer);
                        curPlayer.closeInventory();
                        curPlayer.updateInventory();
                    }
                }
                for (fadePermissionsWithItemStack fade : fadePermissionsWithItemStack.values()) {
                    if (Objects.requireNonNull(click.getCurrentItem().getItemMeta()).getDisplayName().equals(fade.item.getItemMeta().getDisplayName())) {
                        ExecuteSetPrefix.setColor(fade.c1, fade.c2, curPlayer);
                        curPlayer.closeInventory();
                        curPlayer.updateInventory();
                    }
                }
                if (click.getCurrentItem().getType() == Material.BARRIER) {
                    ExecuteSetPrefix.clearPrefix(LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(curPlayer));
                    curPlayer.sendMessage("Der Prefix wurde zurückgesetzt!");
                    curPlayer.closeInventory();
                    curPlayer.updateInventory();
                }

                if (Objects.requireNonNull(click.getCurrentItem().getItemMeta()).getDisplayName().contains("Nächste")) {
                    PrefixGUICreator creator = new PrefixGUICreator(curPlayer);
                    String title = click.getView().getTitle();
                    creator.colorSelector(title.charAt(title.indexOf("| S:") + 5) - '0');
                }

                if (Objects.requireNonNull(click.getCurrentItem().getItemMeta()).getDisplayName().contains("Vorherige")) {
                    PrefixGUICreator creator = new PrefixGUICreator(curPlayer);
                    String title = click.getView().getTitle();
                    creator.colorSelector(title.charAt(title.indexOf("| S:") + 5) - '0'-2);
                }
            }
        }
    }
}
