package io.github.CoolMineman.crusade;

import io.github.CoolMineman.crusade.trebuchet.CursedModelGenerator;
import io.github.CoolMineman.crusade.trebuchet.TrebuchetRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class ClientCrusadeMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //BlockRenderLayerMap.INSTANCE.putBlock(CrusadeMod.TREBUCHET_BASE, RenderLayer.getTranslucent());
        BlockEntityRendererRegistry.INSTANCE.register(CrusadeMod.TREBUCHET_ENTITY, TrebuchetRenderer::new);
        CursedModelGenerator.doTheCursed();
    }
}