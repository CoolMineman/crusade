package io.github.CoolMineman.crusade.trebuchet;

import java.util.Optional;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class TrebuchetProjectile extends PathAwareEntity {
    private boolean hasExploaded = false;
    //NOT OPTIONAL
    private static final TrackedData<Optional<BlockState>> BLOCK = DataTracker.registerData(TrebuchetProjectile.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE);

    public TrebuchetProjectile(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.put("blockid2", NbtHelper.fromBlockState(dataTracker.get(BLOCK).orElseGet(Blocks.AIR::getDefaultState)));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        BlockState state;
        if (tag.contains("blockid")) {
            state = Registry.BLOCK.get((new Identifier(tag.getString("blockid")))).getDefaultState();
        } else {
            state = NbtHelper.toBlockState(tag.getCompound("blockid2"));
        }
        setTheBlockState(state);
    }

    //Had to move the startTracking here or else it wouldn't track propperly for a half second
    public void setTheBlockState(BlockState e) {
        dataTracker.set(BLOCK, Optional.of(e));
    }

    public BlockState getTheBlockState() {
        return dataTracker.get(BLOCK).get();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(BLOCK, Optional.of(Blocks.AIR.getDefaultState()));
    }

    @Override
    protected void updatePostDeath() {
        super.updatePostDeath();
        if (!world.isClient) {
            if (!hasExploaded) {
                this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.0F, Explosion.DestructionType.BREAK);
                this.hasExploaded = true;
            } else if ((this.deathTime == 20) && (world.getBlockState(this.getBlockPos()).getHardness(world, this.getBlockPos()) <= 50)) {
                    world.setBlockState(this.getBlockPos(), getTheBlockState());
            }
        }
    }
}