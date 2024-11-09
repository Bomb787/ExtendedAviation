package io.github.bomb787.extended_aviation.network;

import immersive_aircraft.cobalt.network.Message;
import io.github.bomb787.extended_aviation.client.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ComponentSyncS2CPacket extends Message {

    public final int id;
    public final float flapTarget, currentFlap, gearTarget, currentGear, speedbrakeTarget, currentSpeedbrake;

    public ComponentSyncS2CPacket(int id, float flapTarget, float currentFlap, float gearTarget, float currentGear, float speedbrakeTarget, float currentSpeedbrake) {
        this.id = id;
        this.flapTarget = flapTarget;
        this.currentFlap = currentFlap;
        this.gearTarget = gearTarget;
        this.currentGear = currentGear;
        this.speedbrakeTarget = speedbrakeTarget;
        this.currentSpeedbrake = currentSpeedbrake;
    }

    public ComponentSyncS2CPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.flapTarget = buf.readFloat();
        this.currentFlap = buf.readFloat();
        this.gearTarget = buf.readFloat();
        this.currentGear = buf.readFloat();
        this.speedbrakeTarget = buf.readFloat();
        this.currentSpeedbrake = buf.readFloat();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeFloat(flapTarget);
        buf.writeFloat(currentFlap);
        buf.writeFloat(gearTarget);
        buf.writeFloat(currentGear);
        buf.writeFloat(speedbrakeTarget);
        buf.writeFloat(currentSpeedbrake);
    }

    @Override
    public void receive(Player player) {
        ClientPacketHandler.handleComponentSyncPacket(this);
    }

}