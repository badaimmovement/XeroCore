package com.nekomu.xerocore;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import com.nekomu.xerocore.api.registries.XeroRegistries;
import com.nekomu.xerocore.common.machine.multiblock.modular.Multiblockinit;
import com.nekomu.xerocore.common.machine.multiblock.steam.LargeAlloySmelter;
import com.nekomu.xerocore.common.machine.multiblock.steam.SteamForgeHammer;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@SuppressWarnings("unused")
@GTAddon
public class XeroGTAddon implements IGTAddon {

    @Override
    public GTRegistrate getRegistrate() {
        return XeroRegistries.REGISTRATE;
    }

    @Override
    public void initializeAddon() {
        Multiblockinit.init();
    }

    @Override
    public String addonModId() {
        return XeroCore.MOD_ID;
    }

    @Override
    public void registerTagPrefixes() {
        // CustomTagPrefixes.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        // CustomRecipes.init(provider);
    }

    @Override
    public void registerElements() {
        // CustomElements.init();
    }
}
