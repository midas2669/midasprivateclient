package eaglercraft.client.gui;

import eaglercraft.client.input.KeyBindHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.util.*;

public class ModMenuGui extends GuiScreen {
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 150;
    private static final int SPACING = 5;
    
    private List<String> modNames = new ArrayList<>();
    private Map<String, Boolean> modStates = new HashMap<>();
    private int selectedModIndex = -1;
    private GuiScreen parentScreen;
    
    public ModMenuGui(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
        initializeMods();
    }
    
    private void initializeMods() {
        modNames.clear();
        
        modNames.add("Flight");
        modNames.add("Speed");
        modNames.add("AutoMine");
        modNames.add("ESP");
        modNames.add("AutoClicker");
        modNames.add("NoFall");
        modNames.add("Reach");
        modNames.add("Step");
        modNames.add("FullBright");
        modNames.add("Xray");
        modNames.add("BactroMod");
        modNames.add("ServerInfo");
        
        for (String mod : modNames) {
            if (!modStates.containsKey(mod)) {
                modStates.put(mod, false);
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
        int buttonY = SPACING * 3 + 10;
        
        for (int i = 0; i < modNames.size(); i++) {
            String modName = modNames.get(i);
            int btnY = buttonY + (i * (BUTTON_HEIGHT + SPACING));
            
            if (mouseX >= buttonX && mouseX <= buttonX + BUTTON_WIDTH && 
                mouseY >= btnY && mouseY <= btnY + BUTTON_HEIGHT) {
                
                if (mouseButton == 0) {
                    modStates.put(modName, !modStates.get(modName));
                    selectedModIndex = i;
                    
                    if (modName.equals("BactroMod")) {
                        KeyBindHandler.onKeyEvent(54, true);
                    }
                }
            }
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        
        drawRect(0, 0, this.width, this.height, 0xFF1a1a1a);
        
        drawString(this.fontRendererObj, EnumChatFormatting.BOLD + "Mod Menu", 
                   SPACING, SPACING, 0xFFFFAA00);
        drawString(this.fontRendererObj, "Click to toggle", 
                   SPACING, SPACING + 10, 0xFF999999);
        
        int buttonX = SPACING;
        int buttonY = SPACING * 3 + 10;
        
        for (int i = 0; i < modNames.size(); i++) {
            String modName = modNames.get(i);
            boolean enabled = modStates.get(modName);
            int bgColor = enabled ? 0xFF1a4d1a : 0xFF4d1a1a;
            int textColor = enabled ? 0xFF00FF00 : 0xFFFF5555;
            
            int btnY = buttonY + (i * (BUTTON_HEIGHT + SPACING));
            drawRect(buttonX, btnY, buttonX + BUTTON_WIDTH, btnY + BUTTON_HEIGHT, bgColor);
            
            String status = enabled ? "[ON]" : "[OFF]";
            drawString(this.fontRendererObj, modName + " " + status, 
                      buttonX + 5, btnY + 6, textColor);
            
            if (i == selectedModIndex) {
                drawRect(buttonX - 2, btnY - 2, buttonX + BUTTON_WIDTH + 2, 
                        btnY + BUTTON_HEIGHT + 2, 0xFFFFAA00);
            }
        }
        
        drawString(this.fontRendererObj, "RShift: Cycle | Click BactroMod for settings", 
                  SPACING, this.height - 20, 0xFFAAAAAA);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 54) {
            KeyBindHandler.onKeyEvent(54, true);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}