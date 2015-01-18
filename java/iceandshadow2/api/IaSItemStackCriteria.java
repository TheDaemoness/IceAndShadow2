package iceandshadow2.api;

import net.minecraft.item.ItemStack;

/**
 * A basic class that's used to check if an item stack fits a certain set of criteria.
 * Provided here as it's used in IIaSApiItemStackIO.
 */
public abstract class IaSItemStackCriteria {
	public abstract boolean fitsCriteria(ItemStack is);
}
