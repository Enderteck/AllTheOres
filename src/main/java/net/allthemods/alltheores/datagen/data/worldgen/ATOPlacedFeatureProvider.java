package net.allthemods.alltheores.datagen.data.worldgen;

import net.allthemods.alltheores.content.blocks.sets.ATOSetHelper;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ATOPlacedFeatureProvider {

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);

        ATOSetHelper.applyToOre(group -> PlacementUtils.register(
                context,
                group.PLACED_ORE_FEATURE,
                features.getOrThrow(
                        group.CONFIGURED_ORE_FEATURE),
                List.of(CountPlacement.of(group.count),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(group.minY),
                                VerticalAnchor.absolute(group.maxY)
                        ),
                        BiomeFilter.biome()
                )));
    }
}
