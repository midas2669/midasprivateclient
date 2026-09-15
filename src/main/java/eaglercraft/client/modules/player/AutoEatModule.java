package eaglercraft.client.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class AutoEatModule {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean isEnabled = false;
    private static float hungerThreshold = 10.0f;
    private static int eatDelay = 0;
    
    public static void enable() {
        isEnabled = true;
        System.out.println("[AutoEat] Enabled with hunger threshold: " + hungerThreshold);
    }
    
    public static void disable() {
        isEnabled = false;
        System.out.println("[AutoEat] Disabled");
    }
    
    public static void onTick() {
        if (!isEnabled) return;
        
        EntityPlayer player = mc.player;
        if (player == null) return;
        
        if (eatDelay > 0) {
            eatDelay--;
            return;
        }
        
        if (player.getFoodStats().getFoodLevel() > hungerThreshold) return;
        
        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            ItemStack item = player.inventory.mainInventory[i];
            
            if (item != null && item.getItem() instanceof ItemFood) {
                player.inventory.currentItem = i;
                mc.playerController.attackBlock(player.getPosition(), null);
                mc.playerController.updateController();
                eatDelay = 15;
                return;
            }
        }
    }
    
    public static void setHungerThreshold(float threshold) {
        hungerThreshold = Math.max(0.0f, Math.min(threshold, 20.0f));
    }
    
    public static boolean isActive() {
        return isEnabled;
    }
}
