package eaglercraft.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

public class SpeedModule {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean isEnabled = false;
    private static float speedMultiplier = 1.5f;
    private static float maxSpeed = 1.0f;
    
    public static void enable() {
        isEnabled = true;
        System.out.println("[Speed] Enabled with multiplier: " + speedMultiplier);
    }
    
    public static void disable() {
        isEnabled = false;
        System.out.println("[Speed] Disabled");
    }
    
    public static void onTick() {
        if (!isEnabled) return;
        
        EntityPlayer player = mc.player;
        if (player == null || player.isInWater()) return;
        
        KeyBinding forward = mc.gameSettings.keyBindForward;
        KeyBinding back = mc.gameSettings.keyBindBack;
        KeyBinding left = mc.gameSettings.keyBindLeft;
        KeyBinding right = mc.gameSettings.keyBindRight;
        
        if (forward.isKeyDown() || back.isKeyDown() || left.isKeyDown() || right.isKeyDown()) {
            double currentSpeed = Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
            
            if (currentSpeed < maxSpeed) {
                double motionLength = Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
                
                if (motionLength > 0) {
                    player.motionX = (player.motionX / motionLength) * maxSpeed * speedMultiplier;
                    player.motionZ = (player.motionZ / motionLength) * maxSpeed * speedMultiplier;
                }
            }
        }
    }
    
    public static void setMultiplier(float multiplier) {
        speedMultiplier = Math.max(0.1f, multiplier);
    }
    
    public static float getMultiplier() {
        return speedMultiplier;
    }
    
    public static boolean isActive() {
        return isEnabled;
    }
}
