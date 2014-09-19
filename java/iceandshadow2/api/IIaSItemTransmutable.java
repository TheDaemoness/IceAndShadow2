package iceandshadow2.api;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IIaSItemTransmutable {
	/**
	 * Used to represent an item that can be transmuted in a transmutation altar on Nyx.
	 * @param target The item stack being transmuted.
	 * @param catalyst The item stack being used for transmutation.
	 * @param player The player doing the transmutation.
	 * @return The item stack yielded from doing the transmutation, or null if transmutation is impossible.
	 */
	public ItemStack getTransmutationYield(ItemStack target, ItemStack catalyst, EntityPlayer pl);
}
