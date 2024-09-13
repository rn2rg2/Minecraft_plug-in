package org.rn2rg2plugin.wanderingTraderPlus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MysticTrades implements Listener {
    // 아라비아 숫자를 로마 숫자로 변환하는 메서드
    private String toRomanNumerals(int number) {
        if (number < 1 || number > 3999) return "Invalid Number"; // 로마 숫자 범위
        String[] romanNumerals = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                roman.append(romanNumerals[i]);
            }
        }
        return roman.toString();
    }

    @EventHandler
    public void onWanderingTraderSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof WanderingTrader) {
            WanderingTrader trader = (WanderingTrader) event.getEntity();

            // 10% 확률
            Random random = new Random();
            if (random.nextInt(100) < 95) { // 임의로 95% 확률
                ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
                bookMeta.addStoredEnchant(Enchantment.SHARPNESS, 10, true); // 날카로움 10
                enchantedBook.setItemMeta(bookMeta);

                // 기본 로컬라이즈된 이름 가져오기
                String displayName = enchantedBook.getItemMeta().getDisplayName();

                // 마지막 숫자를 로마 숫자로 변환
                if (displayName != null && displayName.matches(".*\\d+$")) { // 숫자로 끝나는 경우
                    // 숫자를 로마 숫자로 변환
                    String numberPart = displayName.replaceAll("[^0-9]", ""); // 숫자만 추출
                    int number = Integer.parseInt(numberPart); // 숫자 변환
                    String romanNumber = toRomanNumerals(number); // 로마 숫자 변환

                    // 로마 숫자로 교체하여 새로운 이름 설정
                    displayName = displayName.replaceAll("\\d+$", romanNumber); // 마지막 숫자를 로마 숫자로 교체
                }

                // 수정된 이름 설정
                ItemMeta meta = enchantedBook.getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + displayName); // 변경된 이름 설정
                enchantedBook.setItemMeta(meta);

                // 거래 설정 (인첸트 책 1개, 에메랄드 30개)
                MerchantRecipe recipe = new MerchantRecipe(enchantedBook, 1);
                recipe.addIngredient(new ItemStack(Material.EMERALD, 30));

                // 기존 거래에 추가
                List<MerchantRecipe> recipes = new ArrayList<>(trader.getRecipes());
                recipes.add(recipe);
                trader.setRecipes(recipes);
            }
        }
    }
}
