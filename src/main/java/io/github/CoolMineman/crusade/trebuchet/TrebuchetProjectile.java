package io.github.CoolMineman.crusade.trebuchet;

import java.util.Optional;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class TrebuchetProjectile extends MobEntityWithAi {
    private boolean hasExploaded = false;
    //NOT OPTIONAL
    private static final TrackedData<Optional<BlockState>> BLOCK = DataTracker.registerData(TrebuchetProjectile.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE);

    public TrebuchetProjectile(EntityType<? extends MobEntityWithAi> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putString("blockid", dataTracker.get(BLOCK).get().getBlock().toString());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        dataTracker.set(BLOCK, Optional.of(Registry.BLOCK.get((new Identifier(tag.getString("blockid")))).getDefaultState()));
    }

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
        if (!hasExploaded && !world.isClient) {
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.0F, Explosion.DestructionType.BREAK);
            this.hasExploaded = true;
        }
    }
}