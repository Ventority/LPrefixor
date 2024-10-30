package de.ventority.lprefixor;

import de.ventority.lprefixor.Storage.StorageFileHandler;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class LPrefixor extends JavaPlugin {
    public static ServerSettingsHandler serverHandler;

    @Override
    public void onEnable() {
        initServerHandler();
        serverHandler.getFileHandler().parseFile();
        this.getCommand("prefix").setExecutor(new ExecuteSetPrefix());
        getServer().getPluginManager().registerEvents(new PrefixSuffixInvEvent(), this);
    }

    @Override
    public void onDisable() {
        serverHandler.getFileHandler().writeListToFile();
    }

    private void initServerHandler() {
        serverHandler = new ServerSettingsHandler();
        serverHandler.setLuckPerms(LuckPermsProvider.get());
        serverHandler.setPluginManager(getServer().getPluginManager());
        serverHandler.setFileHandler(new StorageFileHandler());
        serverHandler.setPlugin(this);
    }


}
