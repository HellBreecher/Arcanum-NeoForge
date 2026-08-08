package com.hellbreecher.arcanum.fabric.mixin;

import com.hellbreecher.arcanum.common.items.armor.InfernalArmorItem;
import com.hellbreecher.arcanum.common.items.weapons.InfernalSwordItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
abstract class LivingEntityDamageMixin {
    @ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float arcanum$modifyDamage(float amount, ServerLevel level, DamageSource source) {
        LivingEntity target = (LivingEntity) (Object) this;
        float modified = target instanceof Player player
                ? InfernalArmorItem.onIncomingDamage(player, source, amount)
                : amount;
        return InfernalSwordItem.onLivingDamage(target, source, modified);
    }
}
