package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.client.render.entity.renderer.utils.ModelPartRenderHandler;
import immersive_aircraft.entity.AircraftEntity;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entity.RamielEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RamielEntityRenderer<T extends RamielEntity> extends AircraftEntityRenderer<T> {

    private static final ResourceLocation ID = ExtendedAviation.locate("ramiel");
    private final ModelPartRenderHandler<T> model = new ModelPartRenderHandler<T>()
            .add("at_field", (model, object, vertexConsumerProvider, entity, matrixStack, light, time, modelPartRenderer) -> {
                if(entity.isVehicle()) {
                    matrixStack.translate(0, -2, 0);
                    renderOptionalObject("at_field", model, vertexConsumerProvider, entity, matrixStack , light, time);
                }
            });

    @Override
    protected ResourceLocation getModelId() {
        return ID;
    }

    public RamielEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.25f;
    }

    @Override
    protected ModelPartRenderHandler<T> getModel(AircraftEntity entity) {
        return model;
    }

}