package de.ventority.lprefixor;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class LPrefixor extends JavaPlugin {
    LuckPerms api = LuckPermsProvider.get();

    public static ServerSettingsHandler serverHandler = new ServerSettingsHandler();

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
