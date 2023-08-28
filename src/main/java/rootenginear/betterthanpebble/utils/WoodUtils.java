package rootenginear.betterthanpebble.utils;

import net.minecraft.core.block.Block;

import java.util.Arrays;

public class WoodUtils {
    public static final int[] LOG_IDS = {Block.logOak.id, Block.logPine.id, Block.logBirch.id, Block.logCherry.id, Block.logEucalyptus.id, Block.logOakMossy.id};

    public static boolean isWood(Block block) {
        return Arrays.stream(LOG_IDS).anyMatch(Integer.valueOf(block.id)::equals);
    }
}
