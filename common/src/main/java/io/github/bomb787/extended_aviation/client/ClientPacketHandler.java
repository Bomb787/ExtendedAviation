package io.github.bomb787.extended_aviation.client;

import io.github.bomb787.extended_aviation.entity.ExtendedAirplaneEntity;
import io.github.bomb787.extended_aviation.network.ComponentSyncS2CPacket;
import net.minecraft.client.Minecraft;

public class ClientPacketHandler {

    public static void handleComponentSyncPacket(ComponentSyncS2CPacket packet) {
        Minecraft client = Minecraft.getInstance();
         if(client.level != null && client.level.getEntity(packet.id) instanceof ExtendedAirplaneEntity entity) {
            entity.syncClient(packet.flapTarget, packet.currentFlap, packet.gearTarget, packet.currentGear, packet.speedbrakeTarget, packet.currentSpeedbrake);
         }
    }

}