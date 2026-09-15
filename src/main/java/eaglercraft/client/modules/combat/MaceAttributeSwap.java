package eaglercraft.client.modules.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityUtils;

/**
 * Ported from Meteor Client - AttributeSwap for Mace
 * Handles smart weapon swapping with mace enchantments
 * Adapted for Eaglercraft 1.14
 */
public class MaceAttributeSwap {
    private static boolean maceSwappingEnabled = false;
    private static boolean densityEnabled = true;
    private static boolean breachEnabled = true;
    private static boolean windBurstEnabled = true;
    
    public static void setMaceSwappingEnabled(boolean enabled) {
        maceSwappingEnabled = enabled;
    }
    
    public static boolean isMaceSwappingEnabled() {
        return maceSwappingEnabled;
    }
    
    /**
     * Score items based on mace enchantments for smart swapping
     * Returns higher score for better mace options
     */
    public static double getMaceScore(ItemStack stack, boolean isFalling) {
        if (!maceSwappingEnabled) return 0.0;
        
        if (stack == null || stack.isEmpty()) return 0.0;
        
        // Check if it's a mace
        if (!MaceCombatModule.isMace(stack)) return 0.0;
        
        double score = 40.0; // Base mace score
        
        // Bonus for falling (smash attacks)
        if (isFalling) {
            score += 60.0; // Higher priority when falling
            
            // Density enchantment bonus
            if (densityEnabled) {
                int densityLevel = MaceCombatModule.getDensityLevel(stack);
                if (densityLevel > 0) {
                    score += (50.0 + (densityLevel * 10.0));
                }
            }
            
            // Wind Burst enchantment bonus
            if (windBurstEnabled) {
                int windBurstLevel = MaceCombatModule.getWindBurstLevel(stack);
                if (windBurstLevel > 0) {
                    score += (windBurstLevel * 20.0);
                }
            }
        }
        
        // Breach enchantment bonus for reducing armor
        if (breachEnabled) {
            int breachLevel = MaceCombatModule.getBreachLevel(stack);
            if (breachLevel > 0) {
                score += (breachLevel * 15.0);
            }
        }
        
        return score;
    }
    
    /**
     * Determines if player should swap to mace based on situation
     */
    public static boolean shouldSwapToMace(EntityLivingBase player, EntityLivingBase target) {
        if (!maceSwappingEnabled) return false;
        
        // Swap to mace if falling (for smash attacks)
        if (player.fallDistance > 1.5f) return true;
        
        // Swap to mace if target has high armor and we have breach
        if (target != null) {
            double targetArmor = getTargetArmor(target);
            if (targetArmor > 10.0 && breachEnabled) return true;
        }
        
        return false;
    }
    
    /**
     * Get target's armor value
     */
    private static double getTargetArmor(EntityLivingBase entity) {
        int armor = 0;
        // Sum armor from all armor slots
        if (entity.getEquipmentInSlot(1) != null) armor += 2; // Boots
        if (entity.getEquipmentInSlot(2) != null) armor += 5; // Leggings
        if (entity.getEquipmentInSlot(3) != null) armor += 8; // Chestplate
        if (entity.getEquipmentInSlot(4) != null) armor += 3; // Helmet
        return armor;
    }
    
    /**
     * Calculate total damage with mace enchantments
     */
    public static float calculateMaceDamage(ItemStack mace, float fallDistance) {
        if (!MaceCombatModule.isMace(mace)) return 0.0f;
        
        float baseDamage = MaceCombatModule.calculateSmashDamage(fallDistance);
        float totalDamage = baseDamage;
        
        // Apply Density enchantment multiplier
        if (densityEnabled) {
            int densityLevel = MaceCombatModule.getDensityLevel(mace);
            if (densityLevel > 0) {
                // Density: +1 damage per level * 1.5 when falling
                totalDamage += (densityLevel * 1.5f);
            }
        }
        
        return totalDamage;
    }
    
    public static void setDensityEnabled(boolean enabled) {
        densityEnabled = enabled;
    }
    
    public static void setBreachEnabled(boolean enabled) {
        breachEnabled = enabled;
    }
    
    public static void setWindBurstEnabled(boolean enabled) {
        windBurstEnabled = enabled;
    }
    
    public static boolean isDensityEnabled() {
        return densityEnabled;
    }
    
    public static boolean isBreachEnabled() {
        return breachEnabled;
    }
    
    public static boolean isWindBurstEnabled() {
        return windBurstEnabled;
    }
}
