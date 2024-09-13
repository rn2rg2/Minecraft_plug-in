package org.rn2rg2plugin.shulkerPacks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.rn2rg2plugin.shulkerPacks.PackEvents;

public final class ShulkerPacks extends JavaPlugin {
    //추후에 셜커 상자 2개어치 개발
    @Override
    public void onEnable() {
        getLogger().info("ShulckerBag 1.6 running");

        // 이벤트 리스너 등록
        Bukkit.getPluginManager().registerEvents(new PackEvents(this), this); // 이벤트 등록
    }
    @Override
    public void onDisable() {
        getLogger().info("ShulckerBag 1.6 shutting");
    }
}