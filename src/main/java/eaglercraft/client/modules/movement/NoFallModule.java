package eaglercraft.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class NoFallModule {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean isEnabled = false;
    private static float damageThreshold = 3.0f;
    
    public static void enable() {
        isEnabled = true;
        System.out.println("[NoFall] Enabled");
    }
    
    public static void disable() {
        isEnabled = false;
        System.out.println("[NoFall] Disabled");
    }
    
    public static void onTick() {
        if (!isEnabled) return;
        
        EntityPlayer player = mc.player;
        if (player == null) return;
        
        if (player.fallDistance > damageThreshold) {
            player.fallDistance = 0.0f;
            player.velocityChanged = true;
        }
    }
    
    public static void setDamageThreshold(float threshold) {
        damageThreshold = Math.max(0.0f, threshold);
    }
    
    public static boolean isActive() {
        return isEnabled;
    }
}
