package io.github.bomb787.extended_aviation.entity;

import immersive_aircraft.Sounds;
import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.AirplaneEntity;
import io.github.bomb787.extended_aviation.init.ItemInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class TrikeUltralightEntity extends AirplaneEntity {

    public TrikeUltralightEntity(EntityType<? extends AircraftEntity> entityType, Level world) {
        super(entityType, world, true);
    }

    @Override
    public Item asItem() {
        return ItemInit.TRIKE_ULTRALIGHT.get();
    }

    @Override
    protected float getEngineReactionSpeed() {
        return 1.0f;
    }

    @Override
    protected SoundEvent getEngineSound() {
        return Sounds.PROPELLER_TINY.get();
    }

    @Override
    public double getZoom() {
        return 4.0;
    }

    @Override
    public int getDefaultDyeColor() {
        return 0x0018A5;
    }
}
