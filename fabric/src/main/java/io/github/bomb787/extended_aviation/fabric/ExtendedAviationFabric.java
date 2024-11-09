package io.github.bomb787.extended_aviation.fabric;

import immersive_aircraft.fabric.CommonFabric;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import net.fabricmc.api.ModInitializer;

public class ExtendedAviationFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Force loading the Immersive Aircraft class to have networking and registration loaded
        new CommonFabric();
        ExtendedAviation.init();
    }

    static {
         new PacketHandlerImpl();
    }
}