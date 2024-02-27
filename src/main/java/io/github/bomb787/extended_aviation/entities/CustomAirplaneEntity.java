package io.github.bomb787.extended_aviation.entities;

import com.mojang.math.Vector3f;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.EngineAircraft;
import immersive_aircraft.item.upgrade.AircraftStat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class CustomAirplaneEntity extends EngineAircraft {
	
	//This will be replaced by lastY (hopefully) once the next update comes out
	protected double prevY;
	
	public CustomAirplaneEntity(EntityType<? extends AircraftEntity> entityType, Level world) {
		super(entityType, world, true);
	}
	
	@Override
	protected boolean useAirplaneControls() {
		return true;
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
	protected void convertPower(Vec3 direction) {
		Vec3 velocity = getDeltaMovement();
		double drag = Math.abs(direction.dot(velocity.normalize()));
		setDeltaMovement(velocity.normalize()
				.lerp(direction, getProperties().get(AircraftStat.LIFT))
				.scale(velocity.length() * (drag * getProperties().get(AircraftStat.FRICTION) + (1.0 - getProperties().get(AircraftStat.FRICTION)))));
	}
	
	@Override
	protected void updateVelocity() {
		float decay = 1.0f - 0.015f * getTotalUpgrade(AircraftStat.FRICTION);
		float gravity = getGravity();
		if (wasTouchingWater) {
			gravity *= 0.25f;
			decay = 0.9f;
		} else if (onGround) {
			if (isVehicle()) {
				decay = falloffGroundVelocityDecay(getProperties().get(AircraftStat.GROUND_FRICTION));
			} else {
				decay = 0.75f;
			}
		}
		
		// get direction
		Vec3 direction = getForwardDirection();
		
		// glide
		double diff = prevY - getY();
		if (prevY != 0.0 && getProperties().get(AircraftStat.GLIDE_FACTOR) > 0 && diff != 0.0) {
			setDeltaMovement(getDeltaMovement().add(direction.scale(diff * getProperties().get(AircraftStat.GLIDE_FACTOR) * (1.0f - Math.abs(direction.y())))));
		}
		prevY = getY();
		
		// convert power
		convertPower(direction);
		
		// friction
		Vec3 velocity = getDeltaMovement();
		float hd = getProperties().get(AircraftStat.HORIZONTAL_DECAY);
		float vd = getProperties().get(AircraftStat.VERTICAL_DECAY);
		setDeltaMovement(velocity.x * decay * hd, velocity.y * decay * vd + gravity, velocity.z * decay * hd);
		float rf = decay * getProperties().get(AircraftStat.ROTATION_DECAY);
		pressingInterpolatedX.decay(0.0f, 1.0f - rf);
		pressingInterpolatedZ.decay(0.0f, 1.0f - rf);
		
		// wind
		if (!onGround) {
			Vector3f effect = getWindEffect();
			setXRot(getXRot() + effect.x());
			setYRot(getYRot() + effect.z());
			
			float offsetStrength = 0.005f;
			setDeltaMovement(getDeltaMovement().add(effect.x() * offsetStrength, 0.0f, effect.z() * offsetStrength));
		}
		//^super
		
		// landing
        if (onGround) {
            setXRot((getXRot() + getProperties().get(AircraftStat.GROUND_PITCH)) * 0.9f - getProperties().get(AircraftStat.GROUND_PITCH));
        }
	}
	
	@Override
	public void chill() {
		this.prevY = 0.0;
	}
	
	@Override
	protected void updateController() {
		if (!isVehicle()) {
			this.setEngineTarget(0f);
		}
		
		{ //super
			// left-right
			setYRot(getYRot() - getProperties().get(AircraftStat.YAW_SPEED) * pressingInterpolatedX.getSmooth());
			
			// forwards-backwards
			if (!onGround) {
				setXRot(getXRot() + getProperties().get(AircraftStat.PITCH_SPEED) * pressingInterpolatedZ.getSmooth());
			}
			setXRot(getXRot() * (1.0f - getStabilizer()));
		}
		
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
		float thrust = (float) (Math.pow(getEnginePower(), 2.0) * getProperties().get(AircraftStat.ENGINE_SPEED));
        if (onGround && getEngineTarget() < 1.0) {
            thrust = getProperties().get(AircraftStat.PUSH_SPEED) / (1.0f + (float) getDeltaMovement().length() * 5.0f) * pressingInterpolatedZ.getSmooth() * (1.0f - getEnginePower());
        }
		
		// accelerate
		setDeltaMovement(getDeltaMovement().add(direction.scale(thrust)));
	}

}