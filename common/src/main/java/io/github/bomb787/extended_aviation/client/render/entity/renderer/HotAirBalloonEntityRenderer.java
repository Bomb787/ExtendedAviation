package io.github.bomb787.extended_aviation.client.render.entity.renderer;

import immersive_aircraft.client.render.entity.renderer.AircraftEntityRenderer;
import immersive_aircraft.client.render.entity.renderer.utils.ModelPartRenderHandler;
import immersive_aircraft.entity.AircraftEntity;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entity.HotAirBalloonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class HotAirBalloonEntityRenderer extends AircraftEntityRenderer<HotAirBalloonEntity> {

    private static final ResourceLocation ID = ExtendedAviation.locate("hot_air_balloon");
    private final ModelPartRenderHandler<HotAirBalloonEntity> model = new ModelPartRenderHandler<>();

    @Override
    protected ResourceLocation getModelId() {
        return ID;
    }

    public HotAirBalloonEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5f;
    }

    @Override
    protected ModelPartRenderHandler<HotAirBalloonEntity> getModel(AircraftEntity entity) {
        return model;
    }

}