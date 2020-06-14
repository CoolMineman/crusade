package io.github.CoolMineman.crusade.trebuchet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BaseTrebuchetBlock extends Block implements BlockEntityProvider {
    public static final IntProperty EPIC = IntProperty.of("epic", 0, 6);

    public BaseTrebuchetBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(EPIC, 0));
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
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }
}