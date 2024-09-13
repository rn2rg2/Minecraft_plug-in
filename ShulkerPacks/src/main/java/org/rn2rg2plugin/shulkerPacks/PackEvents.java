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
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class PackEvents implements Listener {
    private final JavaPlugin plugin; //final 을 통해서 생성자를 이용해 한번만 받아옴

    public PackEvents(JavaPlugin plugin) { // 생성자를 이용해 shulkerPack 클래스 plugin을 등록
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClick().isRightClick()) { // 우클릭시
            //아이템 확인
            Player player = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || !clickedItem.hasItemMeta()) return;
            //해당 클릭한 아이템이 셜커 박스 이거나 엔더체스트인지 확인
            if (clickedItem.getType().name().contains("SHULKER_BOX")) {
                //기존에 우클릭을 하면 아이템이 좌클릭한것처럼 커서에 아이템이 물리게 되는데 오류는 없지만 UI 상으로 보기 안좋기 때문에
                //setCursor(null)를 통해서 셜커 우클릭시 (셜커가 마우스에 물리게 되는) 현상을 막아준다.
                player.setItemOnCursor(new ItemStack(Material.AIR));
                openShulkerBox(e, clickedItem);
            }else if(clickedItem.getType().name().contains("ENDER_CHEST")){
                player.setItemOnCursor(new ItemStack(Material.AIR));
                openEnderChest(e);
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
        Player player = (Player) e.getWhoClicked(); // 클릭한 플레이어 정보 등록

        // 셜커 박스 아이템의 BlockStateMeta를 가져옴
        BlockStateMeta blockStateMeta = (BlockStateMeta) clickedItem.getItemMeta();
        ShulkerBox shulkerBox = (ShulkerBox) blockStateMeta.getBlockState();

        // 현재 플레이어가 열고 있는 인벤토리에서 셜커 박스의 BlockStateMeta 비교
        InventoryView openInventory = player.getOpenInventory();
        if (openInventory != null && openInventory.getTopInventory().getHolder() instanceof ShulkerBox) {
            ShulkerBox openShulkerBox = (ShulkerBox) openInventory.getTopInventory().getHolder();
            BlockStateMeta openShulkerMeta = (BlockStateMeta) new ItemStack(openShulkerBox.getType()).getItemMeta();
            openShulkerMeta.setBlockState(openShulkerBox);

            // 클릭한 셜커 박스와 현재 열려있는 셜커 박스의 메타데이터를 비교
            if (blockStateMeta.equals(openShulkerMeta)) {
                return; // 동일한 셜커 박스를 다시 열지 않음
            }
        }

        // 셜커 박스 열기
        player.openInventory(shulkerBox.getInventory());

        // 셜커 박스 열리는 소리 재생
        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 1.0f, 1.0f);

        // 닫을 때 셜커 박스 내용 업데이트
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryClose(InventoryCloseEvent closeEvent) {
                if (closeEvent.getPlayer().equals(player)) {
                    for (int i = 0; i < shulkerBox.getInventory().getSize(); i++) {
                        shulkerBox.getInventory().setItem(i, closeEvent.getInventory().getItem(i));
                    }
                    blockStateMeta.setBlockState(shulkerBox);
                    clickedItem.setItemMeta(blockStateMeta);
                    player.updateInventory();

                    // 셜커 박스 닫히는 소리 재생
                    player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, 1.0f, 1.0f);
                    InventoryCloseEvent.getHandlerList().unregister(this);
                }
            }
        }, plugin);
    }
}