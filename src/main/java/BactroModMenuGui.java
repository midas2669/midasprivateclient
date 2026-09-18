package eaglercraft.client.gui;

import eaglercraft.client.input.KeyBindHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.Map;

public class BactroModMenuGui extends Screen {
    private static final int BUTTON_HEIGHT = 22;
    private static final int BUTTON_WIDTH = 160;
    private static final int SPACING = 8;
    private final List<String> features;
    private final Map<String, Boolean> featureStates;
    private int selectedIndex = -1;
    private final Screen parentScreen;

    public BactroModMenuGui(Screen parentScreen) {
        super(new StringTextComponent(""));
        this.parentScreen = parentScreen;
        this.features = EaglerBactroMod.getAvailableFeatures();
        this.featureStates = EaglerBactroMod.getAllFeatures();
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
        int buttonY = SPACING * 3 + 20;
        for (int i = 0; i < features.size(); i++) {
            String feature = features.get(i);
            int btnY = buttonY + i * (BUTTON_HEIGHT + SPACING);
            if (mouseButton == 0 && mouseX >= SPACING && mouseX <= SPACING + BUTTON_WIDTH
                    && mouseY >= btnY && mouseY <= btnY + BUTTON_HEIGHT) {
                boolean enabled = featureStates.getOrDefault(feature, false);
                EaglerBactroMod.setFeatureEnabled(feature, !enabled);
                featureStates.put(feature, !enabled);
                selectedIndex = i;
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        fill(0, 0, width, height, 0xFF0d0d0d);
        drawString(font, TextFormatting.BOLD + "BactroMod Settings", SPACING, SPACING, 0xFFFFAA00);
        drawString(font, "Click features to toggle", SPACING, SPACING + 12, 0xFF999999);
        int buttonY = SPACING * 3 + 20;
        for (int i = 0; i < features.size(); i++) {
            String feature = features.get(i);
            boolean enabled = featureStates.getOrDefault(feature, false);
            int btnY = buttonY + i * (BUTTON_HEIGHT + SPACING);
            fill(SPACING, btnY, SPACING + BUTTON_WIDTH, btnY + BUTTON_HEIGHT,
                    enabled ? 0xFF1a4d1a : 0xFF4d1a1a);
            drawString(font, feature + (enabled ? " [ON]" : " [OFF]"), SPACING + 8, btnY + 7,
                    enabled ? 0xFF00FF00 : 0xFFFF4444);
            if (i == selectedIndex) {
                fill(SPACING - 2, btnY - 2, SPACING + BUTTON_WIDTH + 2,
                        btnY + BUTTON_HEIGHT + 2, 0xFFFFAA00);
            }
        }
        drawString(font, TextFormatting.GRAY + "Press RShift to return to Mod Menu", SPACING, height - 25, 0xFF888888);
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
