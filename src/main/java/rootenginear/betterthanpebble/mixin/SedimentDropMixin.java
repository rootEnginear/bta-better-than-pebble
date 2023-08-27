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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.utils.SedimentData;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Mixin(value = {Block.class, BlockGravel.class, BlockGrass.class, BlockGrassScorched.class}, remap = false)
public class SedimentDropMixin {

    @Unique
    private static final float gravelFlintChance = 39.8F;
    @Unique
    private static final float chanceByFlint = 1.0F / gravelFlintChance / 10.0F;

    @Unique
    private static final float sandChance = 18.0F * chanceByFlint;
    @Unique
    private static final float gravelChance = 20.0F * chanceByFlint;
    @Unique
    private static final float scorchedDirtChance = 3.0F * chanceByFlint;
    @Unique
    private static final float dirtChance = 60.2F * chanceByFlint;

    @Unique
    private static final Map<Integer, SedimentData> sedimentLookup = new HashMap<>();

    static {
        sedimentLookup.put(Block.sand.id, new SedimentData(sandChance, false, 4));
        sedimentLookup.put(Block.gravel.id, new SedimentData(gravelChance, true, 5));
        sedimentLookup.put(Block.dirtScorched.id, new SedimentData(scorchedDirtChance, false, 4));
        sedimentLookup.put(Block.dirtScorchedRich.id, new SedimentData(scorchedDirtChance, false, 4));
        sedimentLookup.put(Block.grassScorched.id, new SedimentData(scorchedDirtChance, true, 4));
        sedimentLookup.put(Block.dirt.id, new SedimentData(dirtChance, false, 2));
        sedimentLookup.put(Block.grassRetro.id, new SedimentData(dirtChance, false, 2));
        sedimentLookup.put(Block.grass.id, new SedimentData(dirtChance, true, 2));
    }

    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void sedimentBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        Block self = (Block) (Object) this;
        float rand = new Random().nextFloat();

        SedimentData data = sedimentLookup.get(self.id);
        if (data != null && rand < data.chance) {
            if (data.pickSilkCheck && (dropCause == EnumDropCause.PICK_BLOCK || dropCause == EnumDropCause.SILK_TOUCH))
                return;
            cir.setReturnValue(new ItemStack[]{new ItemStack(Item.ammoPebble, new Random().nextInt(data.maxDrop) + 1)});
        }
    }
}