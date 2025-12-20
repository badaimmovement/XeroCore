package com.nekomu.xerocore.common.machine.multiblock.steam;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.block.OreBlock;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderHelper;
import com.gregtechceu.gtceu.common.block.BoilerFireboxType;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.swing.text.html.HTML;

import static com.gregtechceu.gtceu.api.pattern.Predicates.any;
import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.nekomu.xerocore.api.registries.XeroRegistries.REGISTRATE;

public class SteamForgeHammer {
    public static final MultiblockMachineDefinition STEAM_FORGE_HAMMER = REGISTRATE.multiblock("steam_forge_hammer", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(BRONZE_BRICKS_HULL)
            .recipeType(GTRecipeTypes.FORGE_HAMMER_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .addOutputLimit(ItemRecipeCapability.CAP, 1)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXX","F F","F F","XXX")
                    .aisle("XBX","   "," P ","XXX")
                    .aisle("XCX","F F","F F","XXX")
                    .where('C', Predicates.controller(blocks(definition.getBlock())))
                    .where('X', blocks(CASING_BRONZE_BRICKS.get())
                            .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1)))
                    .where('F', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.Bronze)))
                    .where('P', blocks(Blocks.PISTON))
                    .where('B', blocks(ChemicalHelper.getBlock(TagPrefix.block, GTMaterials.WroughtIron)))
                    .where(' ', any())
                    .build())
            .model(GTMachineModels.createWorkableCasingMachineModel(
                            GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                            GTCEu.id("block/machines/forge_hammer"))
                    .andThen(b -> b.addDynamicRenderer(() ->
                            DynamicRenderHelper.makeBoilerPartRender(BoilerFireboxType.BRONZE_FIREBOX, CASING_BRONZE_BRICKS))))
            .register();
    public static void init() {

    }
}
