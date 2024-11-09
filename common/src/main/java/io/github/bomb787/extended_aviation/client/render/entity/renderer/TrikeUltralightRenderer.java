package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.client.render.entity.renderer.utils.ModelPartRenderHandler;
import immersive_aircraft.entity.AircraftEntity;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entity.C172Entity;
import io.github.bomb787.extended_aviation.entity.TrikeUltralightEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TrikeUltralightRenderer extends AircraftEntityRenderer<TrikeUltralightEntity> {

    private static final ResourceLocation ID = ExtendedAviation.locate("trike_ultralight");
    private final ModelPartRenderHandler<TrikeUltralightEntity> model = new ModelPartRenderHandler<TrikeUltralightEntity>()
            .add("dyed_body", (model, object, vertexConsumerProvider, entity, matrixStack, light, time, modelPartRenderer) ->
                    renderDyed(model, object, vertexConsumerProvider, entity, matrixStack, light, time, false, false));

    @Override
    protected ResourceLocation getModelId() {
        return ID;
    }

    public TrikeUltralightRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected ModelPartRenderHandler<TrikeUltralightEntity> getModel(AircraftEntity entity) {
        return model;
    }

}