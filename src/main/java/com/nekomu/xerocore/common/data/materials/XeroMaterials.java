package com.nekomu.xerocore.common.data.materials;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.nekomu.xerocore.XeroCore;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class XeroMaterials {
    public static Material TestMaterial;

    public static void register() {


        TestMaterial = new Material.Builder(XeroCore.id("test_material"))
                .ingot()
                .liquid(new FluidBuilder().temperature(933))
                .color(0x080408).secondaryColor(0x000000).iconSet(MaterialIconSet.METALLIC)
                .flags(GENERATE_BOLT_SCREW, GENERATE_ROUND, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_RING,
                        GENERATE_FRAME, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_FINE_WIRE, GENERATE_DENSE)
                .components(Gold, 1, RedSteel, 1, Glowstone, 4)
                .blastTemp(2700, BlastProperty.GasTier.HIGH, GTValues.VA[GTValues.HV], 1200)
                .buildAndRegister();
    }
}
