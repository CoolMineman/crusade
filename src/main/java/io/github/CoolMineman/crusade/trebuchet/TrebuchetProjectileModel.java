package io.github.CoolMineman.crusade.trebuchet;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TrebuchetProjectileModel extends EntityModel<TrebuchetProjectile> {

    public TrebuchetProjectileModel() {
        //Yeet
    }

    @Override
    public void setAngles(TrebuchetProjectile entity, float limbAngle, float limbDistance, float animationProgress,
            float headYaw, float headPitch) {
        //Shutup Vscode
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
            float blue, float alpha) {
        //rendered in feature renderer?
    }
}