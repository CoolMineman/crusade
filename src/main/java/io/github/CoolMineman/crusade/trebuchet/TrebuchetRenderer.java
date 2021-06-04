package io.github.CoolMineman.crusade.trebuchet;

import io.github.CoolMineman.crusade.CrusadeMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

public class TrebuchetRenderer implements BlockEntityRenderer<TrebuchetBlockEntity> {
    private static BlockState[] bases = {CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 1),
        CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 2),
        CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 3),
        CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 4),
        CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 5),
        CrusadeMod.TREBUCHET_BASE.getDefaultState().with(BaseTrebuchetBlock.EPIC, 6)};
    
    private static float[][] counterweightxy = {{-10.5f, 10f}, {-10.5f, 9f}, {-10.5f, 7.5f}, {-10f, 6.5f}, {-8.5f, 6f}, {-7.25f, 5f}};

    private final BlockEntityRenderDispatcher dispatcher;
    private final TextRenderer textRenderer;
 
    public TrebuchetRenderer(BlockEntityRendererFactory.Context ctx) {
        this.dispatcher = ctx.getRenderDispatcher();
        this.textRenderer = ctx.getTextRenderer();
    }
 
    @Override
    public void render(TrebuchetBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int renderDirection = blockEntity.placementDirection;
        //0 = legs south
        //1 = legs east
        //2 = legs west
        //3 = legs north

        //Begin Rendering The Base
        matrices.push();
        if (renderDirection == 1) {
            matrices.translate(0f, 0, 1f);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f));
        } else if (renderDirection == 2) {
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180f));
            matrices.translate(0f, 0, -1f);
        } else if (renderDirection == 3) {
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180f));
            matrices.translate(-1f, 0, -1f);
        }
        matrices.translate(-3.25, 0, -3f);
        matrices.scale(7.5f, 7.5f, 7.5f);
        int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
        int q = blockEntity.armState;
        if (q > 5) q = 5;
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(bases[q], matrices, vertexConsumers, lightAbove, overlay);
        matrices.pop();
        //Stop Rendering The Base
        matrices.push();
        if (renderDirection == 0)
            matrices.translate(-0.25f, counterweightxy[q][1], counterweightxy[q][0]);
        else if (renderDirection == 1)
            matrices.translate(counterweightxy[q][0], counterweightxy[q][1], 0.25f);
        else if (renderDirection == 2)
            matrices.translate(-counterweightxy[q][0], counterweightxy[q][1], -0.25f);
        else if (renderDirection == 3)
            matrices.translate(0.25f, counterweightxy[q][1], -counterweightxy[q][0]);
        for (float i = -1; i <= 1; i++)
            for (float j = -1; j <= 1; j++)
                for (float k = 0; k >= -3; k--) {
                    matrices.translate(i, k, j);
                    MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(Blocks.STONE.getDefaultState(), matrices, vertexConsumers, lightAbove, overlay);
                    matrices.translate(-i, -k, -j);
                }
        matrices.pop();
        renderLabelIfPresent(blockEntity, new LiteralText("Load Players And Blocks Here"), matrices, vertexConsumers, light);
    }

    protected void renderLabelIfPresent(TrebuchetBlockEntity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        double d = this.dispatcher.camera.getBlockPos().getSquaredDistance(entity.getPos());
        if (d <= 4096.0D) {
            float offset = 0.5F;
            int yOffset = 0;
            matrices.push();
            matrices.translate(offset, offset, offset);
            matrices.multiply(this.dispatcher.camera.getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrices.peek().getModel();
            float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
            int j = (int)(g * 255.0F) << 24;
            float h = (float)(-textRenderer.getWidth(text) / 2.0);
            textRenderer.draw(text, h, (float)yOffset, 553648127, false, matrix4f, vertexConsumers, true, j, light);
            textRenderer.draw(text, h, (float)yOffset, -1, false, matrix4f, vertexConsumers, false, 0, light);

            matrices.pop();
        }
    }
}