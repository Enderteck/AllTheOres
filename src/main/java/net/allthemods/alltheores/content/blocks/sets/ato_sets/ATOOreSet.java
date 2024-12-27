package net.allthemods.alltheores.content.blocks.sets.ato_sets;

import net.allthemods.alltheores.content.blocks.ore.*;
import net.allthemods.alltheores.content.blocks.sets.BlockSet;
import net.allthemods.alltheores.content.blocks.sets.ESetTypes;
import net.allthemods.alltheores.infos.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import static net.allthemods.alltheores.registry.ATORegistry.BLOCKS;
import static net.allthemods.alltheores.registry.ATORegistry.blockItem;

public class ATOOreSet extends BlockSet {

    public final ESetTypes type;
    public final String hardness;

    private static final List<ATOOreSet> instances = new ArrayList<>();

    public static List<ATOOreSet> getOreSets() {
        return instances;
    }

    public final int veinSize;
    public final int minY;
    public final int maxY;
    public final int count;

    //Feature
    public final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ORE_FEATURE;
    public final ResourceKey<PlacedFeature> PLACED_ORE_FEATURE;

    // Biome Modifier
    public final ResourceKey<BiomeModifier> OVERWORLD_BIOME_MODIFIER;
    public final ResourceKey<BiomeModifier> NETHER_BIOME_MODIFIER;
    public final ResourceKey<BiomeModifier> END_MODIFIER;

    // Item Tags
    public final TagKey<Item> DROP_ITEM_TAG;

    // Block Tags
    public final TagKey<Block> ORE_BLOCK_TAG;
    public final TagKey<Block> DROP_BLOCK_TAG;

    // BlockItem Tags
    public final TagKey<Item> ORE_BLOCK_ITEM_TAG;
    public final TagKey<Item> DROP_BLOCK_ITEM_TAG;

    // Items
    public final DeferredHolder<Item, Item> DROP;

    // Blocks
    public final DeferredHolder<Block, Block> STONE_ORE_BLOCK;
    public final DeferredHolder<Block, Block> SLATE_ORE_BLOCK;
    public final DeferredHolder<Block, Block> NETHER_ORE_BLOCK;
    public final DeferredHolder<Block, Block> END_ORE_BLOCK;
    public final DeferredHolder<Block, Block> OTHER_ORE_BLOCK;

    public final DeferredHolder<Block, Block> DROP_BLOCK;

    // BlockItems
    public final DeferredHolder<Item, BlockItem> STONE_ORE_BLOCK_ITEM;
    public final DeferredHolder<Item, BlockItem> SLATE_ORE_BLOCK_ITEM;
    public final DeferredHolder<Item, BlockItem> NETHER_ORE_BLOCK_ITEM;
    public final DeferredHolder<Item, BlockItem> END_ORE_BLOCK_ITEM;
    public final DeferredHolder<Item, BlockItem> OTHER_ORE_BLOCK_ITEM;

    public final DeferredHolder<Item, BlockItem> DROP_BLOCK_ITEM;

    public ATOOreSet(String name, ESetTypes type, String hardness, DeferredHolder<Item, Item> drop, DeferredHolder<Block, Block> drop_block, int veinSize, int minY, int maxY, int count) {
        super(name);
        instances.add(this);

        this.type = type;
        this.hardness = hardness;

        this.veinSize = veinSize;
        this.minY = minY;
        this.maxY = maxY;
        this.count = count;

        // Feature
        CONFIGURED_ORE_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, String.format("ore_%s", name)));
        PLACED_ORE_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, String.format("ore_%s_placed", name)));

        // Biome Modifier
        OVERWORLD_BIOME_MODIFIER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, String.format("%s_overworld", name)));
        NETHER_BIOME_MODIFIER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, String.format("%s_nether", name)));
        END_MODIFIER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, String.format("%s_end", name)));

        switch (type) {
            case INGOT:
                DROP_ITEM_TAG = ItemTags.create(Reference.raw_materials(name));
                DROP_BLOCK_TAG = BlockTags.create(Reference.block(String.format("raw_%s", name)));
                break;
            case GEM:
                DROP_ITEM_TAG = ItemTags.create(Reference.gem(name));
                DROP_BLOCK_TAG = BlockTags.create(Reference.block(name));
                break;
            case DUST:
                DROP_ITEM_TAG = ItemTags.create(Reference.dust(name));
                DROP_BLOCK_TAG = BlockTags.create(Reference.block(name));
                break;
            default:
                throw new IllegalArgumentException("Invalid Type: " + name);
        }

        //Block Tags
        ORE_BLOCK_TAG = BlockTags.create(Reference.ore(name));

        //BlockItem Tags
        ORE_BLOCK_ITEM_TAG = ItemTags.create(Reference.ore(name));
        DROP_BLOCK_ITEM_TAG = ItemTags.create(DROP_BLOCK_TAG.location());

        // Items
        DROP = drop;

        // Blocks
        STONE_ORE_BLOCK = BLOCKS.register(String.format("%s_ore", name), OreBlockStone::new);
        SLATE_ORE_BLOCK = BLOCKS.register(String.format("deepslate_%s_ore", name), OreBlockSlate::new);
        NETHER_ORE_BLOCK = BLOCKS.register(String.format("nether_%s_ore", name), OreBlockNether::new);
        END_ORE_BLOCK = BLOCKS.register(String.format("end_%s_ore", name), OreBlockEnd::new);
        OTHER_ORE_BLOCK = BLOCKS.register(String.format("other_%s_ore", name), OreBlockOther::new);

        DROP_BLOCK = drop_block;

        // BlockItems
        STONE_ORE_BLOCK_ITEM = blockItem(STONE_ORE_BLOCK);
        SLATE_ORE_BLOCK_ITEM = blockItem(SLATE_ORE_BLOCK);
        NETHER_ORE_BLOCK_ITEM = blockItem(NETHER_ORE_BLOCK);
        END_ORE_BLOCK_ITEM = blockItem(END_ORE_BLOCK);
        OTHER_ORE_BLOCK_ITEM = blockItem(OTHER_ORE_BLOCK);

        DROP_BLOCK_ITEM = DeferredHolder.create(Registries.ITEM, drop_block.getId());
    }
}
