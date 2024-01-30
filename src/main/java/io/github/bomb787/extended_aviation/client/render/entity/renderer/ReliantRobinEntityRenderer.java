package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import org.jetbrains.annotations.NotNull;

import com.mojang.math.Vector3f;

import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.entity.AircraftEntity;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entities.ReliantRobinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ReliantRobinEntityRenderer<T extends ReliantRobinEntity> extends AircraftEntityRenderer<T> {
	
	private static final ResourceLocation id = new ResourceLocation(ExtendedAviation.MOD_ID, "objects/reliant_robin.obj");
	
	private final ResourceLocation texture;
	
	private final Model model = new Model()
			.add(
					new Object(id, "frame")
			)
			.add(
					new Object(id, "front_wheel").setAnimationConsumer(
							(entity, yaw, tickDelta, matrixStack) -> {
								matrixStack.translate(0, 0, 1f);
								matrixStack.mulPose(Vector3f.YP.rotationDegrees(entity.pressingInterpolatedX.getSmooth(tickDelta) * 35.0f));
							}
					)
			)
			.add(
					new Object(id, "steering_wheel").setAnimationConsumer(
							(entity, yaw, tickDelta, matrixStack) -> {
								matrixStack.translate(-0.6f, 1.25f, 0f);
								matrixStack.mulPose(Vector3f.XP.rotationDegrees(10f));
								matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-entity.pressingInterpolatedX.getSmooth(tickDelta) * 30.0f));
							}
					)
			)
			.add(
					new Object(id, "rotor").setAnimationConsumer(
							(entity, yaw, tickDelta, matrixStack) -> {
								matrixStack.translate(0, 0, -0.75f);
								matrixStack.mulPose(Vector3f.YP.rotationDegrees(-entity.engineRotation.getSmooth(tickDelta) * 100f));
							}
					)
			);
	
	public ReliantRobinEntityRenderer(Context context) {
		super(context);
		this.shadowRadius = 1f;
		texture = new ResourceLocation(ExtendedAviation.MOD_ID, "textures/entity/reliant_robin.png");
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
		return Vector3f.ZERO;
	}

}