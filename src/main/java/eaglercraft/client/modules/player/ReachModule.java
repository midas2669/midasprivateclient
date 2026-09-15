package eaglercraft.client.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemAxe;

public class ReachModule {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean isEnabled = false;
    private static float reachDistance = 6.0f;
    private static final float DEFAULT_REACH = 4.5f;
    
    public static void enable() {
        isEnabled = true;
        System.out.println("[Reach] Enabled with distance: " + reachDistance);
    }
    
    public static void disable() {
        isEnabled = false;
        System.out.println("[Reach] Disabled");
    }
    
    public static void onAttack(EntityPlayer player) {
        if (!isEnabled) return;
        System.out.println("[Reach] Attack extended to " + reachDistance + " blocks");
    }
    
    public static float getReachDistance() {
        return isEnabled ? reachDistance : DEFAULT_REACH;
    }
    
    public static void setReachDistance(float distance) {
        reachDistance = Math.max(DEFAULT_REACH, Math.min(distance, 20.0f));
    }
    
    public static boolean isActive() {
        return isEnabled;
    }
}
