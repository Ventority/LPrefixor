package de.ventority.lprefixor;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExecuteSetPrefix implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (commandSender instanceof Player) {
                Player sender = (Player) commandSender;
                PrefixGUICreator invCreator = new PrefixGUICreator(sender);
                invCreator.colorSelector();
            } else {
                System.out.println("This command can only be run by a player!");
            }
        } else if (args[0].equals("add")) {
            try {
                if (args.length == 4) {
                    LPrefixor.serverHandler.getFileHandler().addColor(args[1], args[2],
                            Material.getMaterial(args[3].toUpperCase()));
                    commandSender.sendMessage(ChatColor.of(Color.decode(args[2])).toString() + "Hallo");
                } else {
                    commandSender.sendMessage("Please use /prefix add <name> <HEXColor> <item>" + '\n' +
                            "Example: /prefix add MyColor #FFFFFF Diamond");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (args[0].equals("remove")) {
            try {
                if (args.length == 2) {
                    LPrefixor.serverHandler.getFileHandler().removeColor(args[1]);
                    commandSender.sendMessage("Color removed!");
                } else {
                    commandSender.sendMessage("Please use /prefix remove <name>");
                }
            } catch (Exception e) {
                commandSender.sendMessage("There was an error removing the color: " + args[1] +
                        ". Try to post an issue on GitHub if you think this is a bug.");
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> completions = new ArrayList<>();

        // Tab-Vervollständigung für das erste Argument (add oder remove)
        if (args.length == 1) {
            if ("add".startsWith(args[0].toLowerCase())) completions.add("add");
            if ("remove".startsWith(args[0].toLowerCase())) completions.add("remove");
        }

        // Tab-Vervollständigung für das dritte Argument (Materialnamen)
        else if (args.length == 4 && args[0].equalsIgnoreCase("add")) {
            for (Material material : Material.values()) {
                String materialName = material.toString().toLowerCase();
                if (materialName.startsWith(args[3].toLowerCase())) {
                    completions.add(materialName);
                }
            }
        }
        return completions;
    }
}
