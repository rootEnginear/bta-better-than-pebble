package rootenginear.betterthanpebble.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rootenginear.betterthanpebble.BetterThanPebble;
import rootenginear.betterthanpebble.item.Rock;

import java.util.Random;

@Mixin(value = {Block.class}, remap = false)
public class StoneDropMixin {
    @Inject(method = "onBlockDestroyedByPlayer", at = @At("HEAD"))
    private void onStoneDestroyedByPlayer(World world, int x, int y, int z, int meta, EntityPlayer player, Item item, CallbackInfo ci) {
        Block self = (Block) (Object) this;

        if (self.id == Block.stone.id) {
            ItemStack inHand = player.getCurrentEquippedItem();
            if (inHand != null && inHand.getItem() instanceof Rock) {
                if (!world.isClientSide) {
                    world.dropItem(x, y, z, new ItemStack(BetterThanPebble.rockItem, new Random().nextInt(2) + 1));
                }
            }
        }
    }
}
