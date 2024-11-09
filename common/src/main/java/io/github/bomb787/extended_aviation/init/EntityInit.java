package io.github.bomb787.extended_aviation.init;

import immersive_aircraft.cobalt.registration.Registration;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entity.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class EntityInit {

    public static Supplier<EntityType<ScarletBiplaneEntity>> SCARLET_BIPLANE_ENTITY;
    public static Supplier<EntityType<EconomyPlaneEntity>> ECONOMY_PLANE_ENTITY;
    public static Supplier<EntityType<ReliantRobinEntity>> RELIANT_ROBIN;
    //public static Supplier<EntityType<C172Entity>> C172;
    public static Supplier<EntityType<RamielEntity>> RAMIEL;
    public static Supplier<EntityType<HotAirBalloonEntity>> HOT_AIR_BALLOON;
    public static Supplier<EntityType<VillagercopterEntity>> VILLAGERCOPTER;
    public static Supplier<EntityType<TrikeUltralightEntity>> TRIKE_ULTRALIGHT;

    public static void init() {
        SCARLET_BIPLANE_ENTITY = register("scarlet_biplane", EntityType.Builder
                .of(ScarletBiplaneEntity::new, MobCategory.MISC)
                .sized(2.25f, 3.5f)
                .clientTrackingRange(12)
                .fireImmune()
        );

        ECONOMY_PLANE_ENTITY = register("economy_plane", EntityType.Builder
                .of(EconomyPlaneEntity::new, MobCategory.MISC)
                .sized(2.5f, 4.0f)
                .clientTrackingRange(12)
                .fireImmune()
        );

        RELIANT_ROBIN = register("reliant_robin", EntityType.Builder
                .of(ReliantRobinEntity::new, MobCategory.MISC)
                .sized(3f, 3f)
                .clientTrackingRange(12)
                .fireImmune()
        );

        /*C172 = register("c172", EntityType.Builder
                .of(C172Entity::new, MobCategory.MISC)
                .sized(3f, 3f)
                .clientTrackingRange(12)
                .fireImmune()
        );*/

        RAMIEL = register("ramiel", EntityType.Builder
                .of(RamielEntity::new, MobCategory.MISC)
                .sized(1.5f, 2.25f)
                .clientTrackingRange(12)
                .fireImmune()
        );

        HOT_AIR_BALLOON = register("hot_air_balloon", EntityType.Builder
                .of(HotAirBalloonEntity::new, MobCategory.MISC)
                .sized(1.5f, 2.0f)
                .clientTrackingRange(12)
        );

        VILLAGERCOPTER = register("villagercopter", EntityType.Builder
                .of(VillagercopterEntity::new, MobCategory.MISC)
                .sized(3f, 3.5f)
                .clientTrackingRange(12)
        );

        TRIKE_ULTRALIGHT = register("trike_ultralight", EntityType.Builder
                .of(TrikeUltralightEntity::new, MobCategory.MISC)
                .sized(2.5f, 3f)
                .clientTrackingRange(12)
        );
    }

    static <T extends Entity> Supplier<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        ResourceLocation id = ExtendedAviation.locate(name);
        return Registration.register(BuiltInRegistries.ENTITY_TYPE, id, () -> builder.build(id.toString()));
    }
}