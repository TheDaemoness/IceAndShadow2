package iceandshadow2.api;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IIaSDistillable {
	/**
	 * Used to represent an item or block that can be distilled.
	 * @param target The item stack being distilled.
	 * @return The item stacks yielded from doing the distillation. Return null if the object should be destroyed.
	 */
	public List<ItemStack> getDistillationYield(ItemStack target);
	
	/**
	 * Used to represent how many of this type of object can be processed at once.
	 * @param target The item stack being distilled.
	 * @return How many of this item/block can be processed at once. Return 0 if it should not be processed.
	 */
	public int getBaseRate(ItemStack target);
}
