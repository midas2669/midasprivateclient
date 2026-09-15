package eaglercraft.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.util.*;

public class ModMenuGui extends GuiScreen {
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 150;
    private static final int SIDEBAR_WIDTH = 200;
    private static final int SPACING = 5;
    
    private List<ModToggleButton> modButtons = new ArrayList<>();
    private int scrollOffset = 0;
    private ModToggleButton selectedMod = null;
    private static final Map<String, Boolean> MOD_STATES = new HashMap<>();
    
    private GuiScreen parentScreen;
    
    public ModMenuGui(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
        initializeMods();
    }
    
    private void initializeMods() {
        modButtons.clear();
        
        modButtons.add(new ModToggleButton("Flight", 0));
        modButtons.add(new ModToggleButton("Speed", 1));
        modButtons.add(new ModToggleButton("AutoMine", 2));
        modButtons.add(new ModToggleButton("ESP", 3));
        modButtons.add(new ModToggleButton("AutoClicker", 4));
        modButtons.add(new ModToggleButton("NoFall", 5));
        modButtons.add(new ModToggleButton("Reach", 6));
        modButtons.add(new ModToggleButton("Step", 7));
        modButtons.add(new ModToggleButton("FullBright", 8));
        modButtons.add(new ModToggleButton("Xray", 9));
        
        for (ModToggleButton btn : modButtons) {
            if (!MOD_STATES.containsKey(btn.name)) {
                MOD_STATES.put(btn.name, false);
            }
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        
        int buttonX = SPACING;
        int buttonY = SPACING;
        
        for (int i = 0; i < modButtons.size(); i++) {
            ModToggleButton btn = modButtons.get(i);
            
            if (mouseX >= buttonX && mouseX <= buttonX + BUTTON_WIDTH && 
                mouseY >= buttonY && mouseY <= buttonY + BUTTON_HEIGHT) {
                
                if (mouseButton == 0) {
                    MOD_STATES.put(btn.name, !MOD_STATES.get(btn.name));
                    selectedMod = btn;
                }
            }
            
            buttonY += BUTTON_HEIGHT + SPACING;
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        
        drawRect(0, 0, this.width, this.height, 0xFF1a1a1a);
        
        drawString(this.fontRendererObj, EnumChatFormatting.BOLD + "Mod Menu", 
                   SPACING, SPACING, 0xFFFFFF);
        
        int buttonX = SPACING;
        int buttonY = SPACING * 3 + 10;
        
        for (ModToggleButton btn : modButtons) {
            boolean enabled = MOD_STATES.get(btn.name);
            int bgColor = enabled ? 0xFF00AA00 : 0xFF550000;
            int textColor = enabled ? 0xFF00FF00 : 0xFFFF0000;
            
            drawRect(buttonX, buttonY, buttonX + BUTTON_WIDTH, 
                    buttonY + BUTTON_HEIGHT, bgColor);
            
            String text = btn.name + (enabled ? " [ON]" : " [OFF]");
            drawString(this.fontRendererObj, text, 
                      buttonX + 5, buttonY + 6, textColor);
            
            buttonY += BUTTON_HEIGHT + SPACING;
        }
        
        if (selectedMod != null) {
            drawRect(SIDEBAR_WIDTH, SPACING, this.width - SPACING, 
                    this.height - SPACING, 0xFF2a2a2a);
            
            drawString(this.fontRendererObj, EnumChatFormatting.BOLD + selectedMod.name, 
                      SIDEBAR_WIDTH + 10, SPACING * 2, 0xFFFFAA00);
            
            boolean enabled = MOD_STATES.get(selectedMod.name);
            String status = enabled ? EnumChatFormatting.GREEN + "ENABLED" : 
                                     EnumChatFormatting.RED + "DISABLED";
            drawString(this.fontRendererObj, "Status: " + status, 
                      SIDEBAR_WIDTH + 10, SPACING * 4 + 10, 0xFFFFFFFF);
        }
        
        drawString(this.fontRendererObj, "Press RShift to Close", 
                  SPACING, this.height - 20, 0xFFAAAAAA);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 54) {
            this.mc.displayGuiScreen(parentScreen);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public static boolean isModEnabled(String modName) {
        return MOD_STATES.getOrDefault(modName, false);
    }
    
    public static void setModEnabled(String modName, boolean enabled) {
        MOD_STATES.put(modName, enabled);
    }
    
    private static class ModToggleButton {
        String name;
        int id;
        
        ModToggleButton(String name, int id) {
            this.name = name;
            this.id = id;
        }
    }
}
