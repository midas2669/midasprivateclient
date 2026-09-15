package eaglercraft.client.viaitems;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class ViaItemsMappings {
    private static final Map<Integer, ItemMapping> ITEM_MAPPINGS = new HashMap<>();
    private static final Map<String, Integer> IDENTIFIER_TO_ID = new HashMap<>();
    
    static {
        // 1.15 Items
        registerItem("bamboo", 668);
        registerItem("bamboo_sapling", 667);
        registerItem("scaffolding", 670);
        registerItem("pumpkin", 669);
        registerItem("composter", 671);
        
        // 1.16 Items
        registerItem("netherite_sword", 750);
        registerItem("netherite_pickaxe", 751);
        registerItem("netherite_axe", 752);
        registerItem("netherite_shovel", 753);
        registerItem("netherite_hoe", 754);
        registerItem("ancient_debris", 755);
        registerItem("netherite_scrap", 756);
        registerItem("netherite_block", 757);
        registerItem("crimson_planks", 758);
        registerItem("warped_planks", 759);
        registerItem("crimson_door", 760);
        registerItem("warped_door", 761);
        registerItem("crimson_trapdoor", 762);
        registerItem("warped_trapdoor", 763);
        registerItem("crimson_fence", 764);
        registerItem("warped_fence", 765);
        registerItem("crimson_fence_gate", 766);
        registerItem("warped_fence_gate", 767);
        registerItem("crimson_stairs", 768);
        registerItem("warped_stairs", 769);
        registerItem("crimson_button", 770);
        registerItem("warped_button", 771);
        registerItem("crimson_pressure_plate", 772);
        registerItem("warped_pressure_plate", 773);
        registerItem("crimson_slab", 774);
        registerItem("warped_slab", 775);
        registerItem("soul_sand", 776);
        registerItem("soul_soil", 777);
        registerItem("basalt", 778);
        registerItem("polished_basalt", 779);
        registerItem("polished_blackstone", 780);
        registerItem("polished_blackstone_brick", 781);
        registerItem("blackstone", 782);
        registerItem("blackstone_slab", 783);
        registerItem("blackstone_stairs", 784);
        registerItem("blackstone_wall", 785);
        registerItem("polished_blackstone_stairs", 786);
        registerItem("polished_blackstone_slab", 787);
        registerItem("polished_blackstone_button", 788);
        registerItem("polished_blackstone_pressure_plate", 789);
        registerItem("polished_blackstone_brick_wall", 790);
        registerItem("polished_blackstone_brick_stairs", 791);
        registerItem("polished_blackstone_brick_slab", 792);
        registerItem("crimson_wood", 793);
        registerItem("warped_wood", 794);
        registerItem("soul_lantern", 795);
        registerItem("soul_torch", 796);
        registerItem("lodestone", 797);
        registerItem("crying_obsidian", 798);
        registerItem("respawn_anchor", 799);
        
        // 1.17 Items
        registerItem("copper_ore", 850);
        registerItem("deepslate_copper_ore", 851);
        registerItem("copper_block", 852);
        registerItem("copper_ingot", 853);
        registerItem("amethyst_block", 854);
        registerItem("amethyst_cluster", 855);
        registerItem("amethyst_shard", 856);
        registerItem("calcite", 857);
        registerItem("tuff", 858);
        registerItem("dripstone_block", 859);
        registerItem("hanging_roots", 860);
        registerItem("azalea", 861);
        registerItem("cave_vines", 862);
        registerItem("cave_vines_plant", 863);
        registerItem("glow_berries", 864);
        registerItem("glow_ink_sac", 865);
        registerItem("glow_item_frame", 866);
        registerItem("tinted_glass", 867);
        registerItem("deepslate", 868);
        registerItem("deepslate_bricks", 869);
        registerItem("deepslate_tile", 870);
        registerItem("sculk_sensor", 871);
        registerItem("sculk_catalyst", 872);
        registerItem("sculk_shrieker", 873);
        registerItem("sculk", 874);
        registerItem("sculk_vein", 875);
        registerItem("powder_snow", 876);
        registerItem("rooted_dirt", 877);
        registerItem("moss_block", 878);
        registerItem("big_dripleaf", 879);
        registerItem("small_dripleaf", 880);
        registerItem("pointed_dripstone", 881);
        registerItem("warped_fungus_on_a_stick", 882);
        registerItem("axolotl_bucket", 883);
        registerItem("goat_horn", 884);
        
        // 1.18 Items
        registerItem("mangrove_log", 950);
        registerItem("mangrove_wood", 951);
        registerItem("stripped_mangrove_log", 952);
        registerItem("stripped_mangrove_wood", 953);
        registerItem("mangrove_planks", 954);
        registerItem("mangrove_leaves", 955);
        registerItem("mangrove_propagule", 956);
        registerItem("frog_spawn", 957);
        registerItem("tadpole_bucket", 958);
        registerItem("allium", 959);
        registerItem("ancient_city", 960);
        
        // 1.19 Items
        registerItem("cherry_log", 1050);
        registerItem("cherry_wood", 1051);
        registerItem("cherry_leaves", 1052);
        registerItem("cherry_sapling", 1053);
        registerItem("cherry_button", 1054);
        registerItem("cherry_door", 1055);
        registerItem("cherry_trapdoor", 1056);
        registerItem("cherry_fence", 1057);
        registerItem("cherry_fence_gate", 1058);
        registerItem("cherry_stairs", 1059);
        registerItem("cherry_slab", 1060);
        registerItem("cherry_pressure_plate", 1061);
        registerItem("sniffer_egg", 1062);
        registerItem("sniffer", 1063);
        registerItem("torchflower", 1064);
        registerItem("torchflower_seeds", 1065);
        
        // 1.20 Items
        registerItem("bamboo_block", 1150);
        registerItem("bamboo_planks", 1151);
        registerItem("bamboo_mosaic", 1152);
        registerItem("bamboo_stairs", 1153);
        registerItem("bamboo_slab", 1154);
        registerItem("bamboo_fence", 1155);
        registerItem("bamboo_fence_gate", 1156);
        registerItem("bamboo_door", 1157);
        registerItem("bamboo_trapdoor", 1158);
        registerItem("bamboo_sign", 1159);
        registerItem("bamboo_hanging_sign", 1160);
        registerItem("bamboo_button", 1161);
        registerItem("bamboo_pressure_plate", 1162);
        registerItem("armor_trim_smithing_template", 1163);
        registerItem("spyglass", 1164);
        registerItem("brush", 1165);
        registerItem("camel_spawn_egg", 1166);
        registerItem("hanging_sign", 1167);
        registerItem("suspicious_sand", 1168);
        registerItem("suspicious_gravel", 1169);
        registerItem("pitcher_pod", 1170);
        registerItem("pitcher_plant", 1171);
        registerItem("torchflower_crop", 1172);
        registerItem("pink_petals", 1173);
        registerItem("ochre_froglight", 1174);
        registerItem("verdant_froglight", 1175);
        registerItem("pearlescent_froglight", 1176);
        
        // 1.21 Items
        registerItem("armadillo_scute", 1250);
        registerItem("armadillo_spawn_egg", 1251);
        registerItem("wind_charge", 1252);
        registerItem("breeze_rod", 1253);
        registerItem("mace", 1254);
        registerItem("trial_spawner", 1255);
        registerItem("vault", 1256);
        registerItem("heavy_core", 1257);
        registerItem("ominous_bottle", 1258);
        registerItem("ominous_trial_spawner", 1259);
    }
    
    private static void registerItem(String identifier, int id) {
        ITEM_MAPPINGS.put(id, new ItemMapping(identifier, id));
        IDENTIFIER_TO_ID.put(identifier, id);
    }
    
    public static ItemMapping getMapping(int itemId) {
        return ITEM_MAPPINGS.getOrDefault(itemId, null);
    }
    
    public static Integer getItemId(String identifier) {
        return IDENTIFIER_TO_ID.getOrDefault(identifier, null);
    }
    
    public static boolean isViaItem(int itemId) {
        return ITEM_MAPPINGS.containsKey(itemId);
    }
    
    public static class ItemMapping {
        private final String identifier;
        private final int itemId;
        private final String displayName;
        
        public ItemMapping(String identifier, int itemId) {
            this.identifier = identifier;
            this.itemId = itemId;
            this.displayName = identifier.replace('_', ' ')
                .replaceAll("([a-z])([A-Z])", "$1 $2");
        }
        
        public String getIdentifier() {
            return identifier;
        }
        
        public int getItemId() {
            return itemId;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}
