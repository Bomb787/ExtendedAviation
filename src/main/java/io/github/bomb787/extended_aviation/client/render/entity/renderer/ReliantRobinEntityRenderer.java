package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.client.render.entity.renderer.utils.ModelPartRenderHandler;
import immersive_aircraft.entity.AircraftEntity;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entities.ReliantRobinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ReliantRobinEntityRenderer<T extends ReliantRobinEntity> extends AircraftEntityRenderer<T> {
	
	private static final ResourceLocation id = new ResourceLocation(ExtendedAviation.MOD_ID, "reliant_robin");
	
	protected ResourceLocation getModelId() {
		return id;
	}
	
	private final ModelPartRenderHandler<T> model = new ModelPartRenderHandler<T>();
	
	public ReliantRobinEntityRenderer(Context context) {
		super(context);
		this.shadowRadius = 1f;
	}
	
	@Override
	protected ModelPartRenderHandler<T> getModel(AircraftEntity entity) {
		return model;
	}

}