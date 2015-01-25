package iceandshadow2.api;

import net.minecraft.item.ItemStack;

/**
 * An interface for handlers of IaS's distillation system. This can either be
 * implemented by Item or Block derivatives, or implemented by a handler and
 * registered with IaSRegistry. If an Item or Block has a handler, it will
 * always be run instead of any in IaSRegistry.
 */
public interface IIaSApiDistillable {

	/**
	 * Gets how many items will be removed from an item stack during processing.
	 * Also used to check if a distillation is possible.
	 * 
	 * @param target
	 *            The item stack being distilled.
	 * @return The number of items that will be removed from stq in processing,
	 *         or 0 if no processing.
	 */
	public int getDistillationRate(ItemStack stq);

	/**
	 * Used to represent an item or block that can be distilled. Only called
	 * getDistillationRate().
	 * 
	 * @param target
	 *            The item stack being distilled.
	 * @return The item stack yielded from doing the distillation. Return null
	 *         if the object should be destroyed.
	 */
	public ItemStack getDistillationYield(ItemStack target);
}
