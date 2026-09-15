package eaglercraft.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EaglerBactroMod {
    private static final Map<String, Boolean> FEATURES = new HashMap<>();
    private static final Map<String, Float> FEATURE_VALUES = new HashMap<>();
    
    static {
        FEATURES.put("Fullbright", false);
        FEATURES.put("NightVisionCleanup", false);
        FEATURES.put("PumpkinBlurToggle", false);
        FEATURES.put("LowFire", false);
        FEATURES.put("LowShield", false);
        FEATURES.put("BoatMapVisibility", false);
        FEATURES.put("LavaFog", false);
        FEATURES.put("PowderSnowFog", false);
        FEATURES.put("BlindnessFog", false);
        FEATURES.put("DarknessFog", false);
        FEATURES.put("WaterFog", false);
        FEATURES.put("AtmosphericFog", false);
        FEATURES.put("ItemScaling", false);
        FEATURES.put("RiptideShieldFix", false);
        FEATURES.put("DebugGamemodeSwitch", false);
        
        FEATURE_VALUES.put("FullbrightMultiplier", 2.0f);
        FEATURE_VALUES.put("FireOverlayOffset", -0.3f);
        FEATURE_VALUES.put("ShieldRenderOffset", -0.2f);
        FEATURE_VALUES.put("ItemScale", 1.0f);
    }
    
    public static void init() {
        System.out.println("[BactroMod] Initializing EaglerBactroMod...");
    }
    
    public static boolean isFeatureEnabled(String feature) {
        return FEATURES.getOrDefault(feature, false);
    }
    
    public static void setFeatureEnabled(String feature, boolean enabled) {
        if (FEATURES.containsKey(feature)) {
            FEATURES.put(feature, enabled);
            System.out.println("[BactroMod] " + feature + " = " + enabled);
            applyFeature(feature, enabled);
        }
    }
    
    private static void applyFeature(String feature, boolean enabled) {
        switch (feature) {
            case "Fullbright":
                if (enabled) {
                    System.out.println("[BactroMod] Fullbright enabled: Brightness multiplier = 2.0x");
                } else {
                    System.out.println("[BactroMod] Fullbright disabled");
                }
                break;
            case "NightVisionCleanup":
                System.out.println("[BactroMod] Night Vision cleanup " + (enabled ? "enabled" : "disabled"));
                break;
            case "PumpkinBlurToggle":
                System.out.println("[BactroMod] Pumpkin blur " + (enabled ? "disabled" : "enabled"));
                break;
            case "LowFire":
                System.out.println("[BactroMod] Low fire overlay: offset = -0.3");
                break;
            case "LowShield":
                System.out.println("[BactroMod] Low shield render: offset = -0.2");
                break;
            case "BoatMapVisibility":
                System.out.println("[BactroMod] Boat map visibility " + (enabled ? "enabled" : "disabled"));
                break;
            case "ItemScaling":
                System.out.println("[BactroMod] Item scaling " + (enabled ? "enabled" : "disabled"));
                break;
            case "RiptideShieldFix":
                System.out.println("[BactroMod] Riptide shield fix " + (enabled ? "enabled" : "disabled"));
                break;
            case "DebugGamemodeSwitch":
                System.out.println("[BactroMod] Debug gamemode switch " + (enabled ? "enabled" : "disabled"));
                break;
        }
    }
    
    public static List<String> getAvailableFeatures() {
        return new ArrayList<>(FEATURES.keySet());
    }
    
    public static Map<String, Boolean> getAllFeatures() {
        return new HashMap<>(FEATURES);
    }
    
    public static float getFeatureValue(String valueName) {
        return FEATURE_VALUES.getOrDefault(valueName, 1.0f);
    }
    
    public static void setFeatureValue(String valueName, float value) {
        if (FEATURE_VALUES.containsKey(valueName)) {
            FEATURE_VALUES.put(valueName, value);
            System.out.println("[BactroMod] " + valueName + " = " + value);
        }
    }
    
    public static float getFullbrightMultiplier() {
        return isFeatureEnabled("Fullbright") ? getFeatureValue("FullbrightMultiplier") : 1.0f;
    }
    
    public static float getFireOverlayOffset() {
        return isFeatureEnabled("LowFire") ? getFeatureValue("FireOverlayOffset") : 0.0f;
    }
    
    public static float getShieldRenderOffset() {
        return isFeatureEnabled("LowShield") ? getFeatureValue("ShieldRenderOffset") : 0.0f;
    }
    
    public static boolean shouldRenderNightVisionEffect() {
        return !isFeatureEnabled("NightVisionCleanup");
    }
    
    public static boolean shouldRenderPumpkinBlur() {
        return !isFeatureEnabled("PumpkinBlurToggle");
    }
    
    public static boolean shouldRenderFog(String fogType) {
        switch (fogType) {
            case "lava":
                return !isFeatureEnabled("LavaFog");
            case "powder_snow":
                return !isFeatureEnabled("PowderSnowFog");
            case "blindness":
                return !isFeatureEnabled("BlindnessFog");
            case "darkness":
                return !isFeatureEnabled("DarknessFog");
            case "water":
                return !isFeatureEnabled("WaterFog");
            case "atmospheric":
                return !isFeatureEnabled("AtmosphericFog");
            default:
                return true;
        }
    }
}