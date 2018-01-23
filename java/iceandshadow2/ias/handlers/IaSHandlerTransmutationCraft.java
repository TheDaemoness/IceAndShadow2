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
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.util.IntPair;
import iceandshadow2.nyx.items.NyxItemIngot;

/**
 * Handles any shapeless crafting recipes as a fallback.
 */
public abstract class IaSHandlerTransmutationCraft implements IIaSApiTransmute {
	
	protected abstract List getInputs(IRecipe recipe, ItemStack target, ItemStack catalyst, boolean orerecipe);
	
	protected IntPair costs(IRecipe recipe, ItemStack target, ItemStack catalyst, boolean orerecipe) {
		List l = getInputs(recipe, target, catalyst, orerecipe);
		if(l == null)
			return new IntPair();
		int
			catacount = 0,
			targcount = 0;
		for(Object input : l) {
			List<ItemStack> isl = null;
			if(input instanceof ItemStack)
				isl = Arrays.asList((ItemStack)input);
			else if (input instanceof List) {
				if(((List)input).get(0) instanceof ItemStack)
					isl = (List<ItemStack>)input;
			}
			if(isl == null)
				return new IntPair(); //Something failed.
			boolean matched = false;
			for(ItemStack is : isl) {
				if(is.getItem() == null)
					continue;
				final boolean noDmgCheck = is.getItemDamage() == 32767;
				if(is.getItem() == target.getItem()
						&& target.stackSize-targcount > 0
						&& (noDmgCheck || is.getItemDamage() == target.getItemDamage())) {
					++targcount;
					matched = true;
					break;
				}
				else if(is.getItem() == catalyst.getItem()
						&& catalyst.stackSize-catacount > 0
						&& (noDmgCheck || is.getItemDamage() == catalyst.getItemDamage())) {
					++catacount;
					matched = true;
					break;
				}
			}
			if(!matched)
				return new IntPair();
		}
		return new IntPair(targcount, catacount);
	}

	public abstract Class getRecipeClass(boolean ore);
	
	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		final List l = CraftingManager.getInstance().getRecipeList();
		for(Object o : l) {
			Class recipeClass = o.getClass();
			final boolean orerecipe = recipeClass == getRecipeClass(true);
			if(orerecipe || recipeClass == getRecipeClass(false)) {
				if(costs((IRecipe)o, target, catalyst, orerecipe).nonzero())
					return 15;
			}
		}
		return 0;
	}

	//This right here is a case study in why the transmute interface was badly-designed.
	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final List l = CraftingManager.getInstance().getRecipeList();
		IRecipe largest = null;
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>(32);
		IntPair cost = null;
		for(Object o : l) {
			Class recipeClass = o.getClass();
			final boolean orerecipe = recipeClass == getRecipeClass(true);
			if(orerecipe || recipeClass == getRecipeClass(false)) {
				IRecipe recipe = (IRecipe)o;
				IntPair costTemp = costs(recipe, target, catalyst, orerecipe);
				if(costTemp.nonzero()
						&& (largest == null || recipe.getRecipeSize() > largest.getRecipeSize())) {
					largest = recipe;
					cost = costTemp;
				}
			}
		}
		final boolean orerecipe = largest.getClass() == getRecipeClass(true);
		boolean equal = target.isItemEqual(catalyst);
		while(cost.nonzero()) {
			target.stackSize -= cost.x();
			catalyst.stackSize -= cost.z();
			if(equal && catalyst.stackSize > 0) {
				final int delta = Math.min(catalyst.stackSize-1, target.getMaxStackSize()-target.stackSize);
				target.stackSize += delta;
				catalyst.stackSize -= delta;
			}
			ret.add(largest.getRecipeOutput().copy());
			cost = costs(largest, target, catalyst, orerecipe);
		}
		if(equal) {
			final int delta = Math.min(target.stackSize, catalyst.getMaxStackSize()-catalyst.stackSize);
			catalyst.stackSize += delta;
			target.stackSize -= delta;
		}
		if (largest.getRecipeOutput().isItemEqual(catalyst)) {
			ret.add(catalyst.copy());
			catalyst.stackSize = 0;
		}
		return ret;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}

}
