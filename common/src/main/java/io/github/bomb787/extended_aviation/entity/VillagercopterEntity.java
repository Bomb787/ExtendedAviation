package io.github.bomb787.extended_aviation.entity;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.Rotorcraft;
import immersive_aircraft.item.upgrade.VehicleStat;
import io.github.bomb787.extended_aviation.init.ItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class VillagercopterEntity extends Rotorcraft {

    public VillagercopterEntity(EntityType<? extends AircraftEntity> entityType, Level world) {
        super(entityType, world, true);
        this.adaptPlayerRotation = false;
    }

    @Override
    protected float getInputInterpolationSteps() {
        return 5;
    }

    @Override
    public Item asItem() {
        return ItemInit.VILLAGERCOPTER.get();
    }

    @Override
    protected float getGravity() {
        return wasTouchingWater ? 0.04f : (1.0f - getEnginePower()) * super.getGravity();
    }

    @Override
    protected void updateController() {
        if (canTurnOnEngine(getControllingPassenger())) {
            setEngineTarget(1.0f);
        }

        // forwards-backwards
        if (!onGround()) {
            setXRot(getXRot() + getProperties().get(VehicleStat.PITCH_SPEED) * pressingInterpolatedZ.getSmooth());
        }
        setXRot(getXRot() * (1.0f - getProperties().getAdditive(VehicleStat.STABILIZER)));

        // up and down
        setDeltaMovement(getDeltaMovement().add(0.0f, getEnginePower() * getProperties().get(VehicleStat.VERTICAL_SPEED) * pressingInterpolatedY.getSmooth(), 0.0f));

        // Rotate to pilot's head rotation
        Entity pilot = getControllingPassenger();
        if (pilot != null) {
            float diff = pilot.getYHeadRot() - getYRot();
            if (diff > 180.0f) {
                diff -= 360.0f;
            } else if (diff < -180.0f) {
                diff += 360.0f;
            }
            diff = diff * getProperties().get(VehicleStat.YAW_SPEED);
            if (Math.abs(diff) < 60f) {
                setYRot(getYRot() + diff);
            }
        }

        float thrust = (float) (Math.pow(getEnginePower(), 5.0) * getProperties().get(VehicleStat.ENGINE_SPEED));

        // left and right
        Vector3f direction = getRightDirection().mul(thrust * pressingInterpolatedX.getSmooth());
        setDeltaMovement(getDeltaMovement().add(direction.x, direction.y, direction.z));

        // forward and backward
        direction = getForwardDirection().mul(thrust * pressingInterpolatedZ.getSmooth());
        setDeltaMovement(getDeltaMovement().add(direction.x, direction.y, direction.z));
    }

    @Override
    public double getZoom() {
        return 4.0;
    }

}