package io.github.CoolMineman.crusade;

import io.github.CoolMineman.crusade.trebuchet.TrebuchetProjectileRenderer;
import io.github.CoolMineman.crusade.trebuchet.TrebuchetRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ClientCrusadeMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(CrusadeMod.TREBUCHET_ENTITY, TrebuchetRenderer::new);
        EntityRendererRegistry.register(CrusadeMod.TREBUCHET_PROJECTILE, TrebuchetProjectileRenderer::new);
    }
}