package io.github.bomb787.extended_aviation.fabric;

import immersive_aircraft.cobalt.network.NetworkHandler;
import io.github.bomb787.extended_aviation.ExtendedAviationClient;
import io.github.bomb787.extended_aviation.client.Keybinds;
import io.github.bomb787.extended_aviation.entity.ExtendedAirplaneEntity;
import io.github.bomb787.extended_aviation.network.SpecialActionC2SPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;

public class ExtendedAviationFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ExtendedAviationClient.init();
        Keybinds.list.forEach(KeyBindingHelper::registerKeyBinding);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(Keybinds.flaps.consumeClick() && Minecraft.getInstance().player.getRootVehicle() instanceof ExtendedAirplaneEntity entity && entity.hasFlaps()) {
                entity.moveFlaps();
                NetworkHandler.sendToServer(new SpecialActionC2SPacket(SpecialActionC2SPacket.Action.FLAPS));
            }
            while(Keybinds.gear.consumeClick() && Minecraft.getInstance().player.getRootVehicle() instanceof ExtendedAirplaneEntity entity && entity.hasRetracts()) {
                entity.moveGear();
                NetworkHandler.sendToServer(new SpecialActionC2SPacket(SpecialActionC2SPacket.Action.GEAR));
            }
            while(Keybinds.speedbrakes.consumeClick() && Minecraft.getInstance().player.getRootVehicle() instanceof ExtendedAirplaneEntity entity && entity.hasSpeedbrakes()) {
                entity.moveSpeedbrakes();
                NetworkHandler.sendToServer(new SpecialActionC2SPacket(SpecialActionC2SPacket.Action.SPEEDBRAKES));
            }
        });
    }
}
