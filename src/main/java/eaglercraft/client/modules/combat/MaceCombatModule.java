package eaglercraft.client.modules.combat;

import java.util.Map;
import java.util.HashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

/**
 * Ported from Meteor Client - Mace Combat Support
 * Adapted for Eaglercraft 1.14 using the Eagler Dev Kit
 */
public class MaceCombatModule {
    private static final Map<String, MaceBehavior> MACE_BEHAVIORS = new HashMap<>();
    private static boolean enabled = false;
    
    public interface MaceBehavior {
        void onMaceAttack(Player player, ItemStack mace);
        void onMaceSmash(Player player, double fallDistance);
        float getSmashDamage(double fallDistance);
    }
    
    static {
        registerDefaultBehaviors();
    }
    
    private static void registerDefaultBehaviors() {
        // Smash attack behavior - uses fall distance for damage
        MACE_BEHAVIORS.put("smash_attack", new MaceBehavior() {
            @Override
            public void onMaceAttack(Player player, ItemStack mace) {
                System.out.println("[MaceCombat] Mace attack initiated");
            }
            
            @Override
            public void onMaceSmash(Player player, double fallDistance) {
                float damage = getSmashDamage(fallDistance);
                System.out.println("[MaceCombat] Smash attack! Fall distance: " + fallDistance + ", Damage: " + damage);
                applySmashEffect(player, damage);
            }
            
            @Override
            public float getSmashDamage(double fallDistance) {
                // Mace damage scales with fall distance
                // Base: 6 damage + (fallDistance - 1.5) * 2
                if (fallDistance < 1.5) return 6.0f;
                return 6.0f + (float) ((fallDistance - 1.5f) * 2f);
            }
        });
        
        System.out.println("[MaceCombat] Registered " + MACE_BEHAVIORS.size() + " mace behaviors");
    }
    
    public static void enable() {
        enabled = true;
        System.out.println("[MaceCombat] Enabled - Mace weapon support for Eaglercraft");
    }
    
    public static void disable() {
        enabled = false;
        System.out.println("[MaceCombat] Disabled");
    }
    
    public static boolean isEnabled() {
        return enabled;
    }
    
    public static boolean isMace(ItemStack item) {
        if (item == null || item.isEmpty()) return false;
        // Check if item is mace (ViaItems or vanilla equivalent)
        return item.getTagCompound() != null && 
               item.getTagCompound().getString("via_identifier").equals("mace");
    }
    
    /**
     * Calculate smash attack damage based on fall distance
     */
    public static float calculateSmashDamage(double fallDistance) {
        if (fallDistance < 1.5) return 6.0f; // Base mace damage
        // Damage increases 2 points per block fallen
        return 6.0f + (float) ((fallDistance - 1.5f) * 2f);
    }
    
    /**
     * Apply smash attack effects (knockback, damage)
     */
    private static void applySmashEffect(Player player, float damage) {
        // Create NBT tag for smash attack
        if (player.getMainHandItem().getTagCompound() == null) {
            player.getMainHandItem().setTagCompound(new CompoundTag());
        }
        
        CompoundTag tag = player.getMainHandItem().getTagCompound();
        tag.setFloat("smash_damage", damage);
        tag.setLong("last_smash_time", System.currentTimeMillis());
    }
    
    /**
     * Get the last smash damage value
     */
    public static float getLastSmashDamage(ItemStack mace) {
        if (mace.getTagCompound() == null) return 0.0f;
        return mace.getTagCompound().getFloat("smash_damage");
    }
    
    /**
     * Check if mace has density enchantment (increases damage on smash)
     */
    public static int getDensityLevel(ItemStack mace) {
        if (mace.getTagCompound() == null) return 0;
        return mace.getTagCompound().getInt("enchantment_density");
    }
    
    /**
     * Check if mace has breach enchantment (reduces armor effectiveness)
     */
    public static int getBreachLevel(ItemStack mace) {
        if (mace.getTagCompound() == null) return 0;
        return mace.getTagCompound().getInt("enchantment_breach");
    }
    
    /**
     * Check if mace has wind burst enchantment (launches player up)
     */
    public static int getWindBurstLevel(ItemStack mace) {
        if (mace.getTagCompound() == null) return 0;
        return mace.getTagCompound().getInt("enchantment_wind_burst");
    }
    
    public static void registerBehavior(String name, MaceBehavior behavior) {
        MACE_BEHAVIORS.put(name, behavior);
    }
    
    public static MaceBehavior getBehavior(String name) {
        return MACE_BEHAVIORS.getOrDefault(name, null);
    }
}
