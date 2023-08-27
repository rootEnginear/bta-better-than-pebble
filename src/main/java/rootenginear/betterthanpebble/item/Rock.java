package rootenginear.betterthanpebble.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;

public class Rock extends ItemTool {
    public Rock(String name, int id) {
        super(name, id, 1, new ToolMaterial().setEfficiency(1.0F, 2.0F).setMiningLevel(0), BlockTags.MINEABLE_BY_AXE);
    }

    public boolean canHarvestBlock(Block block) {
        return block.hasTag(BlockTags.MINEABLE_BY_AXE);
    }
}
