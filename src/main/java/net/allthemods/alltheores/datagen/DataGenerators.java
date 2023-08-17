package net.allthemods.alltheores.datagen;

import net.allthemods.alltheores.datagen.client.BlockStates;
import net.allthemods.alltheores.datagen.client.ItemModels;
import net.allthemods.alltheores.datagen.server.*;
import net.allthemods.alltheores.infos.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Reference.MOD_ID)
public final class DataGenerators {
    private DataGenerators() {}

      @SubscribeEvent
    public static void gatherData(GatherDataEvent event) throws IOException {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        if (event.includeServer()) {
            BlockTags blockTags = new BlockTags(packOutput,event.getLookupProvider(), fileHelper);
            generator.addProvider(true, blockTags);
            generator.addProvider(true,new ItemTags(packOutput,event.getLookupProvider(), blockTags.contentsGetter(), fileHelper));
            generator.addProvider(true,new CraftingRecipes(packOutput));
            generator.addProvider(true,new LootTableProvider(packOutput, Collections.emptySet(),
                    List.of(new LootTableProvider.SubProviderEntry(LootTables::new, LootContextParamSets.BLOCK))));
        }
        if (event.includeClient()) {
            generator.addProvider(true,new BlockStates(generator, fileHelper));
            generator.addProvider(true,new ItemModels(generator, fileHelper));
        }
    }
}
