package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLog;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.item.Rock;
import rootenginear.betterthanpebble.utils.DropItemUtils;

import java.util.Random;

@Mixin(value = {Block.class}, remap = false)
public class WoodDropMixin {
    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void woodBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        Block self = (Block) (Object) this;

        if (self instanceof BlockLog) {
            switch (dropCause) {
                case WORLD:
                case EXPLOSION:
                    cir.setReturnValue(new ItemStack[]{new ItemStack(Item.stick, new Random().nextInt(5))});
                    break;
                case PROPER_TOOL:
                    // Cancel wood drop, check via `onWoodDestroyedByPlayer`
                    cir.setReturnValue(null);
            }
        }
    }

    @Inject(method = "onBlockDestroyedByPlayer", at = @At("HEAD"))
    private void onWoodDestroyedByPlayer(World world, int x, int y, int z, int meta, EntityPlayer player, Item item, CallbackInfo ci) {
        if (player.getGamemode() == Gamemode.creative) return;

        Block self = (Block) (Object) this;
        if (!player.canHarvestBlock(self) || !(self instanceof BlockLog)) return;

        // Has PROPER_TOOL
        ItemStack inHand = player.getCurrentEquippedItem();

        if (inHand != null) {
            Item heldItem = inHand.getItem();

            // If Silk, Skip drop (Handled in `woodBreakResult`)
            if (heldItem.isSilkTouch()) return;

            // If Other Axe, Drops Wood
            if (heldItem instanceof ItemToolAxe || heldItem instanceof Rock) {
                DropItemUtils.WorldDropItem(world, x, y, z, new ItemStack(self));
                return;
            }
        }

        // Normal Drop
        DropItemUtils.WorldDropItem(world, x, y, z, new ItemStack(Item.stick, new Random().nextInt(5)));
    }
}
