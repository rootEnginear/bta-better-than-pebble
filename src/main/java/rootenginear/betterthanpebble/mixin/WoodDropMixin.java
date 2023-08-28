package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.item.Rock;

import java.util.Arrays;
import java.util.Random;

@Mixin(value = {Block.class}, remap = false)
public class WoodDropMixin {
    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void woodBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        Block self = (Block) (Object) this;

        final int[] LOG_IDS = {Block.logOak.id, Block.logPine.id, Block.logBirch.id, Block.logCherry.id, Block.logEucalyptus.id, Block.logOakMossy.id};

        if (Arrays.stream(LOG_IDS).anyMatch(Integer.valueOf(self.id)::equals)) {
            // Cancel wood drop, check via `onWoodDestroyedByPlayer`
            cir.setReturnValue(null);
        }
    }

    @Inject(method = "onBlockDestroyedByPlayer", at = @At("HEAD"))
    private void onWoodDestroyedByPlayer(World world, int x, int y, int z, int meta, EntityPlayer player, Item item, CallbackInfo ci) {
        Block self = (Block) (Object) this;

        final int[] LOG_IDS = {Block.logOak.id, Block.logPine.id, Block.logBirch.id, Block.logCherry.id, Block.logEucalyptus.id, Block.logOakMossy.id};

        if (Arrays.stream(LOG_IDS).anyMatch(Integer.valueOf(self.id)::equals)) {
            ItemStack inHand = player.getCurrentEquippedItem();

            if (inHand != null && ((inHand.getItem() instanceof ItemToolAxe) || (inHand.getItem() instanceof Rock))) {
                // Drop wood if cut by axe or rock
                world.dropItem(x, y, z, new ItemStack(self));
            } else {
                world.dropItem(x, y, z, new ItemStack(Item.stick, new Random().nextInt(5)));
            }
        }
    }
}
