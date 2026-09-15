package eaglercraft.client.modules.render;

import eaglercraft.client.gui.EaglerBactroMod;
import net.minecraft.client.Minecraft;

public class FogControlModule {
    private static final Minecraft mc = Minecraft.getMinecraft();
    
    public static boolean shouldRenderFog(String fogType) {
        return EaglerBactroMod.shouldRenderFog(fogType);
    }
    
    public static void onFogRender(String fogType) {
        if (!shouldRenderFog(fogType)) {
            disableFog();
        }
    }
    
    private static void disableFog() {
        try {
            Class.forName("net.minecraft.client.renderer.RenderGlobal")
                .getDeclaredMethod("setupFog", int.class, float.class)
                .setAccessible(true);
        } catch (Exception e) {
            System.out.println("[FogControl] Could not disable fog: " + e.getMessage());
        }
    }
}
