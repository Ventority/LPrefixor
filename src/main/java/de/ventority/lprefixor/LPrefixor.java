package de.ventority.lprefixor;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class LPrefixor extends JavaPlugin {
    private LuckPerms lp;
    public static ServerSettingsHandler serverHandler = new ServerSettingsHandler();

    @Override
    public void onEnable() {
        lp = LuckPermsProvider.get();
        this.getCommand("prefix").setExecutor(new ExecuteSetPrefix());
        getServer().getPluginManager().registerEvents(new PrefixSuffixInvEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
