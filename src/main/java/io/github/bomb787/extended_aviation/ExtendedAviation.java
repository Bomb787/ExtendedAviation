package io.github.bomb787.extended_aviation;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import io.github.bomb787.extended_aviation.client.render.entity.renderer.TestPlaneEntityRenderer;
import io.github.bomb787.extended_aviation.init.EntityInit;
import io.github.bomb787.extended_aviation.init.ItemInit;
import io.github.bomb787.extended_aviation.init.SoundInit;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExtendedAviation.MOD_ID)
public class ExtendedAviation {
    
    public static final String MOD_ID = "extended_aviation";
    
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExtendedAviation() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        
        EntityInit.ENTITIES.register(bus);
        ItemInit.ITEMS.register(bus);
        SoundInit.SOUNDS.register(bus);
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    	
    }
    
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    	
    }
    
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        	event.registerEntityRenderer(EntityInit.TEST.get(), TestPlaneEntityRenderer::new);
        }
    }
}