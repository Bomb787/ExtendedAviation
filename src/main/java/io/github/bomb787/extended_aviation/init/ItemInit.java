package io.github.bomb787.extended_aviation.init;

import immersive_aircraft.ItemGroups;
import immersive_aircraft.item.AircraftItem;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entities.ReliantRobinEntity;
import io.github.bomb787.extended_aviation.entities.TestHelicopterEntity;
import io.github.bomb787.extended_aviation.entities.TestPlaneEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExtendedAviation.MOD_ID);
	
	public static final RegistryObject<AircraftItem> TEST = ITEMS.register("test", 
			() -> new AircraftItem(new Item.Properties().stacksTo(1).tab(ItemGroups.GROUP), world -> new TestPlaneEntity(EntityInit.TEST.get(), world)));
	
	public static final RegistryObject<AircraftItem> TEST_HELI = ITEMS.register("test_heli", 
			() -> new AircraftItem(new Item.Properties().stacksTo(1).tab(ItemGroups.GROUP), world -> new TestHelicopterEntity(EntityInit.TEST_HELI.get(), world)));
	
	public static final RegistryObject<AircraftItem> RELIANT_ROBIN = ITEMS.register("reliant_robin", 
			() -> new AircraftItem(new Item.Properties().stacksTo(1).tab(ItemGroups.GROUP), world -> new ReliantRobinEntity(EntityInit.RELIANT_ROBIN.get(), world)));

}