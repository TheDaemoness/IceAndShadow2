package iceandshadow2.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * A behavior controller for Nyx's deth(tm) system. Allows mods to alter how
 * their items behave on death in Nyx. Can be implemented on an item or block,
 * or registered with IIaSRegistry. A handler on an item or block will override
 * all in IIaSRegistry.
 */
public interface IIaSApiDeathHook {
	/**
	 * Called if the relevant item function returned one of the ALTER actions.
	 * If ALTER_BOTH had been returned, willDrop will be false.
	 *
	 * @param willDrop
	 *            Indicates if this item stack will be dropped or not.
	 * @param pwai
	 *            The player.
	 * @param is
	 *            The item stack to alter.
	 */
	void alterItemStack(boolean willDrop, EntityPlayer pwai, ItemStack is);

	/**
	 * Called when the handler is processing an item stack that the player has
	 * equipped.
	 *
	 * @param pwai
	 *            The player.
	 * @param is
	 *            The item stack in question.
	 * @return The action the death system should take. Returning default will
	 *         keep the item, unless another handler returns non-default.
	 */
	EnumIaSDeathAction getActionArmor(EntityPlayer pwai, ItemStack is);

	/**
	 * Called when the handler is processing an item stack on the hotbar.
	 *
	 * @param pwai
	 *            The player.
	 * @param is
	 *            The item stack in question.
	 * @return The action the death system should take. Returning default will
	 *         call getActionInventory().
	 */
	EnumIaSDeathAction getActionHotbar(EntityPlayer pwai, ItemStack is);

	/**
	 * Called when the handler is processing an item stack in the player's
	 * inventory.
	 *
	 * @param pwai
	 *            The player.
	 * @param is
	 *            The item stack in question.
	 * @return The action the death system should take. Returning default will
	 *         drop the item, unless another handler returns non-default.
	 */
	EnumIaSDeathAction getActionInventory(EntityPlayer pwai, ItemStack is);

	/**
	 * Called before alterItemStack if ALTER_BOTH was returned.
	 *
	 * @param pwai
	 *            The player.
	 * @param is
	 *            The item stack being processed.
	 * @return The altered item stack to drop.
	 */
	ItemStack getAlterBothDrop(EntityPlayer pwai, ItemStack is);
}
