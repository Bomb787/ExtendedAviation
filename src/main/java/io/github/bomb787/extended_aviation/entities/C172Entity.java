package io.github.bomb787.extended_aviation.entities;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.misc.AircraftProperties;
import immersive_aircraft.entity.misc.VehicleInventoryDescription;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class C172Entity extends CustomAirplaneEntity {
	
	/*protected final AircraftProperties properties = new AircraftProperties(this)
			.setYawSpeed(5.0f)
			.setPitchSpeed(4.0f)
			.setEngineSpeed(0.05f)
			.setGlideFactor(0.05f)
			.setDriftDrag(0.01f)
			.setLift(0.15f)
			.setRollFactor(30.0f)
			.setGroundPitch(5f)
			.setWindSensitivity(0.025f)
			.setMass(5f);
	
	private static final VehicleInventoryDescription inventoryDescription = new VehicleInventoryDescription()
			.addSlot(VehicleInventoryDescription.SlotType.BOILER, 8 + 9, 8 + 22)
			.addSlot(VehicleInventoryDescription.SlotType.BOOSTER, 8 + 9, 8 + 48)
			.addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 6, 8 + 6)
			.addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 28, 8 + 6)
			.addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 6, 8 + 6 + 22)
			.addSlot(VehicleInventoryDescription.SlotType.UPGRADE, 8 + 18 * 2 + 28, 8 + 6 + 22)
			.addSlots(VehicleInventoryDescription.SlotType.INVENTORY, 8 + 18 * 5, 8, 3, 3)
			.build();*/
	
	public C172Entity(EntityType<? extends AircraftEntity> entityType, Level world) {
		super(entityType, world);
	}
	/*
	@Override
	public VehicleInventoryDescription getInventoryDescription() {
		return inventoryDescription;
	}
	
	@Override
	public AircraftProperties getProperties() {
		return properties;
	}*/

}