package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.client.render.entity.renderer.utils.ModelPartRenderHandler;
import immersive_aircraft.entity.AircraftEntity;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entity.VillagercopterEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class VillagercopterEntityRenderer extends AircraftEntityRenderer<VillagercopterEntity> {

    private static final ResourceLocation ID = ExtendedAviation.locate("villagercopter");
    private final ModelPartRenderHandler<VillagercopterEntity> model = new ModelPartRenderHandler<>();

    @Override
    protected ResourceLocation getModelId() {
        return ID;
    }

    public VillagercopterEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected ModelPartRenderHandler<VillagercopterEntity> getModel(AircraftEntity entity) {
        return model;
    }

}