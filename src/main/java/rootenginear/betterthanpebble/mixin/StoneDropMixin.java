package rootenginear.betterthanpebble.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.block.BlockStone;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.item.Rock;

import java.util.Random;

@Mixin(value = {BlockStone.class}, remap = false)
public class StoneDropMixin {
    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void stoneDropCheck(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        switch (dropCause) {
            case WORLD:
            case EXPLOSION:
                cir.setReturnValue(new ItemStack[]{new ItemStack(31415, new Random().nextInt(2) + 1, 0)});
                break;
            case PROPER_TOOL:
                EntityPlayer player = Minecraft.getMinecraft(Minecraft.class).thePlayer;
                ItemStack inHand = player.getCurrentEquippedItem();

                if (inHand != null && inHand.getItem() instanceof Rock) {
                    cir.setReturnValue(new ItemStack[]{new ItemStack(31415, new Random().nextInt(2) + 1, 0)});
                }
        }
    }
}
