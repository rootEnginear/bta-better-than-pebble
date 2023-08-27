package rootenginear.betterthanpebble;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rootenginear.betterthanpebble.item.Rock;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.RecipeHelper;

public class BetterThanPebble implements ModInitializer {
    public static final String MOD_ID = "betterthanpebble";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    static {
        // Prevent Java compiler to remove unused import
        Block $1 = Block.blockOlivine;
        Item $2 = Item.olivine;
    }

    public static final int ITEM_ID = 31415;
    public static final Item rockItem = ItemHelper.createItem(MOD_ID, new Rock(ITEM_ID), "rock", "rock.png");

    @Override
    public void onInitialize() {
        // Remove Wood Item Recipe
        RecipeHelper.Crafting.removeRecipe(Item.toolAxeWood);
        RecipeHelper.Crafting.removeRecipe(Item.toolHoeWood);
        RecipeHelper.Crafting.removeRecipe(Item.toolPickaxeWood);
        RecipeHelper.Crafting.removeRecipe(Item.toolShovelWood);
        RecipeHelper.Crafting.removeRecipe(Item.toolSwordWood);

        // Remove Cobble Item Recipe
        RecipeHelper.Crafting.removeRecipe(Block.cobbleStone);

        // Add Rock Recipes
        RecipeHelper.Crafting.createRecipe(rockItem, 1, new Object[]{
                "AA",
                "AA",
                'A', Item.ammoPebble
        });
        RecipeHelper.Crafting.createRecipe(Block.cobbleStone, 1, new Object[]{
                "AA",
                "AA",
                'A', rockItem
        });

        LOGGER.info("Better Than Pebble initialized.");
    }
}
