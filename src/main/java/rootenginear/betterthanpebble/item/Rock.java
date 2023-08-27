package rootenginear.betterthanpebble.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;

import java.util.Arrays;

public class Rock extends Item {
    private final int damageVsEntity;
    protected ToolMaterial material;

    public Rock(int id) {
        super("Rock", id);
        this.material = new ToolMaterial().setEfficiency(1.0F, 2.0F).setMiningLevel(0);
        this.damageVsEntity = 3 + this.material.getDamage();
    }

    public float getStrVsBlock(ItemStack itemstack, Block block) {
        return block.hasTag(BlockTags.MINEABLE_BY_AXE) || block.hasTag(BlockTags.MINEABLE_BY_PICKAXE) ? this.material.getEfficiency(false) : 1.0F;
    }

    public int getDamageVsEntity(Entity entity) {
        return this.damageVsEntity;
    }

    public int getBlockHitDelay() {
        return this.material.getBlockHitDelay();
    }

    public boolean canHarvestBlock(Block block) {
        boolean isDeepOre = Arrays.asList(deepOres).contains(block);
        return block.hasTag(BlockTags.MINEABLE_BY_AXE) ||
                (!isDeepOre && block.hasTag(BlockTags.MINEABLE_BY_PICKAXE));
    }

    public static Block[] deepOres = {
            Block.obsidian,
            Block.blockDiamond,
            Block.oreDiamondStone,
            Block.oreDiamondBasalt,
            Block.oreDiamondGranite,
            Block.oreDiamondLimestone,
            Block.blockGold,
            Block.oreGoldStone,
            Block.oreGoldBasalt,
            Block.oreGoldGranite,
            Block.oreGoldLimestone,
            Block.blockIron,
            Block.oreIronStone,
            Block.oreIronBasalt,
            Block.oreIronGranite,
            Block.oreIronLimestone,
            Block.blockSteel,
            Block.oreNethercoalNetherrack,
            Block.blockLapis,
            Block.oreLapisStone,
            Block.oreLapisBasalt,
            Block.oreLapisGranite,
            Block.oreLapisLimestone,
            Block.blockRedstone,
            Block.oreRedstoneStone,
            Block.oreRedstoneBasalt,
            Block.oreRedstoneGranite,
            Block.oreRedstoneLimestone,
            Block.oreRedstoneGlowingStone,
            Block.oreRedstoneGlowingBasalt,
            Block.oreRedstoneGlowingGranite,
            Block.oreRedstoneGlowingLimestone,
    };
}
