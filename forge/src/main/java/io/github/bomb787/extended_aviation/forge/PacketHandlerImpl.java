package io.github.bomb787.extended_aviation.forge;

import immersive_aircraft.Main;
import immersive_aircraft.cobalt.network.Message;
import io.github.bomb787.extended_aviation.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class PacketHandlerImpl extends PacketHandler.Impl {

    private static final String PROTOCOL_VERSION = "1";

    private final SimpleChannel channel = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Main.SHORT_MOD_ID, "extra"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private int id = 0;

    @Override
    public <T extends Message> void registerMessage(Class<T> msg, Function<FriendlyByteBuf, T> constructor) {
        channel.registerMessage(id++, msg,
                Message::encode,
                constructor,
                (m, ctx) -> {
                    ctx.get().enqueueWork(() -> {
                        ServerPlayer sender = ctx.get().getSender();
                        m.receive(sender);
                    });
                    ctx.get().setPacketHandled(true);
                });
    }

    @Override
    public void sendToTrackingPlayers(Message m, Entity origin) {
        channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> origin), m);
    }

}