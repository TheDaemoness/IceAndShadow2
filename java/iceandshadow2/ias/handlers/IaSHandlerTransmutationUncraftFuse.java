package iceandshadow2.ias.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.util.IaSItemHelper;
import iceandshadow2.ias.util.IntPair;

/**
 * Reverses crafting recipes that obey two rules:
 * 1: The recipe must generate at least double the output items from input items.
 * 2: The recipe must be a standard/oredict shaped/shapeless recipe.
 */
public class IaSHandlerTransmutationUncraftFuse implements IIaSApiTransmute {

	protected IntPair costs(IRecipe recipe, ItemStack target, ItemStack catalyst, boolean sanitycheck) {
		if(recipe.getRecipeOutput() == null)
			return new IntPair();
		final int
			sizeOut = recipe.getRecipeOutput().stackSize;
		if(sanitycheck) {
			if(recipe.getRecipeSize() >= sizeOut)
				return new IntPair();
			if(!target.isItemEqual(recipe.getRecipeOutput())
					|| target.hasTagCompound()
					|| catalyst.hasTagCompound())
				return new IntPair();
			if(recipe.getClass() != ShapelessRecipes.class &&
					recipe.getClass() != ShapelessOreRecipe.class &&
					recipe.getClass() != ShapedRecipes.class &&
					recipe.getClass() != ShapedOreRecipe.class)
				return new IntPair();
		}
		if(target.stackSize+catalyst.stackSize < sizeOut)
			return new IntPair();
		final int targetdrain = Math.min(sizeOut, target.stackSize);
		return new IntPair(targetdrain, sizeOut-targetdrain);
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if(!target.isItemEqual(catalyst))
			return 0;
		if(IaSRegistry.isBlacklistedUncraft(IaSItemHelper.extractItem(target).getClass()))
			return 0;
		final List l = CraftingManager.getInstance().getRecipeList();
		for(final Object o : l) {
			if(o instanceof IRecipe && costs((IRecipe)o, target, catalyst, true).nonzero())
					return 25;
		}
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final ArrayList<ItemStack> ret = new ArrayList<ItemStack>(11);
		IntPair cost = null;
		IRecipe toReverse = null;
		final List l = CraftingManager.getInstance().getRecipeList();
		for(final Object o : l) {
			if(o instanceof IRecipe) {
				cost = costs((IRecipe)o, target, catalyst, true);
				if(cost.nonzero()) {
					toReverse = (IRecipe)o;
					break;
				}
			}
		}
		if(cost == null || toReverse == null)
			return Arrays.asList(); //Something went horribly wrong.
		final Class toReverseClass = toReverse.getClass();
		do {
			target.stackSize -= cost.x();
			catalyst.stackSize -= cost.z();
			List ins;
			if(toReverseClass == ShapelessRecipes.class) {
				ins = ((ShapelessRecipes)toReverse).recipeItems;
			} else if(toReverseClass == ShapelessOreRecipe.class) {
				ins = ((ShapelessOreRecipe)toReverse).getInput();
			} else if(toReverseClass == ShapedRecipes.class) {
				ins = Arrays.asList(((ShapedRecipes)toReverse).recipeItems);
			} else if(toReverseClass == ShapedOreRecipe.class) {
				ins = Arrays.asList(((ShapedOreRecipe)toReverse).getInput());
			} else
				return ret;
			for(final Object o : ins) {
				try {
					ItemStack is;
					if(o instanceof List) {
						is = (ItemStack)((List)o).get(0);
					} else {
						is = (ItemStack)o;
					}
					is = is.copy();
					if(is.getItemDamage() == IaSHandlerTransmutationCraft.ANY_METADATA_MAGIC_NUMBER) {
						is.setItemDamage(0);
					}
					ret.add(is);
				} catch (final Exception e) {
					continue;
				}
			}
			cost = costs(toReverse, target, catalyst, false);
		} while(cost.nonzero());
		if(target.stackSize > 0) {
			ret.add(target.copy());
			target.stackSize = 0;
		}
		if(catalyst.stackSize > 0) {
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
