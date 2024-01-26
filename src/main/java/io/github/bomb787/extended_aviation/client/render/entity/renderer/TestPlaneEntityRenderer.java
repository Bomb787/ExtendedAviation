package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;

import immersive_aircraft.Main;
import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.misc.VehicleInventoryDescription;
import immersive_aircraft.util.Utils;
import immersive_aircraft.util.obj.Mesh;
import io.github.bomb787.extended_aviation.entities.TestPlaneEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;

public class TestPlaneEntityRenderer<T extends TestPlaneEntity> extends AircraftEntityRenderer<T> {
	private static final ResourceLocation id = Main.locate("objects/biplane.obj");

    private final ResourceLocation texture;

    private final Model model = new Model()
            .add(
                    new Object(id, "frame")
            )
            .add(
                    new Object(id, "banners").setRenderConsumer(
                            (vertexConsumerProvider, entity, matrixStack, light, tickDelta) -> {
                                List<ItemStack> slots = entity.getSlots(VehicleInventoryDescription.SlotType.BANNER);
                                int i = 0;
                                for (ItemStack slot : slots) {
                                    if (!slot.isEmpty() && slot.getItem() instanceof BannerItem) {
                                        List<Pair<Holder<BannerPattern>, DyeColor>> patterns = Utils.parseBannerItem(slot);
                                        Mesh mesh = getFaces(id, "banner_" + (i++));
                                        renderBanner(matrixStack, vertexConsumerProvider, light, mesh, true, patterns);
                                    }
                                }
                            }
                    )
            )
            .add(
                    new Object(id, "propeller").setAnimationConsumer(
                            (entity, yaw, tickDelta, matrixStack) -> {
                                matrixStack.translate(0.0f, 0.3125f, 0.0f);
                                matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) (entity.engineRotation.getSmooth(tickDelta) * 100.0)));
                                matrixStack.translate(0.0f, -0.3125f, 0.0f);
                            }
                    )
            )
            .add(
                    new Object(id, "elevator").setAnimationConsumer(
                            (entity, yaw, tickDelta, matrixStack) -> {
                                matrixStack.translate(0.0f, 0.0625f, -2.5f);
                                matrixStack.mulPose(Vector3f.XP.rotationDegrees(-entity.pressingInterpolatedZ.getSmooth(tickDelta) * 20.0f));
                                matrixStack.translate(0.0f, -0.0625f, 2.5f);
                            }
                    )
            )
            .add(
                    new Object(id, "rudder").setAnimationConsumer(
                            (entity, yaw, tickDelta, matrixStack) -> {
                                matrixStack.translate(0.0f, 0.0625f, -2.5f);
                                matrixStack.mulPose(Vector3f.YP.rotationDegrees(entity.pressingInterpolatedX.getSmooth(tickDelta) * 18.0f));
                                matrixStack.translate(0.0f, -0.0625f, 2.5f);
                            }
                    )
            );

    public TestPlaneEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.8f;
        texture = Main.locate("textures/entity/biplane.png");
    }

    @Override
    public void render(@NotNull T entity, float yaw, float tickDelta, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource vertexConsumerProvider, int i) {
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull T aircraft) {
        return texture;
    }

    @Override
    protected Model getModel(AircraftEntity entity) {
        return model;
    }

    @Override
    protected Vector3f getPivot(AircraftEntity entity) {
        return new Vector3f(0.0f, 0.4f, 0.05f);
    }
}