package rootenginear.betterthanpebble.mixin;

import net.minecraft.client.Minecraft;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rootenginear.betterthanpebble.item.Rock;

import java.util.Arrays;
import java.util.Random;

@Mixin(value = {Block.class}, remap = false)
public class WoodDropMixin {
    @Inject(method = "getBreakResult", at = @At("HEAD"), cancellable = true)
    private void woodDropCheck(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
        Block self = (Block) (Object) this;

        int[] logIDs = {Block.logOak.id, Block.logPine.id, Block.logBirch.id, Block.logCherry.id, Block.logEucalyptus.id, Block.logOakMossy.id};

        // Check if it's wood
        if (Arrays.stream(logIDs).anyMatch(Integer.valueOf(self.id)::equals)) {
            EntityPlayer player = Minecraft.getMinecraft(Minecraft.class).thePlayer;
            ItemStack inHand = player.getCurrentEquippedItem();

            // If the player's hand is empty OR don't has proper tools
            if (inHand == null || (
                    !(inHand.getItem() instanceof ItemToolAxe) && !(inHand.getItem() instanceof Rock)
            )) {
                cir.setReturnValue(new ItemStack[]{new ItemStack(Item.stick, new Random().nextInt(5))});
            }
        }
    }
}