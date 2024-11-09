package io.github.bomb787.extended_aviation.init;

import immersive_aircraft.cobalt.network.NetworkHandler;
import io.github.bomb787.extended_aviation.network.ComponentSyncS2CPacket;
import io.github.bomb787.extended_aviation.network.PacketHandler;
import io.github.bomb787.extended_aviation.network.SpecialActionC2SPacket;

public class PacketInit {

    public static void init() {
        NetworkHandler.registerMessage(SpecialActionC2SPacket.class, SpecialActionC2SPacket::new);
        PacketHandler.registerMessage(ComponentSyncS2CPacket.class, ComponentSyncS2CPacket::new);
    }

}