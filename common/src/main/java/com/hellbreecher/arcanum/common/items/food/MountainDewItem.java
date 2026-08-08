package com.hellbreecher.arcanum.common.items.food;

import com.hellbreecher.arcanum.core.ArcanumItems;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.level.Level;

public class MountainDewItem extends Item {

	public MountainDewItem(Identifier id) {
		super(new Item.Properties()
				.durability(10)
				.food(new FoodProperties.Builder()
						.nutrition(2*2)
						.saturationModifier(5.0F)
						.alwaysEdible()
						.build()
						)
				.component(DataComponents.CONSUMABLE, Consumables.defaultDrink().build())
				.setId(ResourceKey.create(Registries.ITEM, id))
				
				);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity player) {
        ItemStack result = super.finishUsingItem(stack, level, player);
        if (!level.isClientSide()) {
        	player.addEffect(new MobEffectInstance(MobEffects.SPEED, 20*20, 10));
        }
		if(result.getDamageValue() >= result.getMaxDamage() - 1) {
			return new ItemStack(ArcanumItems.emptycan.get());
		}
        result.setDamageValue(result.getDamageValue() + 1);
        return result;
    }
    
	@Override
	public ItemUseAnimation getUseAnimation(ItemStack stack) {
		return ItemUseAnimation.DRINK;
	}
}
