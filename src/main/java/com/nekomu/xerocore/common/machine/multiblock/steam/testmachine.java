package com.nekomu.xerocore.common.machine.multiblock.steam;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;

import static com.gregtechceu.gtceu.api.pattern.Predicates.abilities;
import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_PTFE_INERT;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.*;
import static com.nekomu.xerocore.api.registries.XeroRegistries.REGISTRATE;

public class testmachine {
    public static final MultiblockMachineDefinition TEST_MACHINE = REGISTRATE.multiblock("test_machine", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.ALLOY_SMELTER_RECIPES)
            .recipeModifiers(OC_NON_PERFECT_SUBTICK, BATCH_MODE)
            .appearanceBlock(CASING_PTFE_INERT)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXX", "XXX", "XXX")
                    .aisle("XXX", "X#X", "XXX")
                    .aisle("XXX", "XSX", "XXX")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where('#', Predicates.air())
                    .where('X', blocks(CASING_PTFE_INERT.get())
                            .or(abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(abilities(PartAbility.INPUT_ENERGY).setPreviewCount(2))
                            .or(abilities(PartAbility.MAINTENANCE).setPreviewCount(1)))
                    .build())
            .model(GTMachineModels.createWorkableCasingMachineModel(
                    GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
                    GTCEu.id("block/machines/ore_washer")))
            .register();

    public static void init() {
    }
}

