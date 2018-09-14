package iceandshadow2.ias.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import iceandshadow2.boilerplate.IntPair;
import iceandshadow2.ias.api.IIaSApiTransmute;
import iceandshadow2.ias.util.IaSCraftingHelper;

/**
 * Handles any shapeless crafting recipes as a fallback.
 */
public abstract class IaSHandlerTransmutationCraft implements IIaSApiTransmute {

	protected IntPair costs(IRecipe recipe, ItemStack target, ItemStack catalyst, boolean orerecipe) {
		final List l = getInputs(recipe, target, catalyst, orerecipe);
		if (l == null)
			return new IntPair();
		int catacount = 0, targcount = 0;
		for (final Object input : l) {
			List<ItemStack> isl = null;
			if (input instanceof ItemStack)
				isl = Arrays.asList((ItemStack) input);
			else if (input instanceof List)
				if (((List) input).get(0) instanceof ItemStack)
					isl = (List<ItemStack>) input;
			if (isl == null)
				return new IntPair(); // Something failed.
			boolean matched = false;
			for (final ItemStack is : isl) {
				if (is.getItem() == null)
					continue;
				final boolean noDmgCheck = is.getItemDamage() == IaSCraftingHelper.ANY_METADATA_MAGIC_NUMBER;
				if (is.getItem() == target.getItem() && target.stackSize - targcount > 0
						&& (noDmgCheck || is.getItemDamage() == target.getItemDamage())) {
					++targcount;
					matched = true;
					break;
				} else if (is.getItem() == catalyst.getItem() && catalyst.stackSize - catacount > 0
						&& (noDmgCheck || is.getItemDamage() == catalyst.getItemDamage())) {
					++catacount;
					matched = true;
					break;
				}
			}
			if (!matched)
				return new IntPair();
		}
		return new IntPair(targcount, catacount);
	}

	protected List getInputs(IRecipe recipe, ItemStack target, ItemStack catalyst, boolean orerecipe) {
		return IaSCraftingHelper.getCraftingRecipeInputs(recipe);
	}

	public abstract Class getRecipeClass(boolean ore);

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		final List l = CraftingManager.getInstance().getRecipeList();
		for (final Object o : l) {
			final Class recipeClass = o.getClass();
			final boolean orerecipe = recipeClass == getRecipeClass(true);
			if (orerecipe || recipeClass == getRecipeClass(false))
				if (costs((IRecipe) o, target, catalyst, orerecipe).nonzero())
					return 15;
		}
		return 0;
	}

	// This right here is a case study in why the transmute interface was
	// badly-designed.
	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final List l = CraftingManager.getInstance().getRecipeList();
		IRecipe largest = null;
		final ArrayList<ItemStack> ret = new ArrayList<ItemStack>(32);
		IntPair cost = null;
		for (final Object o : l) {
			final Class recipeClass = o.getClass();
			final boolean orerecipe = recipeClass == getRecipeClass(true);
			if (orerecipe || recipeClass == getRecipeClass(false)) {
				final IRecipe recipe = (IRecipe) o;
				final IntPair costTemp = costs(recipe, target, catalyst, orerecipe);
				if (costTemp.nonzero() && (largest == null || recipe.getRecipeSize() > largest.getRecipeSize())) {
					largest = recipe;
					cost = costTemp;
				}
			}
		}
		final boolean orerecipe = largest.getClass() == getRecipeClass(true);
		final boolean equal = target.isItemEqual(catalyst);
		while (cost.nonzero()) {
			target.stackSize -= cost.x();
			catalyst.stackSize -= cost.z();
			if (equal && catalyst.stackSize > 0) {
				final int delta = Math.min(catalyst.stackSize - 1, target.getMaxStackSize() - target.stackSize);
				target.stackSize += delta;
				catalyst.stackSize -= delta;
			}
			ret.add(largest.getRecipeOutput().copy());
			cost = costs(largest, target, catalyst, orerecipe);
		}
		if (equal) {
			final int delta = Math.min(target.stackSize, catalyst.getMaxStackSize() - catalyst.stackSize);
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
