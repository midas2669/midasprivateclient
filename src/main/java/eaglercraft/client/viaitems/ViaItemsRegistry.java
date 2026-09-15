package eaglercraft.client.viaitems;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.HashMap;
import java.util.Map;

public class ViaItemsRegistry {
    private static final Map<String, ViaItemHandler> HANDLERS = new HashMap<>();
    private static boolean enabled = false;
    
    public interface ViaItemHandler {
        void onItemCreated(ItemStack item);
        void onItemUsed(ItemStack item);
        CompoundTag getItemData(ItemStack item);
    }
    
    static {
        registerDefaultHandlers();
    }
    
    private static void registerDefaultHandlers() {
        // Netherite tools
        HANDLERS.put("netherite_sword", new NetheriteTool("netherite_sword"));
        HANDLERS.put("netherite_pickaxe", new NetheriteTool("netherite_pickaxe"));
        HANDLERS.put("netherite_axe", new NetheriteTool("netherite_axe"));
        HANDLERS.put("netherite_shovel", new NetheriteTool("netherite_shovel"));
        HANDLERS.put("netherite_hoe", new NetheriteTool("netherite_hoe"));
        
        // Deep Dark items
        HANDLERS.put("sculk_sensor", new SculkItem("sculk_sensor"));
        HANDLERS.put("sculk_catalyst", new SculkItem("sculk_catalyst"));
        HANDLERS.put("sculk_shrieker", new SculkItem("sculk_shrieker"));
        HANDLERS.put("sculk", new SculkItem("sculk"));
        
        // 1.20 Items
        HANDLERS.put("brush", new BrushItem());
        HANDLERS.put("spyglass", new SpyglassItem());
        
        System.out.println("[ViaItems] Registered " + HANDLERS.size() + " item handlers");
    }
    
    public static void enable() {
        enabled = true;
        System.out.println("[ViaItems] Enabled - Supporting 1.15-1.21.11 items on Eaglercraft 1.14");
    }
    
    public static void disable() {
        enabled = false;
        System.out.println("[ViaItems] Disabled");
    }
    
    public static boolean isEnabled() {
        return enabled;
    }
    
    public static void registerHandler(String identifier, ViaItemHandler handler) {
        HANDLERS.put(identifier, handler);
    }
    
    public static ViaItemHandler getHandler(String identifier) {
        return HANDLERS.getOrDefault(identifier, null);
    }
    
    // Built-in handlers
    private static class NetheriteTool implements ViaItemHandler {
        private String identifier;
        
        NetheriteTool(String identifier) {
            this.identifier = identifier;
        }
        
        @Override
        public void onItemCreated(ItemStack item) {
            System.out.println("[ViaItems] Created netherite tool: " + identifier);
        }
        
        @Override
        public void onItemUsed(ItemStack item) {
            float durability = 2031; // Netherite durability
            if (item.getTagCompound() == null) {
                item.setTagCompound(new CompoundTag());
            }
            item.getTagCompound().setFloat("via_durability", durability);
        }
        
        @Override
        public CompoundTag getItemData(ItemStack item) {
            CompoundTag tag = new CompoundTag();
            tag.setString("identifier", identifier);
            tag.setString("display_name", identifier.replace('_', ' '));
            tag.setFloat("durability", 2031);
            tag.setBoolean("is_via_item", true);
            return tag;
        }
    }
    
    private static class SculkItem implements ViaItemHandler {
        private String identifier;
        
        SculkItem(String identifier) {
            this.identifier = identifier;
        }
        
        @Override
        public void onItemCreated(ItemStack item) {
            System.out.println("[ViaItems] Created sculk item: " + identifier);
        }
        
        @Override
        public void onItemUsed(ItemStack item) {
            // Sculk items vibrate
            if (item.getTagCompound() == null) {
                item.setTagCompound(new CompoundTag());
            }
            item.getTagCompound().setBoolean("vibrating", true);
        }
        
        @Override
        public CompoundTag getItemData(ItemStack item) {
            CompoundTag tag = new CompoundTag();
            tag.setString("identifier", identifier);
            tag.setString("display_name", identifier.replace('_', ' '));
            tag.setBoolean("is_via_item", true);
            tag.setBoolean("vibrates", true);
            return tag;
        }
    }
    
    private static class BrushItem implements ViaItemHandler {
        @Override
        public void onItemCreated(ItemStack item) {
            System.out.println("[ViaItems] Created brush item");
        }
        
        @Override
        public void onItemUsed(ItemStack item) {
            // Brush is used for archaeology
            if (item.getTagCompound() == null) {
                item.setTagCompound(new CompoundTag());
            }
            item.getTagCompound().setString("last_brushed", "suspicious_sand");
        }
        
        @Override
        public CompoundTag getItemData(ItemStack item) {
            CompoundTag tag = new CompoundTag();
            tag.setString("identifier", "brush");
            tag.setString("display_name", "Brush");
            tag.setBoolean("is_via_item", true);
            tag.setString("use_case", "archaeology");
            return tag;
        }
    }
    
    private static class SpyglassItem implements ViaItemHandler {
        @Override
        public void onItemCreated(ItemStack item) {
            System.out.println("[ViaItems] Created spyglass item");
        }
        
        @Override
        public void onItemUsed(ItemStack item) {
            // Spyglass zooms view
            if (item.getTagCompound() == null) {
                item.setTagCompound(new CompoundTag());
            }
            item.getTagCompound().setFloat("zoom_level", 10.0f);
        }
        
        @Override
        public CompoundTag getItemData(ItemStack item) {
            CompoundTag tag = new CompoundTag();
            tag.setString("identifier", "spyglass");
            tag.setString("display_name", "Spyglass");
            tag.setBoolean("is_via_item", true);
            tag.setFloat("zoom", 10.0f);
            return tag;
        }
    }
}
