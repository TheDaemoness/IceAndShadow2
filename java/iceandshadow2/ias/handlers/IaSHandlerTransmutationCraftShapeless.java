package iceandshadow2.ias.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoublePlant;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.util.IntPair;
import iceandshadow2.nyx.items.NyxItemIngot;

/**
 * Handles any shapeless crafting recipes as a fallback.
 */
public class IaSHandlerTransmutationCraftShapeless extends IaSHandlerTransmutationCraft {
	
	@Override
	public Class getRecipeClass(boolean ore) {
		return ore?ShapelessOreRecipe.class:ShapelessRecipes.class;
	}

	@Override
	protected List getInputs(IRecipe recipe, ItemStack target, ItemStack catalyst, boolean orerecipe) {
		if(recipe.getRecipeSize() == 1 && !(target.isItemEqual(catalyst)))
			return null;
		return orerecipe?((ShapelessOreRecipe)recipe).getInput():((ShapelessRecipes)recipe).recipeItems;
	}
}
