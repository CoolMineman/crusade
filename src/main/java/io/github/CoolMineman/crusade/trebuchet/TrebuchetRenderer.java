package io.github.CoolMineman.crusade.trebuchet;

import io.github.CoolMineman.crusade.CrusadeMod;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;

public class TrebuchetRenderer extends BlockEntityRenderer<TrebuchetBlockEntity> {
    private static BlockState[] bases = {CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 1),
        CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 2),
        CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 3),
        CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 4),
        CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 5),
        CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 6)};
 
    public TrebuchetRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }
 
    @Override
    public void render(TrebuchetBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int renderDirection = 0;
        //0 = legs south
        //1 = legs east
        //2 = legs west
        //3 = legs north

        //Begin Rendering The Base
        matrices.push();
        if (renderDirection == 1) {
            matrices.translate(0f, 0, 1f);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90f));
        } else if (renderDirection == 2) {
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90f));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f));
            matrices.translate(0f, 0, -1f);
        } else if (renderDirection == 3) {
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f));
            matrices.translate(-1f, 0, -1f);
        }
        matrices.translate(-3.25, 0, -3f);
        matrices.scale(7.5f, 7.5f, 7.5f);
        int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
        int q = ((int)blockEntity.getWorld().getTime()/2) % 12;
        if (q > 5) q = 5;
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(bases[q], matrices, vertexConsumers, lightAbove, overlay);
        matrices.pop();
        //Stop Rendering The Base
    }
}