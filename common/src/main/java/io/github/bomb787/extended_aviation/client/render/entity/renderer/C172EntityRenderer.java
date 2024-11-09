package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.client.render.entity.renderer.utils.ModelPartRenderHandler;
import immersive_aircraft.entity.AircraftEntity;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entity.C172Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class C172EntityRenderer extends AircraftEntityRenderer<C172Entity> {

    private static final ResourceLocation ID = ExtendedAviation.locate("c172");
    private final ModelPartRenderHandler<C172Entity> model = new ModelPartRenderHandler<>();

    @Override
    protected ResourceLocation getModelId() {
        return ID;
    }

    public C172EntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected ModelPartRenderHandler<C172Entity> getModel(AircraftEntity entity) {
        return model;
    }

}