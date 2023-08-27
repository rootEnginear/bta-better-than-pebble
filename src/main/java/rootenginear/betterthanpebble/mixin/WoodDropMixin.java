package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.achievement.stat.StatList;
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
    @Inject(method = "harvestBlock", at = @At("HEAD"), cancellable = true)
    private void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfo ci) {
        Block self = (Block) (Object) this;

        int[] logIDs = {Block.logOak.id, Block.logPine.id, Block.logBirch.id, Block.logCherry.id, Block.logEucalyptus.id, Block.logOakMossy.id};

        if (Arrays.stream(logIDs).anyMatch(Integer.valueOf(self.id)::equals)) {
            ItemStack heldItemStack = entityplayer.inventory.getCurrentItem();
            Item heldItem = heldItemStack != null ? Item.itemsList[heldItemStack.itemID] : null;

            // If the player's hand is empty OR don't has proper tools
            if (!(heldItem instanceof ItemToolAxe) && !(heldItem instanceof Rock)) {
                entityplayer.addStat(StatList.mineBlockStatArray[self.id], 1);

                // dropBlockWithCause
                if (!world.isClientSide) {
                    ItemStack[] drops = new ItemStack[]{new ItemStack(Item.stick, new Random().nextInt(5))};
                    for (ItemStack drop : drops) {
                        for (int i = 0; i < drop.stackSize; ++i) {
                            world.dropItem(x, y, z, new ItemStack(drop.itemID, 1, drop.getMetadata(), drop.tag));
                        }
                    }
                }

                ci.cancel();
            }
        }
    }

    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void stoneBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        switch (dropCause) {
            case WORLD:
            case EXPLOSION:
            case IMPROPER_TOOL:
                cir.setReturnValue(new ItemStack[]{new ItemStack(Item.stick, new Random().nextInt(5))});
        }
    }
}
