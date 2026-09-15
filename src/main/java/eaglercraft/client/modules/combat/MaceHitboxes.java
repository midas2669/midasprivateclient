package eaglercraft.client.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import java.util.List;

/**
 * Ported from Meteor Client - Hitboxes with Mace Support
 * Expands entity hitboxes when holding certain weapons
 * Adapted for Eaglercraft 1.14
 */
public class MaceHitboxes {
    private static boolean enabled = false;
    private static double expandValue = 0.5;
    private static boolean maceHitboxEnabled = true;
    private static boolean ignoreFriends = true;
    
    public static void enable() {
        enabled = true;
        System.out.println("[MaceHitboxes] Enabled - Expanded hitbox support");
    }
    
    public static void disable() {
        enabled = false;
        System.out.println("[MaceHitboxes] Disabled");
    }
    
    public static boolean isEnabled() {
        return enabled;
    }
    
    public static void setExpandValue(double value) {
        expandValue = Math.max(0, value);
    }
    
    public static void setMaceHitboxEnabled(boolean value) {
        maceHitboxEnabled = value;
    }
    
    public static void setIgnoreFriends(boolean value) {
        ignoreFriends = value;
    }
    
    /**
     * Get expanded hitbox value for entity
     * Returns the amount to expand the hitbox, or 0 if shouldn't expand
     */
    public static double getEntityExpand(Entity entity, Player player) {
        if (!enabled) return 0.0;
        
        if (player == null) return 0.0;
        
        // Check if player is holding mace or other weapon
        ItemStack weapon = player.getMainHandItem();
        if (weapon == null || weapon.isEmpty()) return 0.0;
        
        // Only expand when holding valid weapon
        boolean hasMace = maceHitboxEnabled && MaceCombatModule.isMace(weapon);
        boolean hasWeapon = hasMace; // Can add other weapons here
        
        if (!hasWeapon) return 0.0;
        
        // Ignore friends if enabled
        if (ignoreFriends && entity instanceof Player targetPlayer) {
            // TODO: Implement friend checking
            // if (isFriend(targetPlayer)) return 0.0;
        }
        
        // Return expand value for attackable entities
        if (entity instanceof EntityLivingBase) {
            return expandValue;
        }
        
        return 0.0;
    }
    
    /**
     * Expand entity's bounding box for easier clicking
     */
    public static double getEntityHitboxExpand(Entity entity, Player player) {
        return getEntityExpand(entity, player);
    }
    
    /**
     * Get the expanded hitbox value (used in rendering/collision)
     */
    public static double getExpandedHitbox(Entity entity, Player player) {
        double expand = getEntityExpand(entity, player);
        if (expand > 0) {
            System.out.println("[MaceHitboxes] Expanded hitbox for " + entity.getClass().getSimpleName() + ": +" + expand);
        }
        return expand;
    }
}
