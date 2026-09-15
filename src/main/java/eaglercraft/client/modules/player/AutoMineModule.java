package eaglercraft.client.modules.player;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class AutoMineModule {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean isEnabled = false;
    private static float breakSpeed = 1.0f;
    private static BlockPos targetBlock = null;
    private static float blockBreakProgress = 0.0f;
    
    public static void enable() {
        isEnabled = true;
        blockBreakProgress = 0.0f;
        System.out.println("[AutoMine] Enabled");
    }
    
    public static void disable() {
        isEnabled = false;
        blockBreakProgress = 0.0f;
        targetBlock = null;
        System.out.println("[AutoMine] Disabled");
    }
    
    public static void onTick() {
        if (!isEnabled) return;
        
        EntityPlayer player = mc.player;
        if (player == null) return;
        
        RayTraceResult rayTrace = mc.objectMouseOver;
        
        if (rayTrace == null || rayTrace.typeOfHit != RayTraceResult.Type.BLOCK) {
            blockBreakProgress = 0.0f;
            targetBlock = null;
            return;
        }
        
        BlockPos blockPos = rayTrace.getBlockPos();
        IBlockState blockState = mc.world.getBlockState(blockPos);
        
        if (blockState.getBlock() == Blocks.AIR) {
            blockBreakProgress = 0.0f;
            targetBlock = null;
            return;
        }
        
        if (!blockPos.equals(targetBlock)) {
            targetBlock = blockPos;
            blockBreakProgress = 0.0f;
        }
        
        blockBreakProgress += blockState.getPlayerRelativeBlockHardness(player, mc.world, blockPos) * breakSpeed;
        
        if (blockBreakProgress >= 1.0f) {
            mc.playerController.onPlayerDestroyBlock(blockPos);
            blockBreakProgress = 0.0f;
            targetBlock = null;
        }
    }
    
    public static void setBreakSpeed(float speed) {
        breakSpeed = Math.max(0.1f, Math.min(speed, 10.0f));
    }
    
    public static boolean isActive() {
        return isEnabled;
    }
}
