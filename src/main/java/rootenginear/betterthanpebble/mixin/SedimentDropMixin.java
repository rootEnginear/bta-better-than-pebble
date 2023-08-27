package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockGrass;
import net.minecraft.core.block.BlockGrassScorched;
import net.minecraft.core.block.BlockGravel;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.logic.SedimentDropLogic;

@Mixin(value = {Block.class, BlockGravel.class, BlockGrass.class, BlockGrassScorched.class}, remap = false)
public class SedimentDropMixin {
    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void sedimentBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        Block self = (Block) (Object) this;
        SedimentDropLogic.sedimentBreakResultLogic(self, world, dropCause, x, y, z, meta, tileEntity, cir);
    }
}