package org.rn2rg2plugin.shulkerPlus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShulkerPlus extends JavaPlugin {
    //추후에 셜커 크기 변경도 추가할 예정
    @Override
    public void onEnable() {
        getLogger().info("ShulckerBag 1.0 running");

        // 이벤트 리스너 등록
        Bukkit.getPluginManager().registerEvents(new ShulkerPlusEvents(this), this); // 이벤트 등록
    }
    @Override
    public void onDisable() {
        getLogger().info("ShulckerBag 1.0 shutting");
    }
}