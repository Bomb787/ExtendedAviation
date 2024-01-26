package io.github.bomb787.extended_aviation.entities;

import java.util.List;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.misc.AircraftProperties;
import immersive_aircraft.entity.misc.Trail;
import immersive_aircraft.entity.misc.VehicleInventoryDescription;
import io.github.bomb787.extended_aviation.init.ItemInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TestPlaneEntity extends CustomAirplaneEntity {
	
	protected final AircraftProperties properties = new AircraftProperties(this)
            .setYawSpeed(5.0f)
            .setPitchSpeed(4.0f)
            .setEngineSpeed(0.05f)
            .setGlideFactor(0.05f)
            .setDriftDrag(0.01f)
            .setLift(0.15f)
            .setRollFactor(45.0f)
            .setGroundPitch(4.0f)
            .setWindSensitivity(0.025f)
            .setMass(1.0f);
	
	@Override
    public AircraftProperties getProperties() {
        return properties;
    }
	
	 private static final VehicleInventoryDescription inventoryDescription = new VehicleInventoryDescription()
			 .addSlot(VehicleInventoryDescription.SlotType.BOILER, 8 + 9, 8 + 22)
	         .addSlot(VehicleInventoryDescription.SlotType.BOOSTER, 8 + 9, 8 + 48)
	         .addSlot(VehicleInventoryDescription.SlotType.WEAPON, 8 + 18 * 2 + 6, 8 + 6)
	         .addSlot(VehicleInventoryDescription.SlotType.BANNER, 8 + 18 * 2 + 28, 8 + 6)
	         .addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 6, 8 + 6 + 22)
	         .addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 28, 8 + 6 + 22)
	         .addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 6, 8 + 6 + 22 * 2)
	         .addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 28, 8 + 6 + 22 * 2)
	         .addSlots(VehicleInventoryDescription.SlotType.INVENTORY, 8 + 18 * 5, 8, 4, 4)
	         .build();

	@Override
	public VehicleInventoryDescription getInventoryDescription() {
		return inventoryDescription;
	}

	public TestPlaneEntity(EntityType<? extends AircraftEntity> entityType, Level world) {
		super(entityType, world);
	}
	
	@Override
    protected float getBaseFuelConsumption() {
        return 1.25f;
    }

    final List<List<Vec3>> PASSENGER_POSITIONS = List.of(List.of(new Vec3(0.0f, 0.05f, -0.6f)));

    protected List<List<Vec3>> getPassengerPositions() {
        return PASSENGER_POSITIONS;
    }

    private final List<Trail> trails = List.of(new Trail(40), new Trail(40));

    public List<Trail> getTrails() {
        return trails;
    }

    private void trail(Matrix4f transform, int index, float x, float y, float z) {
        Vector4f p0 = transformPosition(transform, x, y - 0.15f, z);
        Vector4f p1 = transformPosition(transform, x, y + 0.15f, z);

        float trailStrength = Math.max(0.0f, Math.min(1.0f, (float) (Math.sqrt(getDeltaMovement().length()) * (0.5f + (pressingInterpolatedX.getSmooth() * x) * 0.025f) - 0.25f)));
        trails.get(index).add(p0, p1, trailStrength);
    }

    @Override
    public Item asItem() {
        return ItemInit.TEST.get();
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) {
            if (isWithinParticleRange()) {
                Matrix4f transform = getVehicleTransform();
                Matrix3f normalTransform = getVehicleNormalTransform();

                // Trails
                trail(transform, 0, -3.75f, 0.25f, 0.6f);
                trail(transform, 1, 3.75f, 0.25f, 0.6f);

                // Smoke
                float power = getEnginePower();
                if (power > 0.05) {
                    Vector4f p = transformPosition(transform, 0.325f * (tickCount % 4 == 0 ? -1.0f : 1.0f), 0.5f, 0.8f);
                    Vector3f vel = transformVector(normalTransform, 0.2f * (tickCount % 4 == 0 ? -1.0f : 1.0f), 0.0f, 0.0f);
                    Vec3 velocity = getDeltaMovement();
                    level.addParticle(ParticleTypes.SMOKE, p.x(), p.y(), p.z(), vel.x() + velocity.x, vel.y() + velocity.y, vel.z() + velocity.z);
                }
            } else {
                trails.get(0).add(ZERO_VEC4, ZERO_VEC4, 0.0f);
                trails.get(1).add(ZERO_VEC4, ZERO_VEC4, 0.0f);
            }
        }
    }

}