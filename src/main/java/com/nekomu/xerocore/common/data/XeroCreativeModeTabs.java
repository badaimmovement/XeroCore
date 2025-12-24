package com.nekomu.xerocore.common.data;

import com.nekomu.xerocore.XeroCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;

public class XeroCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, XeroCore.MOD_ID);
}
