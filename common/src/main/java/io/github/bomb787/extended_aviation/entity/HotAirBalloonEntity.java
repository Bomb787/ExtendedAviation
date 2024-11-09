package io.github.bomb787.extended_aviation.entity;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.AirplaneEntity;
import io.github.bomb787.extended_aviation.init.ItemInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class HotAirBalloonEntity extends AirplaneEntity {

    public HotAirBalloonEntity(EntityType<? extends AircraftEntity> entityType, Level world) {
        super(entityType, world, false);
    }

    @Override
    protected SoundEvent getEngineSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    protected SoundEvent getEngineStartSound() {
        return SoundEvents.FIRECHARGE_USE;
    }

    @Override
    public Item asItem() {
        return ItemInit.HOT_AIR_BALLOON.get();
    }

}