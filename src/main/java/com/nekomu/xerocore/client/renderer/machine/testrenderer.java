package com.nekomu.xerocore.client.renderer.machine;

import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderType;
import com.gregtechceu.gtceu.client.util.ModelUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.Codec;
import com.nekomu.xerocore.XeroCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.data.ModelData;
import org.joml.Quaternionf;

import java.util.List;

import static com.hollingsworth.arsnouveau.common.block.tile.LightTile.random;



public class testrenderer extends DynamicRender<WorkableElectricMultiblockMachine, testrenderer> {

    public static final testrenderer INSTANCE = new testrenderer();
    public static final Codec<testrenderer> CODEC = Codec.unit(testrenderer.INSTANCE);
    public static final DynamicRenderType<WorkableElectricMultiblockMachine,testrenderer> TYPE = new DynamicRenderType<>(
            testrenderer.CODEC);

    public static final ResourceLocation BLACK_HOLE_MODEL = XeroCore.id("block/test/blackhole");
    public static final ResourceLocation MAGIC1_MODEL = XeroCore.id( "block/test/magic1");
    public static final ResourceLocation MAGIC2_MODEL = XeroCore.id( "block/test/magic2");
    public static final ResourceLocation MAGIC3_MODEL = XeroCore.id( "block/test/magic3");

    private static BakedModel BlackHoleModel = null;
    private static BakedModel Magic1Model = null;
    private static BakedModel Magic2Model = null;
    private static BakedModel Magic3Model = null;

    private testrenderer() {
        ModelUtils.registerBakeEventListener(true, event->{
            BlackHoleModel = event.getModels().get(BLACK_HOLE_MODEL);
            Magic1Model = event.getModels().get(MAGIC1_MODEL);
            Magic2Model = event.getModels().get(MAGIC2_MODEL);
            Magic3Model = event.getModels().get(MAGIC3_MODEL);
        });
    }
    @Override
    public DynamicRenderType<WorkableElectricMultiblockMachine, testrenderer> getType() {
        return TYPE;
    }
    @Override
    public int getViewDistance() {
        return 256;
    }
    @Override
    public AABB getRenderBoundingBox(WorkableElectricMultiblockMachine machine) {
        return new AABB(machine.getPos()).inflate(getViewDistance(), 16, getViewDistance());
    }
    @Override
    public boolean shouldRenderOffScreen(WorkableElectricMultiblockMachine machine) {
        return true;
    }

    @Override
    public void render(WorkableElectricMultiblockMachine machine, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (!machine.isFormed()) return;

        float totalTick = (Minecraft.getInstance().level.getGameTime() + partialTick);
        VertexConsumer consumer = buffer.getBuffer(Sheets.translucentCullBlockSheet());
        poseStack.pushPose();

        poseStack.translate(0.5f, 0.5f, 0.5f);
        BlockState state = machine.getBlockState();
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);

        float forward = 48f;
        switch (facing) {
            case NORTH -> poseStack.translate(0f, 0f, forward);
            case SOUTH -> poseStack.translate(0f, 0f, -forward);
            case EAST  -> poseStack.translate(-forward, 0f, 0f);
            case WEST  -> poseStack.translate(forward, 0f, 0f);
        }

        renderblackhole(poseStack, consumer, totalTick, packedLight, packedOverlay);
        rendermagicone(poseStack, consumer, totalTick, packedLight, packedOverlay);
        rendermagictwo(poseStack, consumer, totalTick, packedLight, packedOverlay);
        rendermagicthree(poseStack, consumer, totalTick, packedLight, packedOverlay);

        poseStack.popPose();
    }

    public void renderblackhole(PoseStack poseStack, VertexConsumer consumer,
                                float totalTick, int packedLight, int packedOverlay){
        poseStack.pushPose();
        Quaternionf rot = new Quaternionf()
                .rotateXYZ(0f, 0f, 0f)
                .rotateAxis(totalTick * Mth.TWO_PI / 80, 0f, 1f, 0f);
        poseStack.mulPose(rot);
        PoseStack.Pose pose = poseStack.last();

        List<BakedQuad> quads = BlackHoleModel.getQuads(null, null, random, ModelData.EMPTY, null);
        for (BakedQuad quad : quads) {
            consumer.putBulkData(pose, quad, 1f, 1f, 1f, 1f, packedLight, packedOverlay, false);
        }
        poseStack.popPose();
    }

    public void rendermagicone(PoseStack poseStack, VertexConsumer consumer,
                               float totalTick, int packedLight, int packedOverlay){
        poseStack.pushPose();
        Quaternionf rot = new Quaternionf()
                .rotateXYZ(0f, 0f, 0f)
                .rotateAxis(totalTick * Mth.TWO_PI / 80, 0f, 1f, 0f);
        poseStack.mulPose(rot);
        PoseStack.Pose pose = poseStack.last();
        int fullLight = 0xF000F0;
        List<BakedQuad> quads = Magic1Model.getQuads(null, null, random, ModelData.EMPTY, null);
        for (BakedQuad quad : quads) {
            consumer.putBulkData(pose, quad, 0.8f, 0.4f, 1f, 1f, fullLight, packedOverlay, false);
        }
        poseStack.popPose();
    }

    public void rendermagictwo(PoseStack poseStack, VertexConsumer consumer,
                               float totalTick, int packedLight, int packedOverlay){
        poseStack.pushPose();
        Quaternionf rot = new Quaternionf()
                .rotateXYZ(0f, 0f, 0f)
                .rotateAxis(totalTick * Mth.TWO_PI / 80, 0f, 1f, 0f);
        poseStack.mulPose(rot);
        PoseStack.Pose pose = poseStack.last();
        int fullLight = 0xF000F0;
        List<BakedQuad> quads = Magic2Model.getQuads(null, null, random, ModelData.EMPTY, null);
        for (BakedQuad quad : quads) {
            consumer.putBulkData(pose, quad, 0.8f, 0.4f, 1f, 1f, fullLight, packedOverlay, false);
        }
        poseStack.popPose();

    }

    public void rendermagicthree(PoseStack poseStack, VertexConsumer consumer,
                                 float totalTick, int packedLight, int packedOverlay){
        poseStack.pushPose();
        Quaternionf rot = new Quaternionf()
                .rotateXYZ(0f, 0f, 0f)
                .rotateAxis(totalTick * Mth.TWO_PI / 80, 0f, -1f, 0f);
        poseStack.mulPose(rot);
        PoseStack.Pose pose = poseStack.last();

        int fullLight = 0xF000F0;
        List<BakedQuad> quads = Magic3Model.getQuads(null, null, random, ModelData.EMPTY, null);
        for (BakedQuad quad : quads) {
            consumer.putBulkData(pose, quad, 0.8f, 0.4f, 1f, 1f, fullLight, packedOverlay, false);
        }
        poseStack.popPose();
    }
}