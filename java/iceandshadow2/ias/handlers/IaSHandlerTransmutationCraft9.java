package iceandshadow2.ias.handlers;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Handles crafting recipes that take 9 identical items.
 */
public class IaSHandlerTransmutationCraft9 extends IaSHandlerTransmutationCraft {

	@Override
	protected List getInputs(IRecipe recipe, ItemStack target, ItemStack catalyst, boolean orerecipe) {
		if ((recipe.getRecipeSize() != 9 && recipe.getRecipeSize() != 1) || !target.isItemEqual(catalyst))
			return null;
		return super.getInputs(recipe, target, catalyst, orerecipe);
	}

	@Override
	public Class getRecipeClass(boolean ore) {
		return ore ? ShapedOreRecipe.class : ShapedRecipes.class;
	}
}
