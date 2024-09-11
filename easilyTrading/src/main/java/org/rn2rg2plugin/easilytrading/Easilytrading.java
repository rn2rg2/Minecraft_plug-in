package org.rn2rg2plugin.easilytrading;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

public class Easilytrading implements Listener {

    @EventHandler
    public void onTradeSelect(TradeSelectEvent event) {
        // 플레이어 객체 가져오기
        Player player = (Player) event.getWhoClicked();

        // 플레이어가 쉬프트를 누르고 있는지 확인
        if (player.isSneaking()) {
            // 거래 인덱스 가져오기
            int selectedTradeIndex = event.getIndex();

            // Merchant 객체 가져오기
            Merchant merchant = event.getInventory().getMerchant();

            // 거래 레시피 가져오기
            MerchantRecipe recipe = merchant.getRecipe(selectedTradeIndex);

            // 여기에 원하는 로직을 추가
            player.sendMessage("쉬프트+클릭으로 선택된 거래: " + selectedTradeIndex);

            // 예시로 거래를 자동으로 실행하는 코드를 추가할 수 있음
        }
    }
}
