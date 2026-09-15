package eaglercraft.client.modules.render;

import eaglercraft.client.gui.EaglerBactroMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class FullbrightModule {
    private static final Minecraft mc = Minecraft.getMinecraft();
    
    public static void onRenderTick(float partialTicks) {
        if (!EaglerBactroMod.isFeatureEnabled("Fullbright")) return;
        
        float multiplier = EaglerBactroMod.getFeatureValue("FullbrightMultiplier");
        mc.gameSettings.gammaSetting = Math.min(multiplier, 16.0f);
    }
    
    public static void resetBrightness() {
        if (EaglerBactroMod.isFeatureEnabled("Fullbright")) {
            mc.gameSettings.gammaSetting = 1.0f;
        }
    }
}
