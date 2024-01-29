package io.github.bomb787.extended_aviation.entities;

import java.util.List;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.Rotorcraft;
import immersive_aircraft.entity.misc.AircraftProperties;
import immersive_aircraft.entity.misc.VehicleInventoryDescription;
import io.github.bomb787.extended_aviation.init.ItemInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ReliantRobinEntity extends Rotorcraft {
	
	private AircraftProperties properties = new AircraftProperties(this)
            .setYawSpeed(3.5f)
            .setPitchSpeed(1.5f)
            .setEngineSpeed(0.075f)
            .setVerticalSpeed(0.0325f)
            .setGlideFactor(0.0f)
            .setDriftDrag(0.005f)
            .setLift(0.1f)
            .setRollFactor(35.0f)
            .setWindSensitivity(0.0525f)
            .setMass(5f);
	
	private static VehicleInventoryDescription inventoryDescription = new VehicleInventoryDescription()
            .addSlot(VehicleInventoryDescription.SlotType.BOILER, 8 + 9, 8 + 14)
            .addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 6, 8 + 6)
            .addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 6 + 22, 8 + 6)
            .addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 6, 8 + 6 + 22)
            .addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 6 + 22, 8 + 6 + 22)
            .addSlots(VehicleInventoryDescription.SlotType.INVENTORY, 8 + 18 * 5, 8, 4, 3)
            .build();

	public ReliantRobinEntity(EntityType<? extends AircraftEntity> entityType, Level world) {
		super(entityType, world, true);
	}
	
	@Override
	protected float getBaseFuelConsumption() {
		return 0.75f;
	}
	
	@Override
    public VehicleInventoryDescription getInventoryDescription() {
        return inventoryDescription;
    }
	
	@Override
    public AircraftProperties getProperties() {
        return properties;
    }

    @Override
    protected float getGroundVelocityDecay() {
        return 0.95f;
    }

    @Override
    protected float getHorizontalVelocityDelay() {
        return 0.9f;
    }

    @Override
    protected float getVerticalVelocityDelay() {
        return 0.8f;
    }

    @Override
    protected float getStabilizer() {
        return 0.1f;
    }
    
    @Override
    public Item asItem() {
        return ItemInit.RELIANT_ROBIN.get();
    }

    final List<List<Vec3>> PASSENGER_POSITIONS = List.of(
            List.of(
                    new Vec3(-0.6f, 0.5f, -0.75f)
            ),
            List.of(
                    new Vec3(0.6f, 0.5f, -0.75f),
                    new Vec3(0.-6f, 0.5f, -2f),
                    new Vec3(0.6f, 0.5f, -2f)
            )
    );

    protected List<List<Vec3>> getPassengerPositions() {
        return PASSENGER_POSITIONS;
    }

    @Override
    protected float getGravity() {
        return wasTouchingWater ? 0.04f : (1.0f - getEnginePower()) * super.getGravity();
    }
    
    @Override
    protected void updateController() {
        super.updateController();

        setEngineTarget(1.0f);

        // up and down
        setDeltaMovement(getDeltaMovement().add(0.0f, getEnginePower() * properties.getVerticalSpeed() * pressingInterpolatedY.getSmooth(), 0.0f));

        // get pointing direction
        Vec3 direction = getForwardDirection();

        // accelerate
        float thrust = (float) (Math.pow(getEnginePower(), 5.0) * properties.getEngineSpeed()) * pressingInterpolatedZ.getSmooth();
        setDeltaMovement(getDeltaMovement().add(direction.scale(thrust)));
    }

}