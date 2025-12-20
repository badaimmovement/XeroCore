package com.nekomu.xerocore.common.machine.multiblock.steam;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;
import net.minecraft.network.chat.Component;

import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.nekomu.xerocore.api.registries.XeroRegistries.REGISTRATE;

public class SteamMixer {
    public static final MultiblockMachineDefinition STEAM_MIXER = REGISTRATE.multiblock("steam_mixer", SteamParallelMultiblockMachine::new)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeType(GTRecipeTypes.MIXER_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .addOutputLimit(ItemRecipeCapability.CAP, 1)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle(" XXX "," XXX "," XXX ","  F  ")
                    .aisle("XXXXX","X P X","X   X","  F  ")
                    .aisle("XXXXX","XPGPX","X G X","FFGFF")
                    .aisle("XXXXX","X P X","X   X","  F  ")
                    .aisle(" XXX "," XCX "," XXX ","  F  ")

                    .where('C', Predicates.controller(blocks(definition.getBlock())))
                    .where('G', blocks(CASING_BRONZE_GEARBOX.get()))
                    .where('P', blocks(CASING_BRONZE_PIPE.get()))
                    .where('F', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.Bronze)))
                    .where(' ', Predicates.any())
                    .where('X', blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(40)
                            .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1)))
                    .build())
            .model(GTMachineModels.createWorkableCasingMachineModel(
                    GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/chemical_bath")))
            .register();
    public static void init() {

    }
}