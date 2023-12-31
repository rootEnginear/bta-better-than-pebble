package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLog;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.item.Rock;

@Mixin(value = {Block.class}, remap = false)
public class WoodStrMixin {
    @Inject(method = "blockStrength", at = @At("HEAD"), cancellable = true)
    private void woodStrength(EntityPlayer player, CallbackInfoReturnable<Float> cir) {
        Block self = (Block) (Object) this;

        if (self instanceof BlockLog) {
            ItemStack inHand = player.getCurrentEquippedItem();

            if (inHand != null && (inHand.getItem() instanceof ItemToolAxe || inHand.getItem() instanceof Rock)) {
                return;
            }

            cir.setReturnValue(1.0F / Block.stone.getHardness() / 100.0F);
        }
    }
}
