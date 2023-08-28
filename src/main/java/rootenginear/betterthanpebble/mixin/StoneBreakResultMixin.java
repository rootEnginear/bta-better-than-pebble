package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.BlockStone;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.BetterThanPebble;

import java.util.Random;

@Mixin(value = {BlockStone.class}, remap = false)
public class StoneBreakResultMixin {
    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void stoneBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        switch (dropCause) {
            case WORLD:
            case EXPLOSION:
                cir.setReturnValue(new ItemStack[]{new ItemStack(BetterThanPebble.rockItem, new Random().nextInt(2) + 1)});
                break;
            case PROPER_TOOL:
                // Cancel proper stone drop, check via `onStoneDestroyedByPlayer`
                cir.setReturnValue(null);
        }
    }
}
