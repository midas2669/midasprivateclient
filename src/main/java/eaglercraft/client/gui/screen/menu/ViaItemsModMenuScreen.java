package eaglercraft.client.gui.screen.menu;

import eaglercraft.client.viaitems.ViaItemsManager;
import eaglercraft.client.modules.combat.MaceCombatModule;
import eaglercraft.client.modules.combat.MaceAttributeSwap;
import eaglercraft.client.modules.combat.MaceCriticals;
import eaglercraft.client.modules.combat.MaceHitboxes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

/**
 * ModMenu Integration Screen
 * Displays ViaItems and Mace Combat options
 */
public class ViaItemsModMenuScreen extends GuiScreen {
    private GuiScreen parentScreen;
    private int buttonSpacing = 25;
    private int buttonY = 40;
    
    // ViaItems section
    private GuiButton toggleViaItemsBtn;
    private GuiButton listItemsBtn;
    private GuiButton createTestItemBtn;
    
    // Mace Combat section
    private GuiButton toggleMaceCombatBtn;
    private GuiButton toggleSmashAttackBtn;
    private GuiButton toggleAttributeSwapBtn;
    private GuiButton toggleHitboxesBtn;
    
    public ViaItemsModMenuScreen(GuiScreen parent) {
        this.parentScreen = parent;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        
        // ViaItems Buttons
        int id = 0;
        this.buttonList.add(toggleViaItemsBtn = new GuiButton(id++, 10, buttonY, 200, 20, 
            "ViaItems: " + (ViaItemsManager.isInitialized() ? "ENABLED" : "DISABLED")));
        buttonY += buttonSpacing;
        
        this.buttonList.add(listItemsBtn = new GuiButton(id++, 10, buttonY, 200, 20, "List ViaItems (260+)"));
        buttonY += buttonSpacing;
        
        this.buttonList.add(createTestItemBtn = new GuiButton(id++, 10, buttonY, 200, 20, "Create Test Mace"));
        buttonY += buttonSpacing + 10;
        
        // Mace Combat Buttons
        this.buttonList.add(toggleMaceCombatBtn = new GuiButton(id++, 10, buttonY, 200, 20,
            "Mace Combat: " + (MaceCombatModule.isEnabled() ? "ENABLED" : "DISABLED")));
        buttonY += buttonSpacing;
        
        this.buttonList.add(toggleSmashAttackBtn = new GuiButton(id++, 10, buttonY, 200, 20,
            "Smash Attack: " + (MaceCriticals.isEnabled() ? "ENABLED" : "DISABLED")));
        buttonY += buttonSpacing;
        
        this.buttonList.add(toggleAttributeSwapBtn = new GuiButton(id++, 10, buttonY, 200, 20,
            "Attribute Swap: " + (MaceAttributeSwap.isMaceSwappingEnabled() ? "ENABLED" : "DISABLED")));
        buttonY += buttonSpacing;
        
        this.buttonList.add(toggleHitboxesBtn = new GuiButton(id++, 10, buttonY, 200, 20,
            "Mace Hitboxes: ENABLED"));
        buttonY += buttonSpacing + 10;
        
        // Back button
        this.buttonList.add(new GuiButton(id++, 10, this.height - 30, 200, 20, "Back"));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == toggleViaItemsBtn) {
            if (ViaItemsManager.isInitialized()) {
                ViaItemsManager.shutdown();
            } else {
                ViaItemsManager.init();
            }
            this.initGui();
        }
        else if (button == listItemsBtn) {
            ViaItemsManager.listAllViaItems();
        }
        else if (button == createTestItemBtn) {
            if (ViaItemsManager.isInitialized()) {
                ViaItemsManager.createViaItem("mace", 1);
                this.mc.player.addChatMessage("\u00a76[ViaItems] Created Mace!");
            }
        }
        else if (button == toggleMaceCombatBtn) {
            if (MaceCombatModule.isEnabled()) {
                MaceCombatModule.disable();
            } else {
                MaceCombatModule.enable();
            }
            this.initGui();
        }
        else if (button == toggleSmashAttackBtn) {
            if (MaceCriticals.isEnabled()) {
                MaceCriticals.disable();
            } else {
                MaceCriticals.enable();
            }
            this.initGui();
        }
        else if (button == toggleAttributeSwapBtn) {
            boolean current = MaceAttributeSwap.isMaceSwappingEnabled();
            MaceAttributeSwap.setMaceSwappingEnabled(!current);
            this.initGui();
        }
        else if (button == toggleHitboxesBtn) {
            boolean current = MaceHitboxes.isEnabled();
            if (current) {
                MaceHitboxes.disable();
            } else {
                MaceHitboxes.enable();
            }
            this.initGui();
        }
        else if (button.id == this.buttonList.size() - 1) {
            // Back button
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    @Override
    public void drawScreen(int x, int y, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "ViaItems & Mace Combat", this.width / 2, 10, 0xFFFFFF);
        
        this.drawString(this.fontRendererObj, "ViaItems", 10, buttonY - 60, 0xABEDFF);
        this.drawString(this.fontRendererObj, "Mace Combat", 10, buttonY - 15, 0xFFABED);
        
        super.drawScreen(x, y, f);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
