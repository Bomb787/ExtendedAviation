package io.github.bomb787.extended_aviation;

import immersive_aircraft.ItemColors;
import immersive_aircraft.cobalt.registration.Registration;
import immersive_aircraft.resources.bbmodel.BBAnimationVariables;
import io.github.bomb787.extended_aviation.client.render.entity.renderer.*;
import io.github.bomb787.extended_aviation.entity.ExtendedAirplaneEntity;
import io.github.bomb787.extended_aviation.init.EntityInit;
import io.github.bomb787.extended_aviation.init.ItemInit;
import net.minecraft.client.Minecraft;

import static immersive_aircraft.ItemColors.getDyeColor;

public class ExtendedAviationClient {
    public static void init() {
        Registration.register(EntityInit.SCARLET_BIPLANE_ENTITY.get(), ScarletBiplaneEntityRenderer::new);
        Registration.register(EntityInit.ECONOMY_PLANE_ENTITY.get(), EconomyPlaneEntityRenderer::new);
        Registration.register(EntityInit.RELIANT_ROBIN.get(), ReliantRobinEntityRenderer::new);
        //Registration.register(EntityInit.C172.get(), C172EntityRenderer::new);
        Registration.register(EntityInit.RAMIEL.get(), RamielEntityRenderer::new);
        Registration.register(EntityInit.HOT_AIR_BALLOON.get(), HotAirBalloonEntityRenderer::new);
        Registration.register(EntityInit.VILLAGERCOPTER.get(), VillagercopterEntityRenderer::new);
        Registration.register(EntityInit.TRIKE_ULTRALIGHT.get(), TrikeUltralightRenderer::new);
        BBAnimationVariables.register("gear_progress");
        BBAnimationVariables.register("flap_progress");
        BBAnimationVariables.register("speedbrake_progress");
    }

    static {
        ItemColors.ITEM_COLORS.put(ItemInit.SCARLET_BIPLANE_ITEM.get(), getDyeColor(0xEF2323));
        ItemColors.ITEM_COLORS.put(ItemInit.ECONOMY_PLANE_ITEM.get(), getDyeColor(0xFFFFFF));
        ItemColors.ITEM_COLORS.put(ItemInit.TRIKE_ULTRALIGHT.get(), getDyeColor(0x0018A5));
    }
}
