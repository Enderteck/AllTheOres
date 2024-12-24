package net.allthemods.alltheores.content.blocks.ore;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class OreBlockOther extends OreBlock {

    public static final Properties properties = BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.0F, 3.0F);

    public OreBlockOther() {
        super(0, 7, properties);
    }
}
