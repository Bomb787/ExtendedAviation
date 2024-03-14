package io.github.bomb787.extended_aviation.entities;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.AirplaneEntity;
import io.github.bomb787.extended_aviation.init.ItemInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class C172Entity extends AirplaneEntity {
	
	public C172Entity(EntityType<? extends AircraftEntity> entityType, Level world) {
		super(entityType, world, true);
	}
	
	@Override
	protected float getBrakeFactor() {
		if(this.isOnGround()) {
			System.out.println(this.getDeltaMovement().lengthSqr());
			return this.getDeltaMovement().length() < 1f ? 0.1f * (float) this.getDeltaMovement().lengthSqr() + 0.9f: 1f;
		} else {
			return 1f;
		}
	}
	
	@Override
	public Item asItem() {
		return ItemInit.C172.get();
	}

}