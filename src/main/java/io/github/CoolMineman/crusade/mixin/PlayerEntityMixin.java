package io.github.CoolMineman.crusade.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.CoolMineman.crusade.CrusadeMod;
import io.github.CoolMineman.crusade.EpicStaticMath;
import io.github.CoolMineman.crusade.LanceItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private boolean bypassTheArbirtraryAttackBlocker = false;
    private boolean coolDownHacksEnabled = false;

    //private double[] speedBuffer = {0.0D, 0.0D, 0.0D, 0.0D, 0.0D};
    private double speedBuffer0 = 0.0D;
    private double speedBuffer1 = 0.0D;
    private double speedBuffer2 = 0.0D;
    private double speedBuffer3 = 0.0D;
    private double speedBuffer4 = 0.0D;

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void attack(Entity target, CallbackInfo cb) {
        if ( CrusadeMod.LANCES.contains( ((PlayerEntity)(Object)this).getMainHandStack().getItem() ) && !bypassTheArbirtraryAttackBlocker ) {
            cb.cancel();
        }   
        else if (bypassTheArbirtraryAttackBlocker) {
            bypassTheArbirtraryAttackBlocker = false;
            coolDownHacksEnabled = true;
        }
    }

    @Inject(method = "getAttackCooldownProgress", at = @At("HEAD"), cancellable = true)
    public void getAttackCooldownProgress(float baseTime, CallbackInfoReturnable<Float> cb) {
        if (coolDownHacksEnabled) {
            coolDownHacksEnabled = false;
            float damageMultiplier = (float) averageSpeed();
            cb.setReturnValue(damageMultiplier);
        }
    }

    @Inject(method = "takeShieldHit", at = @At("TAIL"))
    protected void takeShieldHit(LivingEntity attacker, CallbackInfo cb) {
        if ( CrusadeMod.LANCES.contains( attacker.getMainHandStack().getItem() ) ) {
            ((PlayerEntity)(Object)this).disableShield(true);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo cb) {
        updateSpeedBuffer();
        if (CrusadeMod.LANCES.contains( ((PlayerEntity)(Object)this).getMainHandStack().getItem() ) && ((PlayerEntity)(Object)this).hasVehicle()) {
            for (Entity e : ((PlayerEntity)(Object)this).world.getEntities(null, new Box(((PlayerEntity)(Object)this).getPos().subtract(5, 5, 5), ((PlayerEntity)(Object)this).getPos().add(5, 5, 5)))) {
                if (e.equals(((PlayerEntity)(Object)this).getVehicle()))
                    continue;
                if ((e.equals((PlayerEntity)(Object)this)))
                    continue;
                if (e.squaredDistanceTo((PlayerEntity)(Object)this) > LanceItem.REACH_SQUARED)
                    continue;
                
                float a = ((PlayerEntity)(Object)this).getHeadYaw(); //Player head direction in scuffed degrees b/c reasons
                //East = 270
                //West = 90
                //South = 0
                //North = -180
                a += 180;
                if (a < 0) a += 360;
                if (a > 360) a -= 360;
                //Should unscuff it ;P North is now 0 deg

                double x = ((PlayerEntity)(Object)this).getPos().x;
                double y = ((PlayerEntity)(Object)this).getPos().z;

                double x1 = e.getPos().x;
                double y1 = e.getPos().z;

                if (Math.abs(EpicStaticMath.angleDiff(EpicStaticMath.calcAngle(x, y, x1, y1), a)) < 20) {
                    bypassTheArbirtraryAttackBlocker = true;
                    ((PlayerEntity)(Object)this).attack(e);
                }
            }
        }
    }

    public double averageSpeed() {
        return (speedBuffer0 + speedBuffer1 + speedBuffer2 + speedBuffer3 + speedBuffer4) / 5;
    }

    public void updateSpeedBuffer() {
        speedBuffer0 = (speedBuffer1 + speedBuffer0) /2;
        speedBuffer1 = speedBuffer2;
        speedBuffer2 = speedBuffer3;
        speedBuffer3 = speedBuffer4;
        speedBuffer4 = new Vec3d(((PlayerEntity)(Object)this).getVelocity().x, 0, ((PlayerEntity)(Object)this).getVelocity().z).length() * 50;
    }
}