package iceandshadow2.ias.handlers;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Handles any shapeless crafting recipes as a fallback.
 */
public class IaSHandlerTransmutationCraftShapeless extends IaSHandlerTransmutationCraft {

	@Override
	protected List getInputs(IRecipe recipe, ItemStack target, ItemStack catalyst, boolean orerecipe) {
		if (recipe.getRecipeSize() == 1 && !(target.isItemEqual(catalyst)))
			return null;
		return orerecipe ? ((ShapelessOreRecipe) recipe).getInput() : ((ShapelessRecipes) recipe).recipeItems;
	}

	@Override
	public Class getRecipeClass(boolean ore) {
		return ore ? ShapelessOreRecipe.class : ShapelessRecipes.class;
	}
}
