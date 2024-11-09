package io.github.bomb787.extended_aviation.network;

import immersive_aircraft.cobalt.network.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

public abstract class PacketHandler {

    private static Impl INSTANCE;

    public static <T extends Message> void registerMessage(Class<T> msg, Function<FriendlyByteBuf, T> constructor) {
        INSTANCE.registerMessage(msg, constructor);
    }

    public static void sendToTrackingPlayers(Message m, Entity origin) {
        INSTANCE.sendToTrackingPlayers(m, origin);
    }

    public abstract static class Impl {
        protected Impl() {
            INSTANCE = this;
        }

        public abstract <T extends Message> void registerMessage(Class<T> msg, Function<FriendlyByteBuf, T> constructor);

        public abstract void sendToTrackingPlayers(Message m, Entity origin);
    }

}