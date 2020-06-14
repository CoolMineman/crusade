package io.github.CoolMineman.crusade.trebuchet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BaseTrebuchetBlock extends Block implements BlockEntityProvider {
    public static final IntProperty EPIC = IntProperty.of("epic", 0, 6);

    public BaseTrebuchetBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(EPIC, 0));
    }

    @Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.PASS;

        BlockEntity be = world.getBlockEntity(pos);
        if (be!=null && be instanceof TrebuchetBlockEntity) {
            TrebuchetBlockEntity blockEntity = (TrebuchetBlockEntity)be;
            blockEntity.hasEntityToThrow = true;
            blockEntity.entityToThrow = player.getUuid();
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(EPIC);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TrebuchetBlockEntity();
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }
}