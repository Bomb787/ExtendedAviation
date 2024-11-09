package io.github.bomb787.extended_aviation.init;

import immersive_aircraft.item.upgrade.VehicleStat;

public class StatInit {

    public static VehicleStat FLAP_DRAG;
    public static VehicleStat FLAP_LIFT;
    public static VehicleStat FLAP_SPEED;
    public static VehicleStat GEAR_DRAG;
    public static VehicleStat GEAR_SPEED;
    public static VehicleStat SPEEDBRAKE_DRAG;
    public static VehicleStat SPEEDBRAKE_SPEED;
    //Speed stats are how many ticks the component needs to extend/retract.

    public static void init() {
        FLAP_DRAG = VehicleStat.register("flapDrag", false);
        FLAP_LIFT = VehicleStat.register("flapLift", true);
        FLAP_SPEED = VehicleStat.register("flapSpeed", true, 20f);
        GEAR_DRAG = VehicleStat.register("gearDrag", false);
        GEAR_SPEED = VehicleStat.register("gearSpeed", true, 20f);
        SPEEDBRAKE_DRAG = VehicleStat.register("speedbrakeDrag", true);
        SPEEDBRAKE_SPEED = VehicleStat.register("speedbrakeSpeed", true, 20f);
    }

}