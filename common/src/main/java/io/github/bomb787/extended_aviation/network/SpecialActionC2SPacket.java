package io.github.bomb787.extended_aviation.network;

import immersive_aircraft.cobalt.network.Message;
import io.github.bomb787.extended_aviation.entity.ExtendedAirplaneEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class SpecialActionC2SPacket extends Message {

    private final Action action;

    public SpecialActionC2SPacket(Action action) {
        this.action = action;
    }

    public SpecialActionC2SPacket(FriendlyByteBuf buf) {
        action = buf.readEnum(Action.class);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeEnum(action);
    }

    @Override
    public void receive(Player player) {
        if(player.getRootVehicle() instanceof ExtendedAirplaneEntity entity) {
            int a = this.action.ordinal();
            switch(a) {
                case 0:
                    if(entity.hasFlaps()) {
                        entity.moveFlaps();
                    }
                    break;
                case 1:
                    if(entity.hasRetracts()) {
                        entity.moveGear();
                    }
                    break;
                case 2:
                    if(entity.hasSpeedbrakes()) {
                        entity.moveSpeedbrakes();
                    }
                    break;
            }
        }
    }

    public enum Action {
        FLAPS,
        GEAR,
        SPEEDBRAKES
    }
}