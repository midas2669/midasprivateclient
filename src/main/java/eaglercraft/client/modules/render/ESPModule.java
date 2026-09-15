package eaglercraft.client.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ESPModule {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean isEnabled = false;
    private static boolean showPlayers = true;
    private static boolean showMobs = true;
    private static boolean showAnimals = false;
    private static float boxAlpha = 0.5f;
    
    public static void enable() {
        isEnabled = true;
        System.out.println("[ESP] Enabled");
    }
    
    public static void disable() {
        isEnabled = false;
        System.out.println("[ESP] Disabled");
    }
    
    public static void onRender() {
        if (!isEnabled || mc.world == null) return;
        
        for (EntityLivingBase entity : mc.world.loadedEntityList.stream()
            .filter(e -> e instanceof EntityLivingBase)
            .map(e -> (EntityLivingBase) e)
            .toArray(EntityLivingBase[]::new)) {
            
            if (entity == mc.player) continue;
            
            boolean isPlayer = entity instanceof net.minecraft.entity.player.EntityPlayer;
            boolean isMob = !isPlayer && entity.isCreatureType(net.minecraft.entity.EnumCreatureType.MONSTER, false);
            boolean isAnimal = !isPlayer && entity.isCreatureType(net.minecraft.entity.EnumCreatureType.CREATURE, false);
            
            if ((isPlayer && !showPlayers) || (isMob && !showMobs) || (isAnimal && !showAnimals)) {
                continue;
            }
            
            drawEntityBox(entity);
        }
    }
    
    private static void drawEntityBox(EntityLivingBase entity) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0f, 0.0f, 0.0f, boxAlpha);
        GL11.glLineWidth(2.0f);
        
        double x = entity.posX - entity.width / 2;
        double y = entity.posY;
        double z = entity.posZ - entity.width / 2;
        double w = entity.width;
        double h = entity.height;
        
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x + w, y, z);
        GL11.glVertex3d(x + w, y + h, z);
        GL11.glVertex3d(x, y + h, z);
        GL11.glEnd();
        
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }
    
    public static void setShowPlayers(boolean show) {
        showPlayers = show;
    }
    
    public static void setShowMobs(boolean show) {
        showMobs = show;
    }
    
    public static void setShowAnimals(boolean show) {
        showAnimals = show;
    }
    
    public static boolean isActive() {
        return isEnabled;
    }
}
