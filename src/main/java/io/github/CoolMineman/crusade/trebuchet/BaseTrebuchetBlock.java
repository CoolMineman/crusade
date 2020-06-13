package io.github.CoolMineman.crusade.trebuchet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class BaseTrebuchetBlock extends Block implements BlockEntityProvider {

    public BaseTrebuchetBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TrebuchetBlockEntity();
    }

}