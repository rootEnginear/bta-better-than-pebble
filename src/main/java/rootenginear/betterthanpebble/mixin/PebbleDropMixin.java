package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = Block.class, remap = false)
public class PebbleDropMixin {

    @Unique
    private final float gravelFlintChance = 39.8F;
    @Unique
    private final float sandChance = 18.0F;
    @Unique
    private final float gravelChance = 20.0F;
    @Unique
    private final float richDirtChance = 3.0F;
    @Unique
    private final float dirtChance = 60.2F;
    @Unique
    private final float chanceByFlint = 1.0F / gravelFlintChance / 10.0F;

    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void pebbleDropCheck(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        Block self = (Block) (Object) this;
        float rand = new Random().nextFloat();

        if (self.id == Block.sand.id && rand < sandChance * chanceByFlint) {
            cir.setReturnValue(new ItemStack[]{new ItemStack(Item.ammoPebble, new Random().nextInt(4) + 1)});
            return;
        }

        if (self.id == Block.gravel.id && rand < gravelChance * chanceByFlint) {
            cir.setReturnValue(new ItemStack[]{new ItemStack(Item.ammoPebble, new Random().nextInt(5) + 1)});
            return;
        }

        if ((self.id == Block.dirtScorched.id || self.id == Block.dirtScorchedRich.id) && rand < richDirtChance * chanceByFlint) {
            cir.setReturnValue(new ItemStack[]{new ItemStack(Item.ammoPebble, new Random().nextInt(4) + 1)});
            return;
        }

        if ((self.id == Block.dirt.id || self.id == Block.grass.id || self.id == Block.grassRetro.id) && rand < dirtChance * chanceByFlint) {
            cir.setReturnValue(new ItemStack[]{new ItemStack(Item.ammoPebble, new Random().nextInt(2) + 1)});
        }
    }
}