package io.github.bomb787.extended_aviation.init;

import immersive_aircraft.cobalt.registration.Registration;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class SoundInit {

    public static Supplier<SoundEvent> ANGELIC_CHORUS;
    public static Supplier<SoundEvent> LASER;

    public static void init() {
        ANGELIC_CHORUS = register("angelic_chorus");
        LASER = register("laser");
    }

    static Supplier<SoundEvent> register(String name) {
        ResourceLocation id = ExtendedAviation.locate(name);
        return Registration.register(BuiltInRegistries.SOUND_EVENT, id, () -> SoundEvent.createVariableRangeEvent(id));
    }


}