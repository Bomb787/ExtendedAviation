package io.github.bomb787.extended_aviation.entity;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.item.upgrade.VehicleStat;
import immersive_aircraft.resources.bbmodel.BBAnimationVariables;
import immersive_aircraft.util.InterpolatedFloat;
import io.github.bomb787.extended_aviation.init.StatInit;
import io.github.bomb787.extended_aviation.network.ComponentSyncS2CPacket;
import io.github.bomb787.extended_aviation.network.PacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
//TODO reload-safe velocity and engine targets
/**
 * Pretty much identical to {@link immersive_aircraft.entity.AirplaneEntity}, except for the addition of flap, gear, and speedbrake capabilities.
 */
public abstract class ExtendedAirplaneEntity extends AircraftEntity {

    protected boolean hasFlaps;
    protected float flapTarget;
    protected float currentFlap;
    protected boolean hasRetracts;
    protected float gearTarget;
    protected float currentGear;
    protected boolean hasSpeedbrakes;
    protected float speedbrakeTarget;
    protected float currentSpeedbrake;

    public final InterpolatedFloat flapProgress;
    public final InterpolatedFloat gearProgress;
    public final InterpolatedFloat speedbrakeProgress;

    public ExtendedAirplaneEntity(EntityType<? extends AircraftEntity> entityType, Level world, boolean canExplodeOnCrash) {
        super(entityType, world, canExplodeOnCrash);
        this.flapProgress = new InterpolatedFloat(getInputInterpolationSteps());
        this.gearProgress = new InterpolatedFloat(getInputInterpolationSteps());
        this.speedbrakeProgress = new InterpolatedFloat(getInputInterpolationSteps());
    }

    public void moveFlaps() {
        this.flapTarget = this.flapTarget == 1.0f ? 0.0f : 1.0f;
    }

    public void moveGear() {
        //Do not move your gear if you're on the ground. :)
        if(this.onGround()) {
            return;
        }
        this.gearTarget = this.gearTarget == 1.0f ? 0.0f : 1.0f;
    }

    public void moveSpeedbrakes() {
        this.speedbrakeTarget = this.speedbrakeTarget == 1.0f ? 0.0f : 1.0f;
    }

    @Override
    protected boolean useAirplaneControls() {
        return true;
    }

    @Override
    protected float getGravity() {
        Vector3f direction = getForwardDirection();
        float speed = (float) getDeltaMovement().length() * (1.0f - Math.abs(direction.y));
        return Math.max(0.0f, 1.0f - speed * 1.5f) * super.getGravity();
    }

    //Called when reducing engine power
    protected float getBrakeFactor() {
        return this.onGround() ? 0.98f : 1.0f;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide) {
            this.flapProgress.update(this.currentFlap);
            this.gearProgress.update(this.currentGear);
            this.speedbrakeProgress.update(this.currentSpeedbrake);
        } else {
            //Sync every 600 ticks/30 seconds
            if(this.tickCount % 600 == 0) {
                PacketHandler.sendToTrackingPlayers(new ComponentSyncS2CPacket(this.getId(), this.flapTarget, this.currentFlap, this.gearTarget, this.currentGear, this.speedbrakeTarget, this.currentSpeedbrake), this);
            }
            if(this.onGround() && (Math.abs(this.getX() - this.lastX) > 0.15 || Math.abs(this.getZ() - this.lastZ) > 0.15   ) && this.currentGear > 0.0f) {
                this.hurt(this.level().damageSources().fall(), 0.25f);
                this.getDeltaMovement().scale(0.1);
            }
        }
        if(this.hasFlaps && this.currentFlap != this.flapTarget) {
            this.currentFlap = Mth.clamp(this.currentFlap + ((this.flapTarget > this.currentFlap) ? 1f / this.getProperties().get(StatInit.FLAP_SPEED) : -1f / this.getProperties().get(StatInit.FLAP_SPEED)), 0.0f, 1.0f);
        }
        if(this.hasRetracts && this.currentGear != this.gearTarget) {
            this.currentGear = Mth.clamp(this.currentGear + ((this.gearTarget > this.currentGear) ? 1f / this.getProperties().get(StatInit.GEAR_SPEED) : -1f / this.getProperties().get(StatInit.GEAR_SPEED)), 0.0f, 1.0f);
        }
        if(this.hasSpeedbrakes && this.currentSpeedbrake != this.speedbrakeTarget) {
            this.currentSpeedbrake = Mth.clamp(this.currentSpeedbrake + ((this.speedbrakeTarget > this.currentSpeedbrake) ? 1f / this.getProperties().get(StatInit.SPEEDBRAKE_SPEED) : -1f / this.getProperties().get(StatInit.SPEEDBRAKE_SPEED)), 0.0f, 1.0f);
        }
    }

    @Override
    protected void convertPower(Vec3 direction) {
        Vec3 velocity = getDeltaMovement();
        double drag = Math.abs(direction.dot(velocity.normalize()))
                //Add flap drag
                - (this.getProperties().get(StatInit.FLAP_DRAG) * this.currentFlap)
                //Add gear drag
                - (this.getProperties().get(StatInit.GEAR_DRAG) * (1- this.currentGear))
                //Add speedbrake drag
                - (this.getProperties().get(StatInit.SPEEDBRAKE_DRAG) * this.currentSpeedbrake)
                //5x drag if on ground
                * (this.onGround() && this.currentGear > 0.0f ? 5 : 1);
        float friction = this.getProperties().get(VehicleStat.FRICTION);
        setDeltaMovement(velocity.normalize()
                .lerp(direction, getProperties().get(VehicleStat.LIFT) + (this.getProperties().get(StatInit.FLAP_LIFT) * this.currentFlap))
                .scale(velocity.length() * (drag * friction + (1.0 - friction))));
    }

    @Override
    protected void updateController() {
        if (!isVehicle()) {
            this.setEngineTarget(0.0f);
        }

        super.updateController();

        // engine control
        if (movementY != 0) {
            setEngineTarget(Math.max(0.0f, Math.min(1.0f, getEngineTarget() + 0.1f * movementY)));
            if (movementY < 0) {
                setDeltaMovement(getDeltaMovement().scale(getBrakeFactor()));
            }
        }

        // speed
        float thrust = this.getEnginePower() * this.getEnginePower() * getProperties().get(VehicleStat.ENGINE_SPEED);
        if (onGround() && this.currentGear > 0.0f) {
            thrust*=0.01f;
        }

        // accelerate
        setDeltaMovement(getDeltaMovement().add(toVec3d(getForwardDirection().mul(thrust))));
    }

    @Override
    protected void updateVelocity() {
        // get direction
        Vector3f direction = getForwardDirection();

        // glide
        float diff = (float) (lastY - getY());
        if (lastY != 0.0 && getProperties().get(VehicleStat.GLIDE_FACTOR) > 0 && diff != 0.0) {
            setDeltaMovement(getDeltaMovement().add(toVec3d(direction).scale(diff * getProperties().get(VehicleStat.GLIDE_FACTOR) * (1.0f - Math.abs(direction.y)))));
        }
        lastY = (float) getY();

        // convert power
        convertPower(toVec3d(direction));

        // friction
        applyFriction();

        if (onGround()) {
            // Landing
            float groundPitch = this.currentGear == 0.0f ? getProperties().get(VehicleStat.GROUND_PITCH) : 0;
            setXRot((getXRot() + groundPitch) * 0.9f - groundPitch);
        } else {
            // Wind
            Vector3f effect = getWindEffect();
            setXRot(getXRot() + effect.x);
            setYRot(getYRot() + effect.z);

            float offsetStrength = 0.005f;
            setDeltaMovement(getDeltaMovement().add(effect.x * offsetStrength, 0.0f, effect.z * offsetStrength));
        }
    }

    //Syncs the client side entity with the server.
    public void syncClient(float flapTarget, float currentFlap, float gearTarget, float currentGear, float speedbrakeTarget, float currentSpeedbrake) {
        this.flapTarget = flapTarget;
        this.currentFlap = currentFlap;
        this.gearTarget = gearTarget;
        this.currentGear = currentGear;
        this.speedbrakeTarget = speedbrakeTarget;
        this.currentSpeedbrake = currentSpeedbrake;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putDouble("velX", this.getX() - this.lastX);
        nbt.putDouble("velY", this.getY() - this.lastY);
        nbt.putDouble("velZ", this.getZ() - this.lastZ);
        nbt.putFloat("engineTarget", this.getEngineTarget());
        nbt.putFloat("flapTarget", this.flapTarget);
        nbt.putFloat("currentFlap", this.currentFlap);
        nbt.putFloat("gearTarget", this.gearTarget);
        nbt.putFloat("currentGear",  this.currentGear);
        nbt.putFloat("speedbrakeTarget", this.speedbrakeTarget);
        nbt.putFloat("currentSpeedbrake", this.currentSpeedbrake);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        //TODO test if this works properly
        this.setDeltaMovement(nbt.getDouble("velX"), nbt.getDouble("velY"), nbt.getDouble("velZ"));
        this.setEngineTarget(nbt.getFloat("engineTarget"));
        this.flapTarget = nbt.getFloat("flapTarget");
        this.currentFlap = nbt.getFloat("currentFlap");
        this.gearTarget = nbt.getFloat("gearTarget");
        this.currentGear = nbt.getFloat("currentGear");
        this.speedbrakeTarget = nbt.getFloat("speedbrakeTarget");
        this.currentSpeedbrake = nbt.getFloat("currentSpeedbrake");
    }

    @Override
    public void setAnimationVariables(float tickDelta) {
        super.setAnimationVariables(tickDelta);
        BBAnimationVariables.set("flap_progress", flapProgress.getSmooth(tickDelta));
        BBAnimationVariables.set("gear_progress", gearProgress.getSmooth(tickDelta));
        BBAnimationVariables.set("speedbrake_progress", speedbrakeProgress.getSmooth(tickDelta));
    }

    public boolean hasFlaps() {
        return this.hasFlaps;
    }

    public boolean hasRetracts() {
        return this.hasRetracts;
    }

    public boolean hasSpeedbrakes() {
        return this.hasSpeedbrakes;
    }

}