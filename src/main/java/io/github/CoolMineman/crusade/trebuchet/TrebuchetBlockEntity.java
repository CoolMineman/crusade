package io.github.CoolMineman.crusade.trebuchet;

import io.github.CoolMineman.crusade.CrusadeMod;
import net.minecraft.block.entity.BlockEntity;

public class TrebuchetBlockEntity extends BlockEntity {
    public TrebuchetBlockEntity() {
       super(CrusadeMod.TREBUCHET_ENTITY);
       System.out.println("exist");
    }
 }