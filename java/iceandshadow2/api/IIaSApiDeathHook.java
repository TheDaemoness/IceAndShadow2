package iceandshadow2.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * A handler for Nyx's deth(tm) system.
 * Allows mods to alter how their items behave on death in Nyx.
 * Can be implemented on an item or block, or registered with IIaSRegistry.
 * A handler on an item or block will override all in IIaSRegistry.
 */
public interface IIaSApiDeathHook {
	EnumIaSDeathAction getActionHotbar(EntityPlayer pwai, ItemStack is);
	EnumIaSDeathAction getActionInventory(EntityPlayer pwai, ItemStack is);
	EnumIaSDeathAction getActionArmor(EntityPlayer pwai, ItemStack is);
	ItemStack getAlteredItemStack(EntityPlayer pwai, ItemStack is);
}
