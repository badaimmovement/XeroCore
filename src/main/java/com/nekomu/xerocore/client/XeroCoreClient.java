package com.nekomu.xerocore.client;

import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderManager;
import com.nekomu.xerocore.XeroCore;
import com.nekomu.xerocore.client.renderer.machine.testrenderer;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class XeroCoreClient {

    private XeroCoreClient(){}

    public static void init(IEventBus modBus) {
        modBus.register(XeroCoreClient.class);

        DynamicRenderManager.register(XeroCore.id("test_renderer"), testrenderer.TYPE);
    }
    @SubscribeEvent
    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        event.register(testrenderer.BLACK_HOLE_MODEL);
        event.register(testrenderer.MAGIC1_MODEL);
        event.register(testrenderer.MAGIC2_MODEL);
        event.register(testrenderer.MAGIC3_MODEL);
    }
}
