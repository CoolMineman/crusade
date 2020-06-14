package io.github.CoolMineman.crusade.trebuchet;

import java.util.UUID;

import io.github.CoolMineman.crusade.CrusadeMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class TrebuchetBlockEntity extends BlockEntity implements Tickable {
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
        if ((tickCounter / 2) <= 5)
            this.armState = (tickCounter / 2);
        else
            this.armState = 0;
        tickCounter++;

        if (hasEntityToThrow && this.world instanceof ServerWorld) {
            Entity e = (((ServerWorld)this.world).getEntity(entityToThrow));
            System.out.println(e);
            if (e instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)e).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(e.getEntityId(), new Vec3d(5000d, 50d, 0d)));
                this.hasEntityToThrow = false;
                this.entityToThrow = null;
            }
        }

        if (tickCounter > 5 * 20) tickCounter = 0;
    }
}