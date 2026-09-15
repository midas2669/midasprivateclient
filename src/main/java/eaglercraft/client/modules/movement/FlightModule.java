package eaglercraft.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

public class FlightModule {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean isFlying = false;
    private static float flightSpeed = 0.1f;
    
    public static void enable() {
        EntityPlayer player = mc.player;
        if (player != null) {
            player.capabilities.isFlying = true;
            player.capabilities.allowFlying = true;
            isFlying = true;
            System.out.println("[Flight] Enabled");
        }
    }
    
    public static void disable() {
        EntityPlayer player = mc.player;
        if (player != null) {
            player.capabilities.isFlying = false;
            if (!player.capabilities.isCreativeMode) {
                player.capabilities.allowFlying = false;
            }
            isFlying = false;
            System.out.println("[Flight] Disabled");
        }
    }
    
    public static void onTick() {
        if (!isFlying) return;
        
        EntityPlayer player = mc.player;
        if (player == null) return;
        
        double motionX = 0;
        double motionY = 0;
        double motionZ = 0;
        
        KeyBinding forward = mc.gameSettings.keyBindForward;
        KeyBinding back = mc.gameSettings.keyBindBack;
        KeyBinding left = mc.gameSettings.keyBindLeft;
        KeyBinding right = mc.gameSettings.keyBindRight;
        KeyBinding jump = mc.gameSettings.keyBindJump;
        KeyBinding sneak = mc.gameSettings.keyBindSneak;
        
        if (forward.isKeyDown()) motionZ += flightSpeed;
        if (back.isKeyDown()) motionZ -= flightSpeed;
        if (left.isKeyDown()) motionX -= flightSpeed;
        if (right.isKeyDown()) motionX += flightSpeed;
        if (jump.isKeyDown()) motionY += flightSpeed;
        if (sneak.isKeyDown()) motionY -= flightSpeed;
        
        player.motionX = motionX;
        player.motionY = motionY;
        player.motionZ = motionZ;
    }
    
    public static boolean isActive() {
        return isFlying;
    }
    
    public static void setSpeed(float speed) {
        flightSpeed = Math.max(0.0f, Math.min(speed, 1.0f));
    }
    
    public static float getSpeed() {
        return flightSpeed;
    }
}
