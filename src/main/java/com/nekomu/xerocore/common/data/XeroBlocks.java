package com.nekomu.xerocore.common.data;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.block.ActiveBlock;
import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.common.block.CoilBlock;
import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.nekomu.xerocore.XeroCore;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import java.util.function.Supplier;

import static com.nekomu.xerocore.api.registries.XeroRegistries.REGISTRATE;


public class XeroBlocks {
    public static final BlockEntry<Block> TESTONE_CASING = createCasingBlock("testone_casing",
            XeroCore.id("block/casings/solid/testone_casing"));
    public static final BlockEntry<Block> TESTTWO_CASING = createCasingBlock("testtwo_casing",
            XeroCore.id("block/casings/solid/testtwo_casing"));
    public static final BlockEntry<Block> TESTTHREE_CASING = createCasingBlock("testthree_casing",
            XeroCore.id("block/casings/solid/testthree_casing"));



    private static BlockEntry<Block> createGlassCasingBlock(String name, ResourceLocation texture,
                                                            Supplier<Supplier<RenderType>> type) {
        NonNullFunction<BlockBehaviour.Properties, Block> supplier = GlassBlock::new;
        return REGISTRATE.block(name, supplier)
                .initialProperties(() -> Blocks.GLASS)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(type)
                .exBlockstate(GTModels.cubeAllModel(texture))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .build()
                .register();
    }

    public record BlockGroup(BlockEntry<Block> block, BlockEntry<StairBlock> stairs, BlockEntry<SlabBlock> slab) {}

    public static BlockGroup createStoneBuildingBlock(String name, ResourceLocation texture) {
        return createStoneBuildingBlocks(name, texture, () -> Blocks.DEEPSLATE_BRICKS,
                () -> RenderType::solid);
    }

    public static BlockGroup createStoneBuildingBlocks(String name,
                                                       ResourceLocation texture,
                                                       NonNullSupplier<? extends Block> properties,
                                                       Supplier<Supplier<RenderType>> type) {
        BlockEntry<Block> fullBlock = REGISTRATE.block(name, Block::new)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn(((blockState, blockGetter, blockPos, entityType) -> false))
                        .speedFactor(1.25f)
                        .requiresCorrectToolForDrops()
                        .strength(5, 6)
                        .sound(SoundType.NETHERITE_BLOCK))
                .addLayer(type)
                .exBlockstate(GTModels.cubeAllModel(texture))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();

        BlockEntry<StairBlock> stairBlock = REGISTRATE
                .block(name + "_stairs", s -> new StairBlock(() -> fullBlock.get().defaultBlockState(), s))
                .initialProperties(fullBlock)
                .properties(p -> p.isValidSpawn(((blockState, blockGetter, blockPos, entityType) -> false))
                        .speedFactor(1.25f)
                        .requiresCorrectToolForDrops()
                        .strength(5, 6)
                        .sound(SoundType.NETHERITE_BLOCK))
                .addLayer(type)
                .blockstate((ctx, prov) -> prov.stairsBlock(ctx.get(), texture))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();

        BlockEntry<SlabBlock> slabBlock = REGISTRATE.block(name + "_slab", SlabBlock::new)
                .initialProperties(fullBlock)
                .properties(p -> p.isValidSpawn(((blockState, blockGetter, blockPos, entityType) -> false))
                        .speedFactor(1.25f)
                        .requiresCorrectToolForDrops()
                        .strength(5, 6)
                        .sound(SoundType.NETHERITE_BLOCK))
                .addLayer(type)
                .blockstate((ctx, prov) -> {
                    var slab = prov.models().slab(ctx.getName(), texture, texture, texture);
                    var slabTop = prov.models().slabTop(ctx.getName() + "_top", texture, texture, texture);
                    var fullModel = prov.models().cubeAll(name, texture);

                    prov.slabBlock((SlabBlock) ctx.get(), slab, slabTop, fullModel);
                })
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();

        return new BlockGroup(fullBlock, stairBlock, slabBlock);
    }

    public static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return createCasingBlock(name, Block::new, texture, () -> Blocks.IRON_BLOCK,
                () -> RenderType::cutoutMipped);
    }

    public static BlockEntry<Block> createCasingBlock(String name,
                                                      NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                      ResourceLocation texture,
                                                      NonNullSupplier<? extends Block> properties,
                                                      Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(type)
                .exBlockstate(GTModels.cubeAllModel(texture))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .build()
                .register();
    }

    private static BlockEntry<Block> createSidedCasingBlock(String name, ResourceLocation texture) {
        return REGISTRATE.block(name, Block::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(GTModels.createSidedCasingModel(texture))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .build()
                .register();
    }

    private static BlockEntry<CoilBlock> createCoilBlock(ICoilType coilType) {
        BlockEntry<CoilBlock> coilBlock = REGISTRATE
                .block("%s_coil_block".formatted(coilType.getName()), p -> new CoilBlock(p, coilType))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(GTModels.createCoilModel(coilType))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .build()
                .register();
        GTCEuAPI.HEATING_COILS.put(coilType, coilBlock);
        return coilBlock;
    }
/*
    private static BlockEntry<CoilBlock> createCoilBlockWithEntity(ICoilType coilType,
                                                                   NonNullBiConsumer<DataGenContext<Block, CoilBlock>, RegistrateBlockstateProvider> blockState) {
        BlockEntry<CoilBlock> coilBlock = REGISTRATE
                .block("%s_coil_block".formatted(coilType.getName()), p -> (CoilBlock) new CosmicCoilBlock(p, coilType))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .addLayer(() -> RenderType::translucent)
                .blockstate(blockState)
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .simpleItem()
                .blockEntity(XeroCoilBlockEntity::new)
                .renderer(() -> NebulaeCoilRenderer::new)
                .build()
                .register();
        GTCEuAPI.HEATING_COILS.put(coilType, coilBlock);
        return coilBlock;
    }
*/
    protected static BlockEntry<ActiveBlock> createActiveCasing(String name, String baseModelPath) {
        return REGISTRATE.block(name, ActiveBlock::new)
                .initialProperties(() -> Blocks.NETHERITE_BLOCK)
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(GTModels.createActiveModel(XeroCore.id(baseModelPath)))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), XeroCore.id(baseModelPath)))
                .build()
                .register();
    }

    private static BlockBehaviour.Properties ironProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                .requiresCorrectToolForDrops()
                .strength(5, 6)
                .sound(SoundType.COPPER);
    }

    public static BlockEntry<LanternBlock> createLantern(String name, ResourceLocation texture,
                                                         ResourceLocation textureHanging) {
        return createLantern(
                name,
                LanternBlock::new,
                texture,
                textureHanging,
                () -> Blocks.LANTERN,
                () -> RenderType::cutout,
                15);
    }

    public static BlockEntry<LanternBlock> createLantern(String name,
                                                         NonNullFunction<BlockBehaviour.Properties, LanternBlock> blockSupplier,
                                                         ResourceLocation texture, ResourceLocation hangingTexture,
                                                         NonNullSupplier<? extends Block> properties,
                                                         java.util.function.Supplier<java.util.function.Supplier<RenderType>> type,
                                                         int lightLevel) {
        return REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties(prop -> prop
                        .strength(3.5f)
                        .sound(SoundType.LANTERN)
                        .requiresCorrectToolForDrops()
                        .noOcclusion()
                        .lightLevel(l -> lightLevel))
                .addLayer(type)
                .blockstate((context, provider) -> {

                    var standing = provider.models()
                            .withExistingParent(context.getName(), provider.mcLoc("block/lantern"))
                            .texture("lantern", texture)
                            .texture("particle", texture);

                    var hanging = provider.models()
                            .withExistingParent(context.getName() + "_hanging", provider.mcLoc("block/lantern_hanging"))
                            .texture("lantern", hangingTexture)
                            .texture("particle", hangingTexture);

                    provider.getVariantBuilder(context.get())
                            .forAllStates(state -> {
                                boolean isHanging = state.getValue(BlockStateProperties.HANGING);
                                return new ConfiguredModel[] {
                                        new ConfiguredModel(
                                                isHanging ? hanging : standing)
                                };
                            });
                })
                .tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .item(BlockItem::new)
                .model((context, provider) -> provider.withExistingParent(context.getName(),
                        provider.modLoc("block/" + context.getName())))
                .build()
                .register();
    }

    public static BlockEntry<Block> createStoneCasingBlock(String name,
                                                           ResourceLocation texture) {
        return REGISTRATE.block(name, Block::new)
                .initialProperties(() -> Blocks.DEEPSLATE)
                .properties(p -> p.isValidSpawn(((blockState, blockGetter, blockPos, entityType) -> false))
                        .requiresCorrectToolForDrops()
                        .strength(5, 6)
                        .sound(SoundType.DEEPSLATE_TILES))
                .addLayer(() -> RenderType::solid)
                .exBlockstate(GTModels.cubeAllModel(texture))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
    }

    public static void init() {}
}