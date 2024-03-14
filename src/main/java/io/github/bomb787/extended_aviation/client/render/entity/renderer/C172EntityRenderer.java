package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import com.mojang.math.Vector3f;

import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.client.render.entity.renderer.utils.ModelPartRenderHandler;
import immersive_aircraft.entity.AircraftEntity;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entities.C172Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class C172EntityRenderer<T extends C172Entity> extends AircraftEntityRenderer<T> {
	
	private static final ResourceLocation id = new ResourceLocation(ExtendedAviation.MOD_ID, "c172");
	
	protected ResourceLocation getModelId() {
		return id;
	}
	
	private final ModelPartRenderHandler<T> model = new ModelPartRenderHandler<T>();
	
	public C172EntityRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.8f;
	}
	
	@Override
	protected ModelPartRenderHandler<T> getModel(AircraftEntity entity) {
		return model;
	}
	
	@Override
	protected Vector3f getPivot(AircraftEntity entity) {
		return new Vector3f(0.0f, 0.4f, 0.05f);
	}

}