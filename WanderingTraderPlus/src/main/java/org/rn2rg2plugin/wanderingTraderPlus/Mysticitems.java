package org.rn2rg2plugin.wanderingTraderPlus;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class Mysticitems {
    /*
    * 무기 : 날카로움 10, 강타 10, 살충 10, 힘 10
    * 도구 : 효율 10, 행운 8
    * 방어구 : 보호 10, 화염보호 10, 쉬프트이속 5, 가착 8
    * 공통 : 내구성 8
    * 기타 : 무한+수선 활, 인첸트 황금사과
    */
    private static final int[] ARABIC_NUMBERS = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ROMAN_NUMERALS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static List<ItemStack> getTradeItems() {
        List<ItemStack> items = new ArrayList<>();
        //items.add(createEnchantedBook(Enchantment.SHARPNESS, 10));

        return items;
    }

//    private static ItemStack createEnchantedBook(Enchantment enchantment, int level) {
//        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
//        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
//        meta.addStoredEnchant(enchantment, level, true);
//        enchantedBook.setItemMeta(meta);
//
//        // 커스텀 이름 지정
//        ItemMeta itemMeta = enchantedBook.getItemMeta();
//        itemMeta.setDisplayName(name + " " + RomanNumeral.toRoman(level));
//        enchantedBook.setItemMeta(itemMeta);
//        return enchantedBook;
//    }
}

