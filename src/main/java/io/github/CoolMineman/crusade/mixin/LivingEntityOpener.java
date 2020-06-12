package io.github.CoolMineman.crusade.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public interface LivingEntityOpener {
    @Accessor
    public int getLastAttackedTicks();
}