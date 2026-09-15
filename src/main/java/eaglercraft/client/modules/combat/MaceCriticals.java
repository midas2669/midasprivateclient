package eaglercraft.client.modules.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

/**
 * Ported from Meteor Client - Criticals with Mace Support
 * Handles mace smash attacks and critical hits
 * Adapted for Eaglercraft 1.14
 */
public class MaceCriticals {
    private static boolean enabled = false;
    private static boolean smashAttackEnabled = true;
    private static double additionalHeight = 0.0;
    private static int lastSmashTime = 0;
    
    public static void enable() {
        enabled = true;
        System.out.println("[MaceCriticals] Enabled - Mace smash attacks & criticals");
    }
    
    public static void disable() {
        enabled = false;
        System.out.println("[MaceCriticals] Disabled");
    }
    
    public static boolean isEnabled() {
        return enabled;
    }
    
    public static void setSmashAttackEnabled(boolean value) {
        smashAttackEnabled = value;
    }
    
    public static void setAdditionalHeight(double height) {
        additionalHeight = Math.max(0, height); // Minimum 0
    }
    
    /**
     * Check if player should perform smash attack
     */
    public static boolean shouldPerformSmash(Player player, ItemStack weapon) {
        if (!enabled || !smashAttackEnabled) return false;
        
        // Must be holding a mace
        if (!MaceCombatModule.isMace(weapon)) return false;
        
        // Must be falling (smash requires fall distance)
        if (player.fallDistance < 1.5f) return false;
        
        // Not in elytra flight
        if (player.isElytraFlying()) return false;
        
        return true;
    }
    
    /**
     * Calculate smash attack height bonus
     * Used to spoof height for packet-based smash attacks
     */
    public static double getSmashHeight(Player player) {
        if (!shouldPerformSmash(player, player.getMainHandItem())) {
            return 0.0;
        }
        
        // Base smash height + additional configured height
        return 1.501 + additionalHeight;
    }
    
    /**
     * Prepare mace for smash attack
     * Returns the position where smash should occur
     */
    public static Vec3 prepareMaceSmash(Player player) {
        if (!shouldPerformSmash(player, player.getMainHandItem())) {
            return null;
        }
        
        ItemStack mace = player.getMainHandItem();
        double fallDistance = player.fallDistance;
        
        // Calculate smash damage
        float damage = MaceCombatModule.calculateSmashDamage(fallDistance);
        
        System.out.println("[MaceCriticals] Preparing smash attack");
        System.out.println("  Fall distance: " + fallDistance);
        System.out.println("  Base damage: " + damage);
        
        // Apply mace enchantments
        float totalDamage = MaceAttributeSwap.calculateMaceDamage(mace, (float) fallDistance);
        System.out.println("  Total damage: " + totalDamage);
        
        return player.getPosition(1.0f);
    }
    
    /**
     * Check if smash attack cooldown is ready
     */
    public static boolean isSmashReady() {
        int currentTime = (int) System.currentTimeMillis();
        return (currentTime - lastSmashTime) > 500; // 500ms cooldown
    }
    
    /**
     * Mark smash attack as performed
     */
    public static void performSmash() {
        lastSmashTime = (int) System.currentTimeMillis();
        System.out.println("[MaceCriticals] Smash attack performed!");
    }
    
    /**
     * Perform critical hit (non-smash mace attack)
     */
    public static void performCritical(Player player, ItemStack weapon) {
        if (!enabled || weapon == null) return;
        
        // Can be any weapon, not just mace
        System.out.println("[MaceCriticals] Critical hit performed!");
        
        // For mace, bonus damage on crit
        if (MaceCombatModule.isMace(weapon)) {
            float critDamage = 1.5f + (float) (Math.random() * 0.5);
            System.out.println("  Mace critical multiplier: " + critDamage + "x");
        }
    }
}
