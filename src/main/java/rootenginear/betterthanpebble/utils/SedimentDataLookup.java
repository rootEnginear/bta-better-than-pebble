package rootenginear.betterthanpebble.utils;

import net.minecraft.core.block.Block;
import rootenginear.betterthanpebble.stuct.SedimentData;

import java.util.HashMap;
import java.util.Map;

public class SedimentDataLookup {
    public static final float gravelFlintChance = 39.8F;
    public static final float chanceByFlint = 1.0F / gravelFlintChance / 10.0F;

    public static final float sandChance = 18.0F * chanceByFlint;
    public static final float gravelChance = 20.0F * chanceByFlint;
    public static final float scorchedDirtChance = 3.0F * chanceByFlint;
    public static final float dirtChance = 60.2F * chanceByFlint;

    public static final Map<Integer, SedimentData> sedimentLookup = new HashMap<>();

    static {
        sedimentLookup.put(Block.sand.id, new SedimentData(sandChance, 4));
        sedimentLookup.put(Block.gravel.id, new SedimentData(gravelChance, 5));
        sedimentLookup.put(Block.dirtScorched.id, new SedimentData(scorchedDirtChance, 4));
        sedimentLookup.put(Block.dirtScorchedRich.id, new SedimentData(scorchedDirtChance, 4));
        sedimentLookup.put(Block.grassScorched.id, new SedimentData(scorchedDirtChance, 4));
        sedimentLookup.put(Block.dirt.id, new SedimentData(dirtChance, 2));
        sedimentLookup.put(Block.grassRetro.id, new SedimentData(dirtChance, 2));
        sedimentLookup.put(Block.grass.id, new SedimentData(dirtChance, 2));
    }

    public static SedimentData getSedimentData(Block block) {
        return sedimentLookup.get(block.id);
    }
}
