package io.github.bomb787.extended_aviation.fabric;

import immersive_aircraft.Main;
import immersive_aircraft.cobalt.network.Message;
import io.github.bomb787.extended_aviation.network.PacketHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class PacketHandlerImpl extends PacketHandler.Impl {

    private final Map<Class<?>, ResourceLocation> identifiers = new HashMap<>();

    private int id = 0;

    private <T> ResourceLocation createMessageIdentifier(Class<T> msg) {
        return new ResourceLocation(Main.SHORT_MOD_ID, msg.getSimpleName().toLowerCase(Locale.ROOT).substring(0, 8) + id++);
    }

    @Override
    public <T extends Message> void registerMessage(Class<T> msg, Function<FriendlyByteBuf, T> constructor) {
        ResourceLocation identifier = createMessageIdentifier(msg);
        identifiers.put(msg, identifier);

        ServerPlayNetworking.registerGlobalReceiver(identifier, (server, player, handler, buffer, responder) -> {
            Message m = constructor.apply(buffer);
            server.execute(() -> m.receive(player));
        });

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientProxy.register(identifier, constructor);
        }
    }

    private ResourceLocation getMessageIdentifier(Message msg) {
        return Objects.requireNonNull(identifiers.get(msg.getClass()), "Used unregistered message!");
    }

    @Override
    public void sendToTrackingPlayers(Message msg, Entity origin) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        msg.encode(buf);
        for(ServerPlayer player : PlayerLookup.tracking(origin)) {
            ServerPlayNetworking.send(player, getMessageIdentifier(msg), buf);
        }
    }

    // Fabric's APIs are not side-agnostic.
    // We punt this to a separate class file to keep it from being eager-loaded on a server environment.
    // By Conczin
    private static final class ClientProxy {
        private ClientProxy() {
            throw new RuntimeException("new ClientProxy()");
        }

        public static <T extends Message> void register(ResourceLocation id, Function<FriendlyByteBuf, T> constructor) {
            ClientPlayNetworking.registerGlobalReceiver(id, (client, ignore1, buffer, ignore2) -> {
                Message m = constructor.apply(buffer);
                client.execute(() -> m.receive(client.player));
            });
        }
    }

}