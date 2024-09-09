package org.rn2rg2plugin.noChargeShoot;

import io.papermc.paper.enchantments.*;
import io.papermc.paper.registry.set.RegistryKeySet;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class NoChargeEnchantment extends Enchantment {

    public NoChargeEnchantment(int id) {
        super(id);
    }
    @Override
    public String getName() {
        return "NoCharge";
    }

    @Override
    public int getMaxLevel() {
        return 3; // 인챈트 최대 레벨 3
    }

    @Override
    public int getStartLevel() {
        return 1; // 인챈트 시작 레벨 1
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.BOW; // 활에만 적용
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return item.getType().toString().contains("BOW"); // 활에만 인챈트 가능
    }

    @Override
    public @NotNull Component displayName(int level) {
        String displayName;
        switch (level) {
            case 1:
                displayName = "NoCharge I"; // 또는 "장전대기시간 I"
                break;
            case 2:
                displayName = "NoCharge II"; // 또는 "장전대기시간 II"
                break;
            case 3:
                displayName = "NoCharge III"; // 또는 "장전대기시간 III"
                break;
            default:
                displayName = "NoCharge"; // 기본 이름
                break;
        }
        // Component 생성
        return Component.text(displayName); // TextComponent로 반환
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean isDiscoverable() {
        return true;
    }

    @Override
    public int getMinModifiedCost(int i) {
        return 0;
    }

    @Override
    public int getMaxModifiedCost(int i) {
        return 0;
    }

    @Override
    public int getAnvilCost() {
        return 0;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return null;
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityType entityType) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlotGroup> getActiveSlotGroups() {
        return null;
    }

    @Override
    public @NotNull Component description() {
        return null;
    }

    @Override
    public @NotNull RegistryKeySet<ItemType> getSupportedItems() {
        return null;
    }

    @Override
    public @Nullable RegistryKeySet<ItemType> getPrimaryItems() {
        return null;
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public @NotNull RegistryKeySet<Enchantment> getExclusiveWith() {
        return null;
    }

    @Override
    public @NotNull String translationKey() {
        return null;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false; // 다른 인챈트와 충돌 없음
    }

    @Override
    public boolean isTreasure() {
        return false; // 인챈트가 보물 인챈트가 아님
    }

    @Override
    public boolean isCursed() {
        return false; // 인챈트가 저주 아님
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return null;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return null;
    }
}
