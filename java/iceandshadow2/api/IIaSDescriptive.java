package iceandshadow2.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Interface for items that should display additional descriptive information upon sneaking.
 */
public interface IIaSDescriptive {
	/**
	 * Returns an unlocalized description, which will be concatenated to 'ias2.description.' or another unlocalized description depending on the caller's needs.
	 * May be passed null, in which case this function should give a sensible default.
	 * Should NOT ever return null. Return an empty string if there is nothing to say.
	 * @param entityPlayer 
	 */
	public String getUnlocalizedDescription(EntityPlayer entityPlayer, ItemStack is);

	/**
	 * Return true if the hint is actually a red text warning.
	 * If true, the hint will not be hidden if the player disables hint tooltips and be displayed differently.
	 * This should be reserved for effects that pose a significant danger to the player.
	 */
	public boolean isHintWarning(EntityPlayer entityPlayer, ItemStack itemStack);

	/**
	 * Returns a hint that gets displayed.
	 * @param entityPlayer
	 * @param itemStack
	 * @return
	 */
	public String getUnlocalizedHint(EntityPlayer entityPlayer, ItemStack itemStack);
}
