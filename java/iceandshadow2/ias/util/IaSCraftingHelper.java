package iceandshadow2.ias.util;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class IaSCraftingHelper {

	public static int ANY_METADATA_MAGIC_NUMBER = 32767;

	public static Object extractItem(ItemStack is) {
		if(is == null)
			return null;
		Object o = is.getItem();
		if(o instanceof ItemBlock)
			return ((ItemBlock)o).field_150939_a;
		return o;
	}

	public static List getCraftingRecipeInputs(IRecipe recipe) {
		if(recipe instanceof ShapedOreRecipe)
			return Arrays.asList(((ShapedOreRecipe)recipe).getInput());
		if(recipe instanceof ShapedRecipes)
			return Arrays.asList(((ShapedRecipes)recipe).recipeItems);
		if(recipe instanceof ShapelessOreRecipe)
			return ((ShapelessOreRecipe)recipe).getInput();
		if(recipe instanceof ShapelessRecipes)
			return ((ShapelessRecipes)recipe).recipeItems;
		return Arrays.asList();
	}

	public static ItemStack extractStack(Object o) {
		if(o instanceof ItemStack)
			return ((ItemStack)o).copy();
		if(o instanceof Item)
			return new ItemStack((Item)o);
		if(o instanceof Block)
			return new ItemStack((Block)o);
		if(o instanceof List && !((List)o).isEmpty())
			return extractStack(((List)o).get(0));
		return null;
	}
}
