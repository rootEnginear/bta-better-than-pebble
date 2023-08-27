package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.item.Rock;

import java.util.Arrays;

@Mixin(value = {Block.class}, remap = false)
public class WoodStrMixin {
    @Inject(method = "blockStrength", at = @At("HEAD"), cancellable = true)
    private void woodStrength(EntityPlayer player, CallbackInfoReturnable<Float> cir) {
        Block self = (Block) (Object) this;

        int[] logIDs = {Block.logOak.id, Block.logPine.id, Block.logBirch.id, Block.logCherry.id, Block.logEucalyptus.id, Block.logOakMossy.id};

        if (Arrays.stream(logIDs).anyMatch(Integer.valueOf(self.id)::equals)) {
            ItemStack inHand = player.getCurrentEquippedItem();

            if (inHand != null && (inHand.getItem() instanceof ItemToolAxe || inHand.getItem() instanceof Rock)) {
                return;
            }

            cir.setReturnValue(1.0F / Block.stone.getHardness() / 100.0F);
        }
    }
}
