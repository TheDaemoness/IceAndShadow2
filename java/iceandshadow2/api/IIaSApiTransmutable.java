package iceandshadow2.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * An interface for handlers of IaS's transmutation system.
 * This can either be implemented by Item or Block derivatives, or implemented by a handler and registered with IaSRegistry.
 * A handler implemented by the target item/block will be called first.
 * If that handler cannot do the transmutation, if the catalyst implements the handler, that will be called next.
 * If that fails, then the handlers in IaSRegistry will be called until one of them can do the transmutation.
 * IaS guarantees that if multiple handlers in IaSRegistry can do the transmutation, the first one registered will be run.
 */
public interface IIaSApiTransmutable {
	
	/**
	 * Called to check if this handler can perform a transmutation.
	 * @param target The item stack being transmuted (on the altar).
	 * @param catalyst The item stack being used for transmutation (held in the player's hand)
	 * @param player The player doing the transmutation.
	 * @return True if this handler has a yield for a transmutation, false otherwise.
	 */
	public boolean canDoTransmutation(ItemStack target, ItemStack catalyst, EntityPlayer pl);
	
	/**
	 * Used to handle a transmutation in a transmutation altar on Nyx.
	 * @param target The item stack being transmuted (on the altar).
	 * @param catalyst The item stack being used for transmutation (held in the player's hand)
	 * @param player The player doing the transmutation.
	 * @return The item stack yielded from doing the transmutation, or null if the transmutation should destroy the target.
	 */
	public ItemStack getTransmutationYield(ItemStack target, ItemStack catalyst, EntityPlayer pl);
	
	/**
	 * Gets the number of catalyst item that will be consumed by a certain transmutation.
	 * If this number exceeds the amount of held catalyst, the transmutation will not happen.
	 * @param target The item stack being transmuted (on the altar).
	 * @param catalyst The item stack being used for transmutation (held in the player's hand)
	 * @param player The player doing the transmutation.
	 * @return The number of catalyst items that should be consumed.
	 * Zero is valid and means that the catalyst shouldn't be consumed.
	 * Negative numbers will be treated as zero.
	 */
	public int getCatalystCost(ItemStack target, ItemStack catalyst, EntityPlayer pl);
}
