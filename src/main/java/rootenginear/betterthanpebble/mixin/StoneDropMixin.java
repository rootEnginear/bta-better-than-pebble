package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockStone;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.betterthanpebble.BetterThanPebble;
import rootenginear.betterthanpebble.utils.DropItemUtils;

import java.util.Random;

@Mixin(value = {Block.class}, remap = false)
public class StoneDropMixin {
    @Inject(method = "onBlockDestroyedByPlayer", at = @At("HEAD"))
    private void onStoneDestroyedByPlayer(World world, int x, int y, int z, int meta, EntityPlayer player, Item item, CallbackInfo ci) {
        if (player.getGamemode() == Gamemode.creative) return;

        Block self = (Block) (Object) this;
        if (!player.canHarvestBlock(self) || !(self instanceof BlockStone)) return;

        // Has PROPER_TOOL
        ItemStack inHand = player.getCurrentEquippedItem();
        if (inHand != null) {
            Item heldItem = inHand.getItem();

            // If Silk, Skip drop (Handled in `stoneBreakResult`)
            if (heldItem.isSilkTouch()) return;

            // If Other Pick, Drops Cobble
            if (heldItem instanceof ItemToolPickaxe) {
                DropItemUtils.WorldDropItem(world, x, y, z, new ItemStack(((BlockStone) self).cobblestone));
                return;
            }
        }

        // Normal Drop
        DropItemUtils.WorldDropItem(world, x, y, z, new ItemStack(BetterThanPebble.rockItem, new Random().nextInt(2) + 1));
    }
}
