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
import net.minecraft.world.level.Level;

public class FermentedBeerItem extends Item {
	
    public FermentedBeerItem(Identifier id) {
        super(new Item.Properties()
        		.durability(10)
        		.food(new FoodProperties.Builder()
        				.alwaysEdible()
        				.nutrition(2)
        				.saturationModifier(0.5F)
        				.build()	
        			)
        		.setId(ResourceKey.create(Registries.ITEM, id))
        		);
    }
    
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity player) {
		ItemStack result = super.finishUsingItem(stack, level, player);
		if(!level.isClientSide()) {
			player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 20*5, 1));
			player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20*5, 1));
			player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 20*5, 1));
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
