package org.rn2rg2plugin.shulkerPacks;

import org.bukkit.Bukkit;
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
import org.bukkit.plugin.Plugin;
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
            ItemStack clickedItem = e.getCurrentItem();

            //해당 클릭한 아이템이 셜커 박스 이거나 엔더체스트인지 확인
            if (clickedItem.getType().name().contains("SHULKER_BOX")) {
                //기존에 우클릭을 하면 아이템이 좌클릭한것처럼 커서에 아이템이 물리게 되는데 오류는 없지만 UI 상으로 보기 안좋기 때문에
                //setCursor(null)를 통해서 셜커 우클릭시 (셜커가 마우스에 물리게 되는) 현상을 막아준다.
                e.setCursor(null);
                openShulkerBox(e, clickedItem);
            }else if(clickedItem.getType().name().contains("ENDER_CHEST")){
                e.setCursor(null);
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
                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
                InventoryCloseEvent.getHandlerList().unregister(this);
            }
        }, plugin);
    }

    private void openShulkerBox(InventoryClickEvent e, ItemStack clickedItem) {
        Player player = (Player) e.getWhoClicked(); //클릭한 플레이어 정보 등록

        // 셜커 박스 아이템의 BlockStateMeta를 가져옴
        //BlockStateMeta 는 Block 종류의 상태값을 관리해주는 클래스이다.

        BlockStateMeta blockStateMeta = (BlockStateMeta) clickedItem.getItemMeta();
        ShulkerBox shulkerBox = (ShulkerBox) blockStateMeta.getBlockState();
        // 셜커 박스의 인벤토리 생성
//            Inventory shulkerInventory = Bukkit.createInventory(player, 27, clickedItem.getItemMeta().getDisplayName());
//
//                // 셜커 박스의 내용물을 복사
//            for (int i = 0; i < shulkerBox.getInventory().getSize(); i++) {
//                shulkerInventory.setItem(i, shulkerBox.getInventory().getItem(i));
//            }

        // 이벤트 취소하고 셜커 박스 열기
        player.openInventory(shulkerBox.getInventory());

        // 셜커 박스 열리는 소리 재생
        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 1.0f, 1.0f);

        // 닫을 때 셜커 박스 내용 업데이트
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryClose(InventoryCloseEvent closeEvent) {
                for (int i = 0; i < shulkerBox.getInventory().getSize(); i++) { // 27로 하드코딩해도 되지만 크기가 더블체스트인
                    //셜커가 나오게 되면 27하드코딩이 통하지 않으므로 유지보수를 위해 이렇게 정의
                    shulkerBox.getInventory().setItem(i, closeEvent.getInventory().getItem(i));
                    //닫힌 셜커박스의 아이템을 인덱스 마다 가져오고 다시 해당 인덱스에다가 setItem 해주는 것 상태 업데이트 해주는 코드
                }
                blockStateMeta.setBlockState(shulkerBox); // 실질적으로 셜커 박스 내부에 저장하는 로직
                clickedItem.setItemMeta(blockStateMeta); // 외형 or 메타데이터(인첸트 유무 혹은 이름) 이 변경될때 사용 됨
                //굳이 필요는 없으나 UI 상에서 혹시 모를 변경점이 있으면 저장할려고 남겨둠

                // 셜커 박스 닫히는 소리 재생
                player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, 1.0f, 1.0f);
                InventoryCloseEvent.getHandlerList().unregister(this);
            }
        }, plugin);
    }
}
