package rootenginear.betterthanpebble;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.RecipeHelper;

public class BetterThanPebble implements ModInitializer {
    public static final String MOD_ID = "better-than-pebble";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    static {
        // Prevent Java compiler to remove unused import
        Block $ = Block.blockOlivine;
    }

    @Override
    public void onInitialize() {
        // Remove Wood Item Recipe
        RecipeHelper.Crafting.removeRecipe(Item.toolAxeWood);
        RecipeHelper.Crafting.removeRecipe(Item.toolHoeWood);
        RecipeHelper.Crafting.removeRecipe(Item.toolPickaxeWood);
        RecipeHelper.Crafting.removeRecipe(Item.toolShovelWood);
        RecipeHelper.Crafting.removeRecipe(Item.toolSwordWood);

        LOGGER.info("Better Than Pebble initialized.");
    }
}
