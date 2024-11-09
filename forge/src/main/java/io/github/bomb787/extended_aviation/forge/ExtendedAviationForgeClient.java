package io.github.bomb787.extended_aviation.forge;

import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.ExtendedAviationClient;
import io.github.bomb787.extended_aviation.client.Keybinds;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ExtendedAviation.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExtendedAviationForgeClient {
    @SubscribeEvent
    public static void setup(EntityRenderersEvent.RegisterRenderers event) {
        ExtendedAviationClient.init();
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        Keybinds.list.forEach(event::register);
    }
}
