package io.github.CoolMineman.crusade;

import io.github.CoolMineman.crusade.trebuchet.TrebuchetRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class ClientCrusadeMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(CrusadeMod.TREBUCHET_ENTITY, TrebuchetRenderer::new);
    }
}