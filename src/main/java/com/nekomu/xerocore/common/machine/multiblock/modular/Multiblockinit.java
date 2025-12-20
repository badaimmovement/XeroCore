package com.nekomu.xerocore.common.machine.multiblock.modular;

import com.nekomu.xerocore.common.machine.multiblock.steam.*;

public class Multiblockinit {
    public static void init(){
        SteamChemicalBath.init();
        SteamCompressor.init();
        SteamForgeHammer.init();
        SteamMixer.init();
        SteamOreWasher.init();
        testmachine.init();
    }
}
