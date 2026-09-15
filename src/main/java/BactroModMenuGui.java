package eaglercraft.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BactroModMenuGui extends GuiScreen {
    private static final int BUTTON_HEIGHT = 22;
    private static final int BUTTON_WIDTH = 160;
    private static final int SPACING = 8;
    
    private List<String> features;
    private Map<String, Boolean> featureStates;
    private int scrollOffset = 0;
    private int selectedIndex = -1;
    private GuiScreen parentScreen;
    
    public BactroModMenuGui(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
        this.features = EaglerBactroMod.getAvailableFeatures();
        this.featureStates = EaglerBactroMod.getAllFeatures();
    }
    
    @Override
    public void initGui() {
        super.initGui();
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        
        int buttonY = SPACING * 3 + 20;
        
        for (int i = 0; i < features.size(); i++) {
            String feature = features.get(i);
            int btnX = SPACING;
            int btnY = buttonY + (i * (BUTTON_HEIGHT + SPACING));
            
            if (mouseX >= btnX && mouseX <= btnX + BUTTON_WIDTH && 
                mouseY >= btnY && mouseY <= btnY + BUTTON_HEIGHT) {
                
                if (mouseButton == 0) {
                    boolean current = featureStates.get(feature);
                    EaglerBactroMod.setFeatureEnabled(feature, !current);
                    featureStates.put(feature, !current);
                    selectedIndex = i;
                }
            }
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(0, 0, this.width, this.height, 0xFF0d0d0d);
        
        drawString(this.fontRendererObj, EnumChatFormatting.BOLD + "BactroMod Settings", 
                   SPACING, SPACING, 0xFFFFAA00);
        drawString(this.fontRendererObj, "Click to toggle features", 
                   SPACING, SPACING + 12, 0xFF999999);
        
        int buttonY = SPACING * 3 + 20;
        
        for (int i = 0; i < features.size(); i++) {
            String feature = features.get(i);
            boolean enabled = featureStates.get(feature);
            
            int btnX = SPACING;
            int btnY = buttonY + (i * (BUTTON_HEIGHT + SPACING));
            int bgColor = enabled ? 0xFF1a4d1a : 0xFF4d1a1a;
            int textColor = enabled ? 0xFF00FF00 : 0xFFFF4444;
            
            drawRect(btnX, btnY, btnX + BUTTON_WIDTH, btnY + BUTTON_HEIGHT, bgColor);
            
            String status = enabled ? "[ON]" : "[OFF]";
            drawString(this.fontRendererObj, feature + " " + status, 
                      btnX + 8, btnY + 7, textColor);
            
            if (i == selectedIndex) {
                drawRect(btnX - 2, btnY - 2, btnX + BUTTON_WIDTH + 2, 
                        btnY + BUTTON_HEIGHT + 2, 0xFFFFAA00);
            }
        }
        
        drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "Press RShift to Close", 
                  SPACING, this.height - 25, 0xFF888888);
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
}
