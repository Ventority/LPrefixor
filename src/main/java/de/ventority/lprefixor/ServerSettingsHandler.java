package de.ventority.lprefixor;

import de.ventority.lprefixor.Storage.StorageFileHandler;
import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ServerSettingsHandler {
    private LuckPerms lp;
    private PluginManager pm;
    private StorageFileHandler fileHandler;
    private Plugin plugin;

    private String ServerName = "§lServerName§7";

    public void setLuckPerms(LuckPerms lp) {
        this.lp = lp;
    }

    public void setPluginManager(PluginManager pm) {
        this.pm = pm;
    }

    public void setServerName(String ServerName) {
        this.ServerName = ServerName;
    }

    public void setFileHandler(StorageFileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public String getServerName() {
        return ServerName;
    }

    public PluginManager getPluginManager() {
        return pm;
    }

    public LuckPerms getLuckPerms() {
        return lp;
    }

    public StorageFileHandler getFileHandler() {
        return fileHandler;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
