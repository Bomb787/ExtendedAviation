package io.github.bomb787.extended_aviation.init;

import immersive_aircraft.Items;
import immersive_aircraft.cobalt.registration.Registration;
import immersive_aircraft.item.AircraftItem;
import immersive_aircraft.item.DyeableAircraftItem;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.entity.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

import static immersive_aircraft.Items.baseProps;

public class ItemInit {

    public static Supplier<Item> SCARLET_BIPLANE_ITEM;
    public static Supplier<Item> ECONOMY_PLANE_ITEM;
    public static Supplier<Item> RELIANT_ROBIN;
    //public static Supplier<Item> C172;
    public static Supplier<Item> RAMIEL;
    public static Supplier<Item> RED_BALL_THING;
    public static Supplier<Item> HOT_AIR_BALLOON;
    public static Supplier<Item> VILLAGERCOPTER;
    public static Supplier<Item> TRIKE_ULTRALIGHT;

    public static void init() {
        SCARLET_BIPLANE_ITEM = register("scarlet_biplane", () -> new DyeableAircraftItem(baseProps().stacksTo(1), world -> new ScarletBiplaneEntity(EntityInit.SCARLET_BIPLANE_ENTITY.get(), world)));
        ECONOMY_PLANE_ITEM = register("economy_plane", () -> new DyeableAircraftItem(baseProps().stacksTo(1), world -> new EconomyPlaneEntity(EntityInit.ECONOMY_PLANE_ENTITY.get(), world)));
        RELIANT_ROBIN = register("reliant_robin", () -> new AircraftItem(baseProps().stacksTo(1), world -> new ReliantRobinEntity(EntityInit.RELIANT_ROBIN.get(), world)));
        //C172 = register("c172", () -> new AircraftItem(baseProps().stacksTo(1), world -> new C172Entity(EntityInit.C172.get(), world)));
        RAMIEL = register("ramiel", () -> new AircraftItem(baseProps().stacksTo(1), world -> new RamielEntity(EntityInit.RAMIEL.get(), world)));
        RED_BALL_THING = register("red_ball_thing", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));
        HOT_AIR_BALLOON = register("hot_air_balloon", () -> new AircraftItem(baseProps().stacksTo(1), world -> new HotAirBalloonEntity(EntityInit.HOT_AIR_BALLOON.get(), world)));
        VILLAGERCOPTER = register("villagercopter", () -> new AircraftItem(baseProps().stacksTo(1), world -> new VillagercopterEntity(EntityInit.VILLAGERCOPTER.get(), world)));
        TRIKE_ULTRALIGHT = register("trike_ultralight", () -> new DyeableAircraftItem(baseProps().stacksTo(1), world -> new TrikeUltralightEntity(EntityInit.TRIKE_ULTRALIGHT.get(), world)));
    }

    static Supplier<Item> register(String name, Supplier<Item> item) {
        Supplier<Item> register = Registration.register(BuiltInRegistries.ITEM, ExtendedAviation.locate(name), item);
        Items.items.add(register);
        return register;
    }

}