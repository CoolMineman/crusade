package io.github.CoolMineman.crusade.trebuchet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TrebuchetItem extends BlockItem {
    private ItemPlacementContext context;

    public TrebuchetItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        this.context = context;
        return super.place(context);
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, PlayerEntity player, ItemStack stack, BlockState state) {
        boolean b = super.postPlacement(pos, world, player, stack, state);
        TrebuchetBlockEntity be = (TrebuchetBlockEntity) world.getBlockEntity(pos);
        switch (context.getPlayerFacing()) {
            case DOWN:
                break;
            case EAST:
                be.placementDirection = 2;
                break;
            case NORTH:
                be.placementDirection = 0;
                break;
            case SOUTH:
                be.placementDirection = 3;
                break;
            case UP:
                break;
            case WEST:
                be.placementDirection = 1;
                break;
            default:
                break;

        }
        return b;
    }

    @Override
    public String getTranslationKey() {
        return this.getOrCreateTranslationKey();
    }
}