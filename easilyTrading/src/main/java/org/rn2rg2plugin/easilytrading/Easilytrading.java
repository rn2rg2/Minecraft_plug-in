package org.rn2rg2plugin.easilytrading;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.MerchantRecipe;

import java.util.List;

public class Easilytrading extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // 플러그인이 활성화될 때
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("EasierVillagerTrading enabled!");
    }

    @Override
    public void onDisable() {
        // 플러그인이 비활성화될 때
        getLogger().info("EasierVillagerTrading disabled!");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            if (inventory.getHolder() instanceof Villager) {
                Villager villager = (Villager) inventory.getHolder();
                int selectedSlot = event.getRawSlot();

                // 쉬프트+우클릭을 감지
                if (event.isShiftClick() && event.isRightClick()) {
                    // Villager 거래창의 거래 슬롯인지 확인
                    if (selectedSlot >= 0 && selectedSlot < villager.getRecipeCount()) {
                        MerchantRecipe recipe = villager.getRecipe(selectedSlot);

                        if (recipe != null) {
                            // 거래 가능 여부 확인
                            if (canTrade(recipe) && hasEnoughItemsInInventory(player, recipe)) {
                                executeTrade(player, recipe);

                                // 거래가 성공적으로 완료된 경우 경험치 지급 여부 확인 및 처리
                                //if (recipe.hasExperienceReward()) {
                                //player.giveExp(1); // 원하는 경험치 값 설정
                                //}
                            }
                            event.setCancelled(true); // 기본 동작 취소
                        }
                    }
                }
            }
        }
    }

    // 거래가 가능한지 확인 (거래 가능 횟수 확인)
    private boolean canTrade(MerchantRecipe recipe) {
        return recipe.getUses() < recipe.getMaxUses();
    }

    // 인벤토리에서 필요한 아이템이 충분한지 확인
    private boolean hasEnoughItemsInInventory(Player player, MerchantRecipe recipe) {
        List<ItemStack> ingredients = recipe.getIngredients();
        for (ItemStack ingredient : ingredients) {
            if (!player.getInventory().containsAtLeast(ingredient, ingredient.getAmount())) {
                return false;
            }
        }
        return true;
    }

    // 거래 실행 및 횟수 증가
    private void executeTrade(Player player, MerchantRecipe recipe) {
        List<ItemStack> ingredients = recipe.getIngredients();
        for (ItemStack ingredient : ingredients) {
            player.getInventory().removeItem(new ItemStack(ingredient.getType(), ingredient.getAmount()));
        }
        ItemStack result = recipe.getResult();
        player.getInventory().addItem(result);

        // 거래 사용 횟수 증가
        recipe.setUses(recipe.getUses() + 1);
    }
}
