package io.github.bomb787.extended_aviation.forge;

import immersive_aircraft.cobalt.network.NetworkHandler;
import io.github.bomb787.extended_aviation.ExtendedAviation;
import io.github.bomb787.extended_aviation.client.Keybinds;
import io.github.bomb787.extended_aviation.network.SpecialActionC2SPacket;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExtendedAviation.MOD_ID)
public class Events {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            while(Keybinds.flaps.consumeClick()) {
                NetworkHandler.sendToServer(new SpecialActionC2SPacket(SpecialActionC2SPacket.Action.FLAPS));
            }
            while(Keybinds.gear.consumeClick()) {
                NetworkHandler.sendToServer(new SpecialActionC2SPacket(SpecialActionC2SPacket.Action.GEAR));
            }
            while(Keybinds.speedbrakes.consumeClick()) {
                NetworkHandler.sendToServer(new SpecialActionC2SPacket(SpecialActionC2SPacket.Action.SPEEDBRAKES));
            }
        }
    }

}