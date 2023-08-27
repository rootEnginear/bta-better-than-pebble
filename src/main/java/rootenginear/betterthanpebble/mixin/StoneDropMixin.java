package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.achievement.stat.StatList;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockStone;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.ItemToolShears;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.BetterThanPebble;

import java.util.Random;

@Mixin(value = {BlockStone.class}, remap = false)
public class StoneDropMixin {
    @Inject(method = "harvestBlock", at = @At("HEAD"), cancellable = true)
    public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfo ci) {
        Block self = (Block) (Object) this;

        entityplayer.addStat(StatList.mineBlockStatArray[self.id], 1);
        ItemStack heldItemStack = entityplayer.inventory.getCurrentItem();
        Item heldItem = heldItemStack != null ? Item.itemsList[heldItemStack.itemID] : null;
        if (heldItem != null) {
            if (heldItem.isSilkTouch() && entityplayer.canHarvestBlock(self)) {
                self.dropBlockWithCause(world, EnumDropCause.SILK_TOUCH, x, y, z, meta, tileEntity);
                return;
            }

            if (heldItem instanceof ItemToolShears && self.hasTag(BlockTags.SHEARS_DO_SILK_TOUCH)) {
                ItemToolShears heldShears = (ItemToolShears) heldItem;
                self.dropBlockWithCause(world, EnumDropCause.SILK_TOUCH, x, y, z, meta, tileEntity);
                heldShears.onBlockSheared(entityplayer, heldItemStack);
                return;
            }
        }

        if (entityplayer.canHarvestBlock(self)) {

            // dropBlockWithCause
            if (!world.isClientSide) {
                ItemStack[] drops = new ItemStack[]{new ItemStack(BetterThanPebble.rockItem, new Random().nextInt(2) + 1)};
                for (ItemStack drop : drops) {
                    for (int i = 0; i < drop.stackSize; ++i) {
                        world.dropItem(x, y, z, new ItemStack(drop.itemID, 1, drop.getMetadata(), drop.tag));
                    }
                }

            }

            ci.cancel();
        } else {
            self.dropBlockWithCause(world, EnumDropCause.IMPROPER_TOOL, x, y, z, meta, tileEntity);
        }
    }

    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void stoneBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        switch (dropCause) {
            case WORLD:
            case EXPLOSION:
                cir.setReturnValue(new ItemStack[]{new ItemStack(BetterThanPebble.rockItem, new Random().nextInt(2) + 1)});
        }
    }
}
