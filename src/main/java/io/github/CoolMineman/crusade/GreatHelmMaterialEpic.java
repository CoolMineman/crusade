package io.github.CoolMineman.crusade;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

/**
 * Helemet only
 */
public class GreatHelmMaterialEpic implements ArmorMaterial {

    @Override
    public int getDurability(EquipmentSlot slot) {
        return 13 * 33; //diamond
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return 3; //highest for a helmet
    }

    @Override
    public int getEnchantability() {
        return 20; //Less than gold but still high
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }

    @Override
    public String getName() {
        return "great";
    }

    @Override
    public float getToughness() {
        return 2.0F; //diamond
    }

    @Override
    public float getKnockbackResistance() {
        return 0F;
    }
    
}