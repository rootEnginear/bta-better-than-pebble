package rootenginear.betterthanpebble.utils;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class DropItemUtils {
    public static void WorldDropItem(World world, int x, int y, int z, ItemStack itemStack) {
        if (!world.isClientSide) world.dropItem(x, y, z, itemStack);
    }
}
