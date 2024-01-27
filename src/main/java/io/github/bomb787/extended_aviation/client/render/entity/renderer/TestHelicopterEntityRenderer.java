package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import java.util.Random;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import immersive_aircraft.Main;
import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.util.obj.Mesh;
import io.github.bomb787.extended_aviation.entities.TestHelicopterEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TestHelicopterEntityRenderer<T extends TestHelicopterEntity> extends AircraftEntityRenderer<T> {
	
	private static final ResourceLocation id = Main.locate("objects/quadrocopter.obj");

    private final ResourceLocation texture = Main.locate("textures/entity/quadrocopter.png");

    private final Random random = new Random();

    private static final float[][] PROPELLERS = {
            {1.25f, 0.5f + 0.5f / 16.0f, 0.65625f},
            {-1.25f, 0.5f + 0.5f / 16.0f, 0.65625f},
            {1.25f, 0.5f + 0.5f / 16.0f, -0.71875f},
            {-1.25f, 0.5f + 0.5f / 16.0f, -0.71875f}
    };

    private final Model model = new Model()
            .add(
                    new Object(id, "frame")
            ).add(
                    new Object(id, "engine")
                            .setAnimationConsumer(
                                    (entity, yaw, tickDelta, matrixStack) -> {
                                        double p = entity.enginePower.getSmooth() / 128.0;
                                        matrixStack.translate((random.nextDouble() - 0.5) * p, (random.nextDouble() - 0.5) * p, (random.nextDouble() - 0.5) * p);
                                    }
                            )
                            .setRenderConsumer(
                                    (vertexConsumerProvider, entity, matrixStack, light, tickDelta) -> {
                                        ResourceLocation identifier = getTextureLocation(entity);
                                        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityCutoutNoCull(identifier));
                                        Mesh mesh = getFaces(id, "engine_" + (entity.enginePower.getSmooth() > 0.01 ? entity.tickCount % 2 : 0));
                                        renderObject(mesh, matrixStack, vertexConsumer, light);
                                    }
                            )
            );

    {
        for (float[] propeller : PROPELLERS) {
            model.add(
                    new Object(id, "propeller")
                            .setAnimationConsumer(
                                    (entity, yaw, tickDelta, matrixStack) -> {
                                        matrixStack.translate(propeller[0], propeller[1], propeller[2]);
                                        matrixStack.mulPose(Vector3f.YP.rotationDegrees(entity.engineRotation.getSmooth(tickDelta) * propeller[0] * propeller[2] * 200.0f));
                                    }
                            )
                            .setRenderConsumer(
                                    (vertexConsumerProvider, entity, matrixStack, light, tickDelta) -> {
                                        ResourceLocation identifier = getTextureLocation(entity);
                                        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityCutoutNoCull(identifier));
                                        Mesh mesh = getFaces(id, "propeller");
                                        renderObject(mesh, matrixStack, vertexConsumer, light);
                                    }
                            )
            );
        }
    }

	public TestHelicopterEntityRenderer(Context context) {
		super(context);
		this.shadowRadius = 0.8f;
	}
	
	@Override
    public ResourceLocation getTextureLocation(@NotNull T entity) {
        return texture;
    }

    @Override
    protected Model getModel(AircraftEntity entity) {
        return model;
    }

    @Override
    protected Vector3f getPivot(AircraftEntity entity) {
        return new Vector3f(0.0f, 0.0f, 0.0f);
    }

}