package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.item.Rock;

import java.util.Arrays;

@Mixin(value = Block.class, remap = false)
public class StoneStrMixin {
    @Inject(method = "blockStrength", at = @At("HEAD"), cancellable = true)
    private void stoneHardness(EntityPlayer player, CallbackInfoReturnable<Float> cir) {
        Block self = (Block) (Object) this;

        int[] logIDs = {Block.stone.id, Block.mossStone.id, Block.limestone.id, Block.granite.id};

        if (Arrays.stream(logIDs).anyMatch(Integer.valueOf(self.id)::equals)) {
            ItemStack inHand = player.getCurrentEquippedItem();

            if (inHand != null && inHand.getItem() instanceof Rock) {
                cir.setReturnValue(1.0F / self.getHardness() / 60.0F);
            }
        }
    }
}
