package io.github.CoolMineman.crusade.trebuchet;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TrebuchetProjectileRenderer extends MobEntityRenderer<TrebuchetProjectile, TrebuchetProjectileModel> {

    public TrebuchetProjectileRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new TrebuchetProjectileModel(), 1f);
        this.addFeature(new TrebuchetProjectileFeature(this));
    }

    @Override
    public Identifier getTexture(TrebuchetProjectile entity) {
        return new Identifier("crusade", "this/error/is/normal/ignore/it.png");
    }
}