package org.rn2rg2plugin.fasterTrade;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class FasterTrade extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("FasterTrade - V : 0.0.1 Enabled ");
        getServer().getPluginManager().registerEvents(this, this);  // 이벤트 등록
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() instanceof Villager) {
            Player player = (Player) event.getWhoClicked();
            Villager villager = (Villager) event.getInventory().getHolder();

            // 쉬프트 클릭 감지
            if (event.isShiftClick()) {
                int selectedSlot = event.getRawSlot();

                // 거래 슬롯을 클릭했는지 확인
                if (selectedSlot >= 0 && selectedSlot < villager.getRecipeCount()) {
                    MerchantRecipe recipe = villager.getRecipe(selectedSlot);

                    if (recipe != null) {
                        // 플레이어 인벤토리에서 거래에 필요한 재료 확인
                        ItemStack ingredient = recipe.getIngredients().get(0);  // 첫 번째 재료만 사용한다고 가정
                        int ingredientAmount = ingredient.getAmount();
                        int totalIngredients = 0;

                        // 플레이어의 인벤토리에서 해당 재료가 얼마나 있는지 확인
                        for (ItemStack item : player.getInventory().getContents()) {
                            if (item != null && item.isSimilar(ingredient)) {
                                totalIngredients += item.getAmount();
                            }
                        }

                        // 거래 가능한 횟수 계산 (최대 거래 가능한 횟수)
                        int maxUses = Math.min(totalIngredients / ingredientAmount, recipe.getMaxUses() - recipe.getUses());

                        if (maxUses > 0) {
                            // 재료 제거 및 결과물 지급
                            int totalCost = maxUses * ingredientAmount;

                            // 재료 제거
                            player.getInventory().removeItem(new ItemStack(ingredient.getType(), totalCost));

                            // 결과물 지급 (거래 결과)
                            ItemStack result = recipe.getResult();
                            ItemStack totalResult = new ItemStack(result.getType(), result.getAmount() * maxUses);
                            player.getInventory().addItem(totalResult);

                            // 거래 사용 횟수 증가
                            MerchantRecipe newRecipe = new MerchantRecipe(result, recipe.getMaxUses());
                            newRecipe.setUses(recipe.getUses() + maxUses);
                            newRecipe.setIngredients(recipe.getIngredients());
                            newRecipe.setExperienceReward(recipe.hasExperienceReward());

                            // Villager의 거래 목록 업데이트
                            List<MerchantRecipe> newRecipes = villager.getRecipes();
                            newRecipes.set(selectedSlot, newRecipe);
                            villager.setRecipes(newRecipes);

                            player.sendMessage("거래 완료: " + totalCost + "개의 " + ingredient.getType() + "을 팔고 " + totalResult.getAmount() + "개의 " + result.getType() + "을 얻었습니다.");
                        } else {
                            player.sendMessage("거래할 충분한 재료가 없습니다.");
                        }

                        event.setCancelled(true);  // 기본 클릭 동작 취소
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}