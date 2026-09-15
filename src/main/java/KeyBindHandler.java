package eaglercraft.client.input;

import eaglercraft.client.gui.BactroModMenuGui;
import eaglercraft.client.gui.ModMenuGui;
import net.minecraft.client.Minecraft;

public class KeyBindHandler {
    private static boolean rightShiftPressed = false;
    private static boolean menuOpen = false;
    private static int menuState = 0;
    private static final int STATE_CLOSED = 0;
    private static final int STATE_MOD_MENU = 1;
    private static final int STATE_BACTRO_MENU = 2;
    
    public static void onKeyEvent(int keyCode, boolean pressed) {
        if (keyCode == 54) {
            if (pressed && !rightShiftPressed) {
                rightShiftPressed = true;
                toggleMenu();
            }
            if (!pressed) {
                rightShiftPressed = false;
            }
        }
    }
    
    private static void toggleMenu() {
        Minecraft mc = Minecraft.getMinecraft();
        
        if (menuState == STATE_CLOSED) {
            mc.displayGuiScreen(new ModMenuGui(null));
            menuState = STATE_MOD_MENU;
        } else if (menuState == STATE_MOD_MENU) {
            mc.displayGuiScreen(new BactroModMenuGui(new ModMenuGui(null)));
            menuState = STATE_BACTRO_MENU;
        } else if (menuState == STATE_BACTRO_MENU) {
            mc.displayGuiScreen(null);
            menuState = STATE_CLOSED;
        }
    }
    
    public static void resetMenuState() {
        menuState = STATE_CLOSED;
    }
    
    public static int getMenuState() {
        return menuState;
    }
}