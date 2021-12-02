package io.github.CoolMineman.crusade.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.CoolMineman.crusade.CrusadeMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.model.json.ModelTransformation;

@Mixin(HeadFeatureRenderer.class)
public class Savemefromthisdirtyhack {
    @Shadow
    private float scaleX;
    @Shadow
    private float scaleY;
    @Shadow
    private float scaleZ;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo cb) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        if (!(itemStack.isEmpty()) && (itemStack.getItem() == CrusadeMod.GREAT_HELM || itemStack.getItem() == CrusadeMod.GREAT_HELM_CHRISTMAS)) {
            matrixStack.push();
            matrixStack.scale(this.scaleX, this.scaleY, this.scaleZ);
            boolean bl = livingEntity instanceof VillagerEntity || livingEntity instanceof ZombieVillagerEntity;
            ((ModelWithHead)((HeadFeatureRenderer)(Object)this).getContextModel()).getHead().rotate(matrixStack);
            matrixStack.translate(0.0D, -0.25D, 0.0D);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            matrixStack.scale(0.625F, -0.625F, -0.625F);
            if (bl) {
               matrixStack.translate(0.0D, 0.1875D, 0.0D);
            }

            MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, itemStack, ModelTransformation.Mode.HEAD, false, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
            cb.cancel();
        }
    }
}