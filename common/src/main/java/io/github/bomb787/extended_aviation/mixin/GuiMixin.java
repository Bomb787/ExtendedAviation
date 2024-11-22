package io.github.bomb787.extended_aviation.mixin;

import immersive_aircraft.entity.VehicleEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow public abstract Font getFont();

    private boolean a = false;

    @Inject(method = "render", at = @At("TAIL"))
    public void extended_aviation$renderGui(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        if(Minecraft.getInstance().player.getRootVehicle() instanceof VehicleEntity entity) {
            double pitch = -entity.getXRot();
            guiGraphics.drawString(this.getFont(), "Corrected Pitch: " + correctPitch(entity.getXRot()), 5, 5, 0xFFFFFF);
            guiGraphics.drawString(this.getFont(), "xRot: " + entity.getXRot(), 5, 15, 0xFFFFFF);
            double movement = Mth.RAD_TO_DEG * Math.asin(entity.getDeltaMovement().y / entity.getDeltaMovement().length());
            guiGraphics.drawString(this.getFont(), "Movement Y: " + entity.getDeltaMovement().y, 5, 25, 0xFFFFFF);
            guiGraphics.drawString(this.getFont(), "Movement Length: " + entity.getDeltaMovement().length(), 5, 35, 0xFFFFFF);
            guiGraphics.drawString(this.getFont(), "Corrected Movement: " + correctMovement(movement, entity), 5, 45, 0xFFFFFF);
            guiGraphics.drawString(this.getFont(), "Movement: " + movement, 5, 55, 0xFFFFFF);
            guiGraphics.drawString(this.getFont(), "AoA: " + (-entity.getXRot() - movement), 5, 65, 0xFFFFFF);
        }
    }

    private float correctPitch(float xRot) {
        if(xRot <= 0) {
            return -xRot;
        } else {
            return 360 - xRot;
        }
    }

    private double correctMovement(double movementAngle, VehicleEntity entity) {
        double pitch = correctPitch(entity.getXRot());
        if(pitch >= 0 && pitch <= 90) {
            return movementAngle;
        } else {
            return 180 - movementAngle;
        }
    }

}