package io.github.CoolMineman.crusade.trebuchet;

import io.github.CoolMineman.crusade.CrusadeMod;
import io.github.CoolMineman.crusade.mixin.BucketItemAccesser;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {
        if (world.isClient)
            return ActionResult.CONSUME;

        BlockEntity be = world.getBlockEntity(pos);
        if (be != null && be instanceof TrebuchetBlockEntity) {
            TrebuchetBlockEntity blockEntity = (TrebuchetBlockEntity) be;

            // Don't spawn add new entity if it is already throwing
            if (blockEntity.armState != 0)
                return ActionResult.CONSUME;

            if (player.getMainHandStack().isEmpty()) {
                blockEntity.hasEntityToThrow = true;
                blockEntity.entityToThrow = player.getUuid();
            } else if (player.getMainHandStack().getItem() instanceof BucketItem) {
                // todo fix
                Fluid a = ((BucketItemAccesser) player.getMainHandStack().getItem()).getFluid();
                if (!(a.equals(Fluids.EMPTY))) {
                    Entity e = new FallingBlockEntity(world, pos.getX(), pos.getY() + 5, pos.getZ(),
                            a.getDefaultState().getBlockState());
                    world.spawnEntity(e);
                    blockEntity.hasEntityToThrow = true;
                    blockEntity.entityToThrow = e.getUuid();
                }
            } else if (player.getMainHandStack().getItem() instanceof BlockItem) {
                Block a = ((BlockItem) player.getMainHandStack().getItem()).getBlock();
                TrebuchetProjectile e = new TrebuchetProjectile(CrusadeMod.TREBUCHET_PROJECTILE, world);
                e.setTheBlockState(a.getDefaultState());
                e.setPos(be.getPos().getX(), -5, be.getPos().getZ()); // Hacky Code To Make Enitity exist
                world.spawnEntity(e);
                blockEntity.hasEntityToThrow = true;
                blockEntity.entityToThrow = e.getUuid();
                if (!player.isCreative())
                    player.getMainHandStack().decrement(1);
            }
        } else {
            System.out.println(":irritater:");
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

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}