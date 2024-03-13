package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import java.util.Random;

import immersive_aircraft.Main;
import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.client.render.entity.renderer.utils.ModelPartRenderHandler;
import immersive_aircraft.entity.AircraftEntity;
import io.github.bomb787.extended_aviation.entities.TestHelicopterEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TestHelicopterEntityRenderer<T extends TestHelicopterEntity> extends AircraftEntityRenderer<T> {
	
	private static final ResourceLocation id = Main.locate("quadrocopter");
	
	protected ResourceLocation getModelId() {
		return id;
	}
	
	private final Random random = new Random();
	
	private final ModelPartRenderHandler<T> model = new ModelPartRenderHandler<T>()
			.add(
					"engine",
					(entity, yaw, tickDelta, matrixStack) -> {
						double p = entity.enginePower.getSmooth() / 128.0;
						matrixStack.translate((random.nextDouble() - 0.5) * p, (random.nextDouble() - 0.5) * p, (random.nextDouble() - 0.5) * p);
					},
					(model, object, vertexConsumerProvider, entity, matrixStack, light, time, modelPartRenderer) -> {
						String engine = "engine_" + (entity.enginePower.getSmooth() > 0.01 ? entity.tickCount % 2 : 0);
						renderOptionalObject(engine, model, vertexConsumerProvider, entity, matrixStack, light, time);
					}
			);
	
	public TestHelicopterEntityRenderer(Context context) {
		super(context);
		this.shadowRadius = 0.8f;
	}
	
	@Override
	protected ModelPartRenderHandler<T> getModel(AircraftEntity entity) {
		return model;
	}
	
}