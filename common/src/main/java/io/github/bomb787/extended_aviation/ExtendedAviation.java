package io.github.bomb787.extended_aviation;

import io.github.bomb787.extended_aviation.init.*;
import net.minecraft.resources.ResourceLocation;

public class ExtendedAviation {
    public static final String MOD_ID = "extended_aviation";

    public static void init() {
        ItemInit.init();
        EntityInit.init();
        SoundInit.init();
        PacketInit.init();
        StatInit.init();
    }

    public static ResourceLocation locate(String name) {
        return new ResourceLocation(ExtendedAviation.MOD_ID, name);
    }
}
