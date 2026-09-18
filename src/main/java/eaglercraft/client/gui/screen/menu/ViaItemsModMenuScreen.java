package eaglercraft.client.gui.screen.menu;

import eaglercraft.client.modules.combat.MaceAttributeSwap;
import eaglercraft.client.modules.combat.MaceCombatModule;
import eaglercraft.client.modules.combat.MaceCriticals;
import eaglercraft.client.modules.combat.MaceHitboxes;
import eaglercraft.client.viaitems.ViaItemsManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class ViaItemsModMenuScreen extends Screen {
    private final Screen parentScreen;
    private final int buttonSpacing = 25;
    private int buttonY = 40;

    public ViaItemsModMenuScreen(Screen parent) {
        super(new StringTextComponent(""));
        this.parentScreen = parent;
    }

    @Override
    protected void init() {
        buttons.clear();
        addButton(new Button(10, buttonY, 200, 20,
                "ViaItems: " + (ViaItemsManager.isInitialized() ? "ENABLED" : "DISABLED"), button -> {
            if (ViaItemsManager.isInitialized()) ViaItemsManager.shutdown(); else ViaItemsManager.init();
            init();
        }));
        buttonY += buttonSpacing;
        addButton(new Button(10, buttonY, 200, 20, "List ViaItems (260+)", button -> ViaItemsManager.listAllViaItems()));
        buttonY += buttonSpacing;
        addButton(new Button(10, buttonY, 200, 20, "Create Test Mace", button -> {
            if (ViaItemsManager.isInitialized()) {
                ViaItemsManager.createViaItem("mace", 1);
                if (mc.player != null) mc.player.addChatMessage("§6[ViaItems] Created Mace!");
            }
        }));
        buttonY += buttonSpacing + 10;
        addButton(new Button(10, buttonY, 200, 20,
                "Mace Combat: " + (MaceCombatModule.isEnabled() ? "ENABLED" : "DISABLED"), button -> {
            if (MaceCombatModule.isEnabled()) MaceCombatModule.disable(); else MaceCombatModule.enable();
            init();
        }));
        buttonY += buttonSpacing;
        addButton(new Button(10, buttonY, 200, 20,
                "Smash Attack: " + (MaceCriticals.isEnabled() ? "ENABLED" : "DISABLED"), button -> {
            if (MaceCriticals.isEnabled()) MaceCriticals.disable(); else MaceCriticals.enable();
            init();
        }));
        buttonY += buttonSpacing;
        addButton(new Button(10, buttonY, 200, 20,
                "Attribute Swap: " + (MaceAttributeSwap.isMaceSwappingEnabled() ? "ENABLED" : "DISABLED"), button -> {
            MaceAttributeSwap.setMaceSwappingEnabled(!MaceAttributeSwap.isMaceSwappingEnabled());
            init();
        }));
        buttonY += buttonSpacing;
        addButton(new Button(10, buttonY, 200, 20, "Mace Hitboxes: ENABLED", button -> {
            if (MaceHitboxes.isEnabled()) MaceHitboxes.disable(); else MaceHitboxes.enable();
            init();
        }));
        buttonY += buttonSpacing + 10;
        addButton(new Button(10, height - 30, 200, 20, "Back", button -> mc.displayGuiScreen(parentScreen)));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        drawCenteredString(font, "ViaItems & Mace Combat", width / 2, 10, 0xFFFFFF);
        drawString(font, "ViaItems", 10, buttonY - 60, 0xABEDFF);
        drawString(font, "Mace Combat", 10, buttonY - 15, 0xFFABED);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
