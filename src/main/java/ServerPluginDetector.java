package eaglercraft.client.utils;

import java.util.*;

public class ServerPluginDetector {
    private static final Set<String> ANTICHEAT_LIST = Set.of(
        "nocheatplus", "negativity", "warden", "horizon", "illegalstack", 
        "coreprotect", "exploitsx", "vulcan", "abc", "spartan", "kauri", 
        "anticheatreloaded", "witherac", "godseye", "matrix", "wraith", 
        "antixrayheuristics", "grimac", "themis", "foxaddition", 
        "guardianac", "ggintegrity", "lightanticheat", "anarchyexploitfixes", "polar"
    );
    
    private static final Set<String> VERSION_ALIASES = Set.of(
        "version", "ver", "about", "bukkit:version", "bukkit:ver", "bukkit:about"
    );
    
    private final List<String> detectedPlugins = new ArrayList<>();
    private final List<String> commandTreePlugins = new ArrayList<>();
    private String versionAlias;
    private int tickCounter = 0;
    private boolean waitingForResponse = false;
    private static final Random RANDOM = new Random();
    
    public void detectPlugins() {
        detectedPlugins.addAll(commandTreePlugins);
        
        if (versionAlias != null) {
            sendCommandSuggestionPacket(versionAlias + " ");
            waitingForResponse = true;
        } else {
            displayDetectedPlugins();
        }
    }
    
    public void processCommandTree(List<String> commandNodes) {
        commandTreePlugins.clear();
        versionAlias = null;
        
        for (String nodeName : commandNodes) {
            String[] parts = nodeName.split(":");
            if (parts.length > 1) {
                if (!commandTreePlugins.contains(parts[0])) {
                    commandTreePlugins.add(parts[0]);
                }
            }
            
            if (versionAlias == null && VERSION_ALIASES.contains(nodeName)) {
                versionAlias = nodeName;
            }
        }
    }
    
    public void processCommandSuggestions(List<String> suggestions) {
        if (suggestions.isEmpty()) {
            System.err.println("Failed to detect plugins");
            return;
        }
        
        for (String suggestion : suggestions) {
            if (!detectedPlugins.contains(suggestion.toLowerCase())) {
                detectedPlugins.add(suggestion);
            }
        }
        
        displayDetectedPlugins();
    }
    
    public void onTick() {
        if (!waitingForResponse) return;
        
        tickCounter++;
        if (tickCounter >= 100) {
            displayDetectedPlugins();
        }
    }
    
    private void displayDetectedPlugins() {
        if (detectedPlugins.isEmpty()) {
            System.out.println("[PluginDetector] No plugins found on this server.");
            return;
        }
        
        detectedPlugins.sort(String.CASE_INSENSITIVE_ORDER);
        
        List<String> formatted = new ArrayList<>();
        for (String plugin : detectedPlugins) {
            formatted.add(formatPluginName(plugin));
        }
        
        System.out.println("[PluginDetector] Detected " + detectedPlugins.size() + " plugins:");
        System.out.println("[PluginDetector] " + String.join(", ", formatted));
        
        reset();
    }
    
    private String formatPluginName(String name) {
        if (ANTICHEAT_LIST.contains(name.toLowerCase())) {
            return "§c" + name + "§r";
        } else if (name.toLowerCase().contains("exploit") || 
                   name.toLowerCase().contains("cheat") || 
                   name.toLowerCase().contains("illegal")) {
            return "§c" + name + "§r";
        }
        
        return "§6" + name + "§r";
    }
    
    private void sendCommandSuggestionPacket(String command) {
        try {
            int transactionId = RANDOM.nextInt(200);
            System.out.println("[PluginDetector] Requesting command suggestions for: " + command);
        } catch (Exception e) {
            System.err.println("[PluginDetector] Failed to send command packet");
        }
    }
    
    private void reset() {
        waitingForResponse = false;
        tickCounter = 0;
        detectedPlugins.clear();
    }
    
    public List<String> getDetectedPlugins() {
        return new ArrayList<>(detectedPlugins);
    }
    
    public boolean isDetecting() {
        return waitingForResponse;
    }
    
    public void cancel() {
        reset();
    }
}
