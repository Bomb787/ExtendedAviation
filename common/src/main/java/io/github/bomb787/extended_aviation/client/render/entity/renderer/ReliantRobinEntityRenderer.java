package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.client.render.entity.renderer.utils.ModelPartRenderHandler;
import immersive_aircraft.entity.AircraftEntity;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entity.ReliantRobinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReliantRobinEntityRenderer extends AircraftEntityRenderer<ReliantRobinEntity> {

    private static final ResourceLocation ID = ExtendedAviation.locate("reliant_robin");
    private final ModelPartRenderHandler<ReliantRobinEntity> model = new ModelPartRenderHandler<>();

    @Override
    protected ResourceLocation getModelId() {
        return ID;
    }

    public ReliantRobinEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 1.0f;
    }

    @Override
    protected ModelPartRenderHandler<ReliantRobinEntity> getModel(AircraftEntity entity) {
        return model;
    }

}