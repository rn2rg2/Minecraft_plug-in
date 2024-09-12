package org.rn2rg2plugin.shulkerPack;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShulkerPack extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("ShulckerBag 1.0 running");

        // 이벤트 리스너 등록
        Bukkit.getPluginManager().registerEvents(new PackEvents(this), this); // 이벤트 등록
    }
    @Override
    public void onDisable() {
        getLogger().info("ShulckerBag 1.0 shutting");
    }
}