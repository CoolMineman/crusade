package io.github.CoolMineman.crusade.trebuchet;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;

public class TrebuchetProjectileFeature extends FeatureRenderer<TrebuchetProjectile, TrebuchetProjectileModel> {

    public TrebuchetProjectileFeature(FeatureRendererContext<TrebuchetProjectile, TrebuchetProjectileModel> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
            TrebuchetProjectile entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress,
            float headYaw, float headPitch) {
        matrices.translate(-0.5, 0.5, -0.5);
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(entity.getTheBlockState(), matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
    }
    
}