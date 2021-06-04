package io.github.CoolMineman.crusade.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.CoolMineman.crusade.CrusadeMod;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;

@Mixin(BipedEntityModel.class)
public class HandRenderMixin {
    @Inject(method = "setAngles", at = @At(value = "INVOKE", target = "net/minecraft/client/render/entity/model/BipedEntityModel.animateArms(Lnet/minecraft/entity/LivingEntity;F)V"))
    public void setAngles(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo epic) {
        //todo Other mobs?
        if (!(livingEntity instanceof PlayerEntity))
            return;
        if (((PlayerEntity)livingEntity).hasVehicle()) {
            if (((PlayerEntity)livingEntity).getMainArm() == Arm.RIGHT) {
                if (CrusadeMod.LANCES.contains(((PlayerEntity)livingEntity).getMainHandStack().getItem()))
                    ((BipedEntityModel)(Object)this).rightArm.pitch = 0.2F;
                if (CrusadeMod.LANCES.contains(((PlayerEntity)livingEntity).getOffHandStack().getItem()))
                    ((BipedEntityModel)(Object)this).leftArm.pitch = 0.2F;
            } else {
                if (CrusadeMod.LANCES.contains(((PlayerEntity)livingEntity).getMainHandStack().getItem()))
                    ((BipedEntityModel)(Object)this).leftArm.pitch = 0.2F;
                if (CrusadeMod.LANCES.contains(((PlayerEntity)livingEntity).getOffHandStack().getItem()))
                    ((BipedEntityModel)(Object)this).rightArm.pitch = 0.2F;
            }
        } else {
            if (((PlayerEntity)livingEntity).getMainArm() == Arm.RIGHT) {
                if (CrusadeMod.LANCES.contains(((PlayerEntity)livingEntity).getMainHandStack().getItem()))
                    ((BipedEntityModel)(Object)this).rightArm.pitch = -1.5F;
                if (CrusadeMod.LANCES.contains(((PlayerEntity)livingEntity).getOffHandStack().getItem()))
                    ((BipedEntityModel)(Object)this).leftArm.pitch = -1.5F;
            } else {
                if (CrusadeMod.LANCES.contains(((PlayerEntity)livingEntity).getMainHandStack().getItem()))
                    ((BipedEntityModel)(Object)this).leftArm.pitch = -1.5F;
                if (CrusadeMod.LANCES.contains(((PlayerEntity)livingEntity).getOffHandStack().getItem()))
                    ((BipedEntityModel)(Object)this).rightArm.pitch = -1.5F;
            }
        }
    }
}