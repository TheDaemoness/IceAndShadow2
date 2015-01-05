package iceandshadow2.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * An interface for handlers of IaS's distillation system.
 * This can either be implemented by Item or Block derivatives, or implemented by a handler and registered with IaSRegistry.
 * If an Item or Block has a handler, it will always be run instead of any in IaSRegistry.
 * For Item or Block handlers, the item on the alter (the target), not the catalyst, will have its function called.
 */
public interface IIaSApiTransmutable {
	/**
	 * Used to represent an item or block that can be transmuted in a transmutation altar on Nyx.
	 * @param target The item stack being transmuted.
	 * @param catalyst The item stack being used for transmutation.
	 * @param player The player doing the transmutation.
	 * @return The item stack yielded from doing the transmutation, or null if transmutation is impossible.
	 */
	public ItemStack getTransmutationYield(ItemStack target, ItemStack catalyst, EntityPlayer pl);
}
