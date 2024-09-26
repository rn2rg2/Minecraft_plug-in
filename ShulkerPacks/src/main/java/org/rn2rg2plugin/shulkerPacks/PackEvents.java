package org.rn2rg2plugin.shulkerPacks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class PackEvents implements Listener {
    private final JavaPlugin plugin; //final 을 통해서 생성자를 이용해 한번만 받아옴

    public PackEvents(JavaPlugin plugin) { // 생성자를 이용해 shulkerPack 클래스 plugin을 등록
        this.plugin = plugin;
    }
    // 셜커 박스 관리용 Map (플레이어 UUID와 셜커 박스 인벤토리를 저장)

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClick().isRightClick()) { // 우클릭 시
            // 아이템 확인
            Player player = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();

            // 클릭한 아이템이 셜커박스인지 확인
            if (clickedItem.getType().name().contains("SHULKER_BOX")) {
                // UI 상 커서에 셜커박스가 물리는 현상 방지
                player.setItemOnCursor(new ItemStack(Material.AIR));
                openShulkerBox(e, clickedItem);
                //e.setCancelled(true);
            } else if (clickedItem.getType().name().contains("ENDER_CHEST")) {
                player.setItemOnCursor(new ItemStack(Material.AIR));
                openEnderChest(e);
                //e.setCancelled(true);
            }
        }
    }

    private void openEnderChest(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        // 엔더 체스트 인벤토리 열기
        Inventory enderChestInventory = player.getEnderChest();

        // 우클릭 시 엔더 체스트 인벤토리 열기
        player.openInventory(enderChestInventory);

        // 엔더 체스트 열리는 소리 재생
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1.0f, 1.0f);

        // 엔더 체스트 닫을 때 소리 재생
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryClose(InventoryCloseEvent closeEvent) {
                // 닫힌 인벤토리가 해당 플레이어의 엔더 체스트인지 확인
                if (closeEvent.getPlayer().equals(player)) {
                    player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                    InventoryCloseEvent.getHandlerList().unregister(this);
                }
            }
        }, plugin);
    }

    private void openShulkerBox(InventoryClickEvent e, ItemStack clickedItem) {
        Player player = (Player) e.getWhoClicked();

        // 셜커 박스의 BlockStateMeta 가져오기
        BlockStateMeta blockStateMeta = (BlockStateMeta) clickedItem.getItemMeta();
        ShulkerBox shulkerBox = (ShulkerBox) blockStateMeta.getBlockState();
        Inventory shulkerInventory = shulkerBox.getInventory();

        // 셜커 박스 열기
        player.openInventory(shulkerInventory);
        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 1.0f, 1.0f);

        // 닫을 때 셜커 박스 상태를 직접 업데이트
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryClose(InventoryCloseEvent closeEvent) {
                if (closeEvent.getPlayer().equals(player)) {
                    // BlockStateMeta에 인벤토리 상태 저장
                    blockStateMeta.setBlockState(shulkerBox);
                    clickedItem.setItemMeta(blockStateMeta); // 아이템의 메타데이터를 갱신

                    // 셜커 박스 닫히는 소리 재생
                    player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, 1.0f, 1.0f);
                    // 이벤트 등록 해제
                    InventoryCloseEvent.getHandlerList().unregister(this);
                }
            }
        }, plugin);
    }
}