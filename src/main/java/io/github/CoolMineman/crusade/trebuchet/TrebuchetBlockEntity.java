package io.github.CoolMineman.crusade.trebuchet;

import java.util.UUID;

import io.github.CoolMineman.crusade.CrusadeMod;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TrebuchetBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
    private static final Double[][] entityLocationCacheXY = { { 4d, 6d }, { 4d, 10d }, { 3d, 14d }, { 1d, 17.5d },
            { -3d, 19d }, { -6.5d, 21d }, };

    public int armState = 0;
    // see TrebuchetRenderer
    public int placementDirection = 0;
    public boolean hasEntityToThrow = false;
    public UUID entityToThrow = null;

    public TrebuchetBlockEntity(BlockPos pos, BlockState state) {
        super(CrusadeMod.TREBUCHET_ENTITY, pos, state);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);

        // Save the current value of the number to the tag
        tag.putInt("armState", armState);
        tag.putInt("placementDirection", placementDirection);
        tag.putBoolean("hasEntityToThrow", hasEntityToThrow);
        if (hasEntityToThrow) {
            tag.putUuid("entityToThrow", entityToThrow);
        }

        return tag;
    }

    // Deserialize the BlockEntity
    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        armState = tag.getInt("armState");
        placementDirection = tag.getInt("placementDirection");
        hasEntityToThrow = tag.getBoolean("hasEntityToThrow");
        if (hasEntityToThrow) {
            entityToThrow = tag.getUuid("entityToThrow");
        }

    }

    int tickCounter = 0;

    public void tick() {
        if (!(this.world instanceof ServerWorld))
            return;

        switch (tickCounter) {
            case 1:
                this.setArmState(1);
                break;
            case 2:
                this.setArmState(2);
                break;
            case 3:
                this.setArmState(3);
                break;
            case 4:
                this.setArmState(4);
                break;
            case 5:
                this.setArmState(5);
                break;
            case 6:
                this.setArmState(5);
                break;
            case 7:
                this.setArmState(4);
                break;
            case 8:
                this.setArmState(4);
                break;
            case 9:
                this.setArmState(3);
                break;
            case 10:
                this.setArmState(3);
                break;
            case 11:
                this.setArmState(2);
                break;
            case 12:
                this.setArmState(2);
                break;
            case 13:
                this.setArmState(1);
                break;
            case 14:
                this.setArmState(1);
                break;
            default:
                this.tickCounter = 0;
                this.setArmState(0);
                break;
        }

        if (hasEntityToThrow) {
            //System.out.println(this.world);
            Entity e = (((ServerWorld) this.world).getEntity(entityToThrow));
            if (e != null) {
                updateEntity(e);
                tickCounter++;
            } else {
                hasEntityToThrow = false;
                entityToThrow = null;
            }
        } else if (tickCounter > 0) {
            tickCounter++;
        }
    }

    private void updateEntity(Entity e) {
        if (e.hasVehicle()) {
            e.stopRiding();
        }

        if (placementDirection == 0) {
            e.setPosition(this.pos.getX() + 0.25, this.pos.getY() + entityLocationCacheXY[armState][1],
                    this.pos.getZ() + entityLocationCacheXY[armState][0]);
        } else if (placementDirection == 1) {
            e.setPosition(this.pos.getX() + entityLocationCacheXY[armState][0],
                    this.pos.getY() + entityLocationCacheXY[armState][1], this.pos.getZ() + 0.75);
        } else if (placementDirection == 2) {
            e.setPosition(this.pos.getX() - entityLocationCacheXY[armState][0] + 1,
                    this.pos.getY() + entityLocationCacheXY[armState][1], this.pos.getZ() + 0.25);
        } else if (placementDirection == 3) {
            e.setPosition(this.pos.getX() + 0.25, this.pos.getY() + entityLocationCacheXY[armState][1],
                    this.pos.getZ() - entityLocationCacheXY[armState][0] + 1);
        }

        //System.out.println(e);

        if (e instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) e).networkHandler.requestTeleport(e.getX(), e.getY(), e.getZ(), 0f, 0f);
        }
        if (armState == 5) {
            if (e instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) e).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(e.getId(),
                        new Vec3d(-5000d, 50d, -5000d).multiply(placementDirectionVec3d())));
            } else {
                // e.setVelocity(0d, 5d, -10d);
                e.setVelocity(new Vec3d(-8d, 4d, -8d).multiply(placementDirectionVec3d()));
                e.velocityDirty = true;
            }
            this.hasEntityToThrow = false;
            this.entityToThrow = null;
        }
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        this.readNbt(tag);
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        return this.writeNbt(tag);
    }

    public void setArmState(int i) {
        if (this.armState != i) {
            this.armState = i;
            this.sync();
        }
    }

    public Vec3d placementDirectionVec3d() {
        if (placementDirection == 0) {
            return new Vec3d(0d, 1d, 1d);
        } else if (placementDirection == 1) {
            return new Vec3d(1d, 1d, 0d);
        } else if (placementDirection == 2) {
            return new Vec3d(-1d, 1d, 0d);
        } else if (placementDirection == 3) {
            return new Vec3d(0d, 1d, -1d);
        } else {
            // Unreachable, hopefully
            return null;
        }
    }
}