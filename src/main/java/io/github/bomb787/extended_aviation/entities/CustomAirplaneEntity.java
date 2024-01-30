package io.github.bomb787.extended_aviation.entities;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.EngineAircraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class CustomAirplaneEntity extends EngineAircraft {
	
	public CustomAirplaneEntity(EntityType<? extends AircraftEntity> entityType, Level world) {
		super(entityType, world, true);
	}
	
	@Override
	protected boolean useAirplaneControls() {
		return true;
	}
	
	@Override
	protected float getGroundVelocityDecay() {
		return falloffGroundVelocityDecay(0.9f);
	}
	
	@Override
	protected float getGravity() {
		Vec3 direction = getForwardDirection();
		float speed = (float) ((float) getDeltaMovement().length() * (1.0f - Math.abs(direction.y())));
		return Math.max(0.0f, 1.0f - speed * 1.5f) * super.getGravity();
	}
	
	protected float getBrakeFactor() {
		return 0.95f;
	}
	
	@Override
	protected void updateController() {
		if (!isVehicle()) {
			return;
		}
		
		super.updateController();
		
		// engine control
		if (movementY != 0) {
			setEngineTarget(Math.max(0.0f, Math.min(1.0f, getEngineTarget() + 0.1f * movementY)));
			if (movementY < 0) {
				setDeltaMovement(getDeltaMovement().scale(getBrakeFactor()));
			}
		}
		
		// get direction
		Vec3 direction = getForwardDirection();
		
		// speed
		float thrust = (float) (Math.pow(getEnginePower(), 2.0) * this.getProperties().getEngineSpeed());
		
		// accelerate
		setDeltaMovement(getDeltaMovement().add(direction.scale(thrust)));
	}

}