package io.github.CoolMineman.crusade.trebuchet;

import java.util.UUID;

import io.github.CoolMineman.crusade.CrusadeMod;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class TrebuchetBlockEntity extends BlockEntity implements Tickable, BlockEntityClientSerializable {
    private static final Double[][] entityLocationCacheXY = {{4d, 6d}, {4d, 10d}, {3d, 14d}, {1d, 17.5d}, {-3d, 19d}, {-6.5d, 21d}, }; 

    public int armState = 0;
    public boolean hasEntityToThrow = false;
    public UUID entityToThrow = null;

    public TrebuchetBlockEntity() {
        super(CrusadeMod.TREBUCHET_ENTITY);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        // Save the current value of the number to the tag
        tag.putInt("armState", armState);
        tag.putBoolean("hasEntityToThrow", hasEntityToThrow);
        if (hasEntityToThrow) {
            tag.putUuid("entityToThrow", entityToThrow);
        }

        return tag;
    }

    // Deserialize the BlockEntity
    @Override
    public void fromTag(BlockState state,CompoundTag tag) {
        super.fromTag(state, tag);
        armState = tag.getInt("armState");
        hasEntityToThrow = tag.getBoolean("hasEntityToThrow");
        if (hasEntityToThrow) {
            entityToThrow = tag.getUuid("entityToThrow");
        }

    }

    int tickCounter = 0;
    @Override
    public void tick() {
        if (!(this.world instanceof ServerWorld)) return;

        if ((tickCounter / 2) <= 5)
            this.setArmState(tickCounter / 2);
        else
            this.setArmState(0);
        tickCounter++;

        //this.armState = 5;

        if (hasEntityToThrow) {
            System.out.println(this.world);
            Entity e = (((ServerWorld)this.world).getEntity(entityToThrow));
            if (e != null)
                updateEntity(e);
            else {
                hasEntityToThrow = false;
                entityToThrow = null;
            }
        }

        if (tickCounter > 5 * 20) tickCounter = 0;
    }

    private void updateEntity(Entity e) {
        e.updatePosition(this.pos.getX() + 0.25, this.pos.getY() + entityLocationCacheXY[armState][1], this.pos.getZ() + entityLocationCacheXY[armState][0]);
        System.out.println(e);
        
        if (e instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity)e).networkHandler.requestTeleport(e.getX(), e.getY(), e.getZ(), 180f, 45f);
        }
        if (armState == 5) {
            if (e instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)e).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(e.getEntityId(), new Vec3d(0d, 50d, -5000d)));
            } else {
                //e.setVelocity(0d, 5d, -10d);
                e.setVelocity(0, 0d , 0d);
            }
            this.hasEntityToThrow = false;
            this.entityToThrow = null;
        }
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.fromTag(null, tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return this.toTag(tag);
    }

    public void setArmState(int i) {
        if (this.armState != i) {
            this.armState = i;
            this.sync();
        }
    }
}