package eaglercraft.client.viaitems;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class ViaItemsManager {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean initialized = false;
    
    public static void init() {
        if (initialized) return;
        
        ViaItemsRegistry.enable();
        System.out.println("[ViaItems] Manager initialized");
        System.out.println("[ViaItems] Available items: " + ViaItemsMappings.IDENTIFIER_TO_ID.size());
        initialized = true;
    }
    
    public static void shutdown() {
        ViaItemsRegistry.disable();
        initialized = false;
    }
    
    public static ItemStack createViaItem(String identifier, int count) {
        if (!ViaItemsRegistry.isEnabled()) return null;
        
        Integer itemId = ViaItemsMappings.getItemId(identifier);
        if (itemId == null) {
            System.out.println("[ViaItems] Unknown item: " + identifier);
            return null;
        }
        
        ViaItemsMappings.ItemMapping mapping = ViaItemsMappings.getMapping(itemId);
        if (mapping == null) return null;
        
        ItemStack item = new ItemStack(null, count);
        CompoundTag tag = new CompoundTag();
        tag.setString("via_identifier", identifier);
        tag.setInteger("via_item_id", itemId);
        tag.setBoolean("is_via_item", true);
        item.setTagCompound(tag);
        
        ViaItemsRegistry.ViaItemHandler handler = ViaItemsRegistry.getHandler(identifier);
        if (handler != null) {
            handler.onItemCreated(item);
        }
        
        System.out.println("[ViaItems] Created item: " + identifier + " (count: " + count + ")");
        return item;
    }
    
    public static ItemStack createViaItem(String identifier) {
        return createViaItem(identifier, 1);
    }
    
    public static boolean isViaItem(ItemStack item) {
        if (item == null || item.getTagCompound() == null) return false;
        return item.getTagCompound().getBoolean("is_via_item");
    }
    
    public static String getViaItemIdentifier(ItemStack item) {
        if (!isViaItem(item)) return null;
        return item.getTagCompound().getString("via_identifier");
    }
    
    public static void useViaItem(ItemStack item) {
        if (!isViaItem(item)) return;
        
        String identifier = getViaItemIdentifier(item);
        ViaItemsRegistry.ViaItemHandler handler = ViaItemsRegistry.getHandler(identifier);
        if (handler != null) {
            handler.onItemUsed(item);
            System.out.println("[ViaItems] Used item: " + identifier);
        }
    }
    
    public static void listAllViaItems() {
        System.out.println("\n=== Available ViaItems ===");
        System.out.println("Total items: " + ViaItemsMappings.IDENTIFIER_TO_ID.size());
        System.out.println("\nCategories:");
        System.out.println("- 1.15 Items: Bamboo, Scaffolding (4 items)");
        System.out.println("- 1.16 Items: Netherite tools, Crimson/Warped wood, Soul blocks (50+ items)");
        System.out.println("- 1.17 Items: Amethyst, Copper, Sculk, Powder Snow (30+ items)");
        System.out.println("- 1.18 Items: Mangrove, Frogs (10+ items)");
        System.out.println("- 1.19 Items: Cherry wood, Sniffers (15+ items)");
        System.out.println("- 1.20 Items: Bamboo blocks, Brush, Armor trims (30+ items)");
        System.out.println("- 1.21 Items: Armadillo, Mace, Trial Spawner (10+ items)");
        System.out.println("============================\n");
    }
    
    public static boolean isInitialized() {
        return initialized;
    }
}
