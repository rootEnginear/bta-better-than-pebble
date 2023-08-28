package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockGrass;
import net.minecraft.core.block.BlockGrassScorched;
import net.minecraft.core.block.BlockGravel;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.stuct.SedimentData;
import rootenginear.betterthanpebble.utils.SedimentDataLookup;

import java.util.Random;

@Mixin(value = {Block.class, BlockGravel.class, BlockGrass.class, BlockGrassScorched.class}, remap = false)
public class SedimentDropMixin {
    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void sedimentBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        Block self = (Block) (Object) this;

        SedimentData data = SedimentDataLookup.getSedimentData(self);
        if (data != null) {
            Random rand = new Random();
            if (rand.nextFloat() < data.chance) {
                if (data.pickSilkCheck && (dropCause == EnumDropCause.PICK_BLOCK || dropCause == EnumDropCause.SILK_TOUCH))
                    return;
                cir.setReturnValue(new ItemStack[]{new ItemStack(Item.ammoPebble, rand.nextInt(data.maxDrop) + 1)});
            }
        }
    }
}