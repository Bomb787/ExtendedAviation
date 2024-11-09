package io.github.bomb787.extended_aviation.client;

import com.mojang.blaze3d.platform.InputConstants;
import immersive_aircraft.Main;
import immersive_aircraft.client.FallbackKeyMapping;
import immersive_aircraft.client.MultiKeyMapping;
import immersive_aircraft.config.Config;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class Keybinds {

    public static final List<KeyMapping> list = new LinkedList<>();

    public static final KeyMapping flaps, gear, speedbrakes;

    static {
        ///if (Config.getInstance().useCustomKeybindSystem && Main.MOD_LOADER.equals("fabric")) {
            flaps = newMultiKey("multi_control_flaps", GLFW.GLFW_KEY_F);
            gear = newMultiKey("multi_control_gear", GLFW.GLFW_KEY_G);
            speedbrakes = newMultiKey("multi_control_speedbrakes", GLFW.GLFW_KEY_SLASH);
        ///} else {
            //Minecraft client = Minecraft.getInstance();
            //flaps = newFallbackKey("fallback_control_flaps", () -> client.options.keyLeft);
            //gear = newFallbackKey("fallback_control_gear", () -> client.options.keyRight);
            //speedbrakes = newFallbackKey("fallback_control_speedbrakes", () -> client.options.keyUp);
        //}
    }

    private static KeyMapping newFallbackKey(String name, Supplier<KeyMapping> fallback) {
        KeyMapping key = new FallbackKeyMapping(
                "key.immersive_aircraft." + name,
                InputConstants.Type.KEYSYM,
                fallback,
                "itemGroup.immersive_aircraft.immersive_aircraft_tab"
        );
        list.add(key);
        return key;
    }

    private static KeyMapping newMultiKey(String name, int defaultKey) {
        KeyMapping key = new MultiKeyMapping(
                "key.immersive_aircraft." + name,
                InputConstants.Type.KEYSYM,
                defaultKey,
                "itemGroup.immersive_aircraft.immersive_aircraft_tab"
        );
        list.add(key);
        return key;
    }

}