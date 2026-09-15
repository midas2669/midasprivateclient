package eaglercraft.client.modules.render;

import eaglercraft.client.gui.EaglerBactroMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class OverlayControlModule {
    private static final Minecraft mc = Minecraft.getMinecraft();
    
    public static boolean shouldRenderNightVisionEffect() {
        return EaglerBactroMod.shouldRenderNightVisionEffect();
    }
    
    public static boolean shouldRenderPumpkinBlur() {
        return EaglerBactroMod.shouldRenderPumpkinBlur();
    }
    
    public static float getFireOverlayOffset() {
        return EaglerBactroMod.getFireOverlayOffset();
    }
    
    public static float getShieldRenderOffset() {
        return EaglerBactroMod.getShieldRenderOffset();
    }
    
    public static void applyFireOverlayOffset(float yOffset) {
        if (EaglerBactroMod.isFeatureEnabled("LowFire")) {
            GlStateManager.translate(0, getFireOverlayOffset(), 0);
        }
    }
    
    public static void applyShieldRenderOffset(float yOffset) {
        if (EaglerBactroMod.isFeatureEnabled("LowShield")) {
            GlStateManager.translate(0, getShieldRenderOffset(), 0);
        }
    }
}
