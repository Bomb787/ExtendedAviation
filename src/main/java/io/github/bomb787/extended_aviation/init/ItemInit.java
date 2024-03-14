package io.github.bomb787.extended_aviation.init;

import immersive_aircraft.ItemGroups;
import immersive_aircraft.item.AircraftItem;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entities.C172Entity;
import io.github.bomb787.extended_aviation.entities.ReliantRobinEntity;
import io.github.bomb787.extended_aviation.entities.TestHelicopterEntity;
import io.github.bomb787.extended_aviation.entities.TestPlaneEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExtendedAviation.MOD_ID);
	
	//Aircraft
	public static final RegistryObject<AircraftItem> TEST = ITEMS.register("test_plane", 
			() -> new AircraftItem(new Item.Properties().stacksTo(1).tab(ItemGroups.GROUP), world -> new TestPlaneEntity(EntityInit.TEST_PLANE.get(), world)));
	
	public static final RegistryObject<AircraftItem> TEST_HELI = ITEMS.register("test_heli", 
			() -> new AircraftItem(new Item.Properties().stacksTo(1).tab(ItemGroups.GROUP), world -> new TestHelicopterEntity(EntityInit.TEST_HELI.get(), world)));
	
	public static final RegistryObject<AircraftItem> RELIANT_ROBIN = ITEMS.register("reliant_robin", 
			() -> new AircraftItem(new Item.Properties().stacksTo(1).tab(ItemGroups.GROUP), world -> new ReliantRobinEntity(EntityInit.RELIANT_ROBIN.get(), world)));
	
	public static final RegistryObject<AircraftItem> C172 = ITEMS.register("c172", 
			() -> new AircraftItem(new Item.Properties().stacksTo(1).tab(ItemGroups.GROUP), world -> new C172Entity(EntityInit.C172.get(), world)));
	
	//Other Items
	public static final RegistryObject<Item> METAL_FUSELAGE = ITEMS.register("metal_fuselage",
			() -> new Item(new Item.Properties().tab(ItemGroups.GROUP)));
	
	public static final RegistryObject<Item> WHEEL = ITEMS.register("wheel",
			() -> new Item(new Item.Properties().tab(ItemGroups.GROUP)));
	
	public static final RegistryObject<Item> SMALL_ROTOR = ITEMS.register("small_rotor",
			() -> new Item(new Item.Properties().tab(ItemGroups.GROUP)));
	
	public static final RegistryObject<Item> LARGE_ROTOR = ITEMS.register("large_rotor",
			() -> new Item(new Item.Properties().tab(ItemGroups.GROUP)));
	
	public static final RegistryObject<Item> LARGE_PROPELLER = ITEMS.register("large_propeller",
			() -> new Item(new Item.Properties().tab(ItemGroups.GROUP)));


}