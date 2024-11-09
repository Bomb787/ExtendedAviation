package io.github.bomb787.extended_aviation.forge;

import io.github.bomb787.extended_aviation.ExtendedAviation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod(ExtendedAviation.MOD_ID)
@Mod.EventBusSubscriber(modid = ExtendedAviation.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExtendedAviationForge {
    private static boolean registered = false;

    @SubscribeEvent
    public static void onRegistryEvent(RegisterEvent event) {
        if (!registered) {
            registered = true;
            ExtendedAviation.init();
        }
    }

    static {
        new PacketHandlerImpl();
    }
}