package eaglercraft.client.gui;

import eaglercraft.client.input.KeyBindHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModMenuGui extends Screen {
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 150;
    private static final int SPACING = 5;

    private final List<String> modNames = new ArrayList<>();
    private final Map<String, Boolean> modStates = new HashMap<>();
    private int selectedModIndex = -1;
    private final Screen parentScreen;

    public ModMenuGui(Screen parentScreen) {
        super(new StringTextComponent(""));
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
            modStates.putIfAbsent(mod, false);
        }
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (super.mouseClicked(mouseX, mouseY, mouseButton)) {
            return true;
        }
        int buttonX = SPACING;
        int buttonY = SPACING * 3 + 10;
        for (int i = 0; i < modNames.size(); i++) {
            String modName = modNames.get(i);
            int btnY = buttonY + i * (BUTTON_HEIGHT + SPACING);
            if (mouseButton == 0 && mouseX >= buttonX && mouseX <= buttonX + BUTTON_WIDTH
                    && mouseY >= btnY && mouseY <= btnY + BUTTON_HEIGHT) {
                modStates.put(modName, !modStates.getOrDefault(modName, false));
                selectedModIndex = i;
                if (modName.equals("BactroMod")) {
                    KeyBindHandler.onKeyEvent(54, true);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        fill(0, 0, width, height, 0xFF1a1a1a);
        drawCenteredString(font, TextFormatting.BOLD + "Mod Menu", width / 2, SPACING, 0xFFFFAA00);
        drawCenteredString(font, "Click to toggle", width / 2, SPACING + 10, 0xFF999999);
        int buttonX = SPACING;
        int buttonY = SPACING * 3 + 10;
        for (int i = 0; i < modNames.size(); i++) {
            String modName = modNames.get(i);
            boolean enabled = modStates.getOrDefault(modName, false);
            int btnY = buttonY + i * (BUTTON_HEIGHT + SPACING);
            fill(buttonX, btnY, buttonX + BUTTON_WIDTH, btnY + BUTTON_HEIGHT,
                    enabled ? 0xFF1a4d1a : 0xFF4d1a1a);
            drawString(font, modName + (enabled ? " [ON]" : " [OFF]"), buttonX + 5, btnY + 6,
                    enabled ? 0xFF00FF00 : 0xFFFF5555);
            if (i == selectedModIndex) {
                fill(buttonX - 2, btnY - 2, buttonX + BUTTON_WIDTH + 2,
                        btnY + BUTTON_HEIGHT + 2, 0xFFFFAA00);
            }
        }
        drawString(font, "RShift: Cycle | Click BactroMod for settings", SPACING, height - 20, 0xFFAAAAAA);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 54) {
            KeyBindHandler.onKeyEvent(keyCode, true);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
