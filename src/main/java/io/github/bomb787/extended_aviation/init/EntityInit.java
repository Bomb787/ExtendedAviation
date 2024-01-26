package io.github.bomb787.extended_aviation.init;

import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entities.TestPlaneEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
	
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ExtendedAviation.MOD_ID);
	
	public static final RegistryObject<EntityType<TestPlaneEntity>> TEST = ENTITIES.register("test", () -> EntityType.Builder.of(TestPlaneEntity::new, MobCategory.MISC).sized(3f, 3f).build("test"));

}