package iceandshadow2.api;

import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * An interface for handlers of IaS's distillation system.
 * This can either be implemented by Item or Block derivatives, or implemented by a handler and registered with IaSRegistry.
 * If an Item or Block has a handler, it will always be run instead of any in IaSRegistry.
 */
public interface IIaSApiDistillable {
	/**
	 * Used to represent an item or block that can be distilled.
	 * @param target The item stack being distilled.
	 * @return The item stack yielded from doing the distillation. Return null if the object should be destroyed.
	 */
	public ItemStack getDistillationYield(ItemStack target);
	
	/**
	 * Used to represent how many of this type of object can be processed at once.
	 * @param target The item stack being distilled.
	 * @return How many of this item/block can be processed at once. Return 0 if it should not be processed.
	 */
	public int getBaseRate(ItemStack target);
}
