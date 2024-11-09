package io.github.bomb787.extended_aviation.entity;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.AirplaneEntity;
import io.github.bomb787.extended_aviation.init.ItemInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class C172Entity extends ExtendedAirplaneEntity {

    public C172Entity(EntityType<? extends AircraftEntity> entityType, Level world) {
        super(entityType, world, true);
        this.hasFlaps = true;
    }

    /*@Override
    public Item asItem() {
        return ItemInit.C172.get();
    }*/

}