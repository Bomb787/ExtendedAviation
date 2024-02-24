package io.github.bomb787.extended_aviation.init;

import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entities.C172Entity;
import io.github.bomb787.extended_aviation.entities.ReliantRobinEntity;
import io.github.bomb787.extended_aviation.entities.TestHelicopterEntity;
import io.github.bomb787.extended_aviation.entities.TestPlaneEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
	
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ExtendedAviation.MOD_ID);
	
	public static final RegistryObject<EntityType<TestPlaneEntity>> TEST_PLANE = ENTITIES.register("test_plane", 
			() -> EntityType.Builder.of(TestPlaneEntity::new, MobCategory.MISC).sized(3f, 3f).build("test_plane"));
	
	public static final RegistryObject<EntityType<TestHelicopterEntity>> TEST_HELI = ENTITIES.register("test_heli", 
			() -> EntityType.Builder.of(TestHelicopterEntity::new, MobCategory.MISC).sized(3f, 3f).build("test_heli"));
	
	public static final RegistryObject<EntityType<ReliantRobinEntity>> RELIANT_ROBIN = ENTITIES.register("reliant_robin", 
			() -> EntityType.Builder.of(ReliantRobinEntity::new, MobCategory.MISC).sized(3f, 3f).build("reliant_robin"));
	
	/*public static final RegistryObject<EntityType<C172Entity>> C172 = ENTITIES.register("c172", 
			() -> EntityType.Builder.of(C172Entity::new, MobCategory.MISC).sized(3f, 3f).build("c172"));*/

}