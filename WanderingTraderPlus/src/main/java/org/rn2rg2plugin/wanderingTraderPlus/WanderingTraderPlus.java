package org.rn2rg2plugin.wanderingTraderPlus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WanderingTraderPlus extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new MysticTrades(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
