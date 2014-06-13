package iceandshadow2.api;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IIaSAltarDistillable {
	/**
	 * Returns the distilled item/block thrown into a distillation altar on Nyx.
	 * @param world
	 * @param it The item stack being thrown in.
	 * @return A value that is positive if the altar should yield experience, zero if the item should be rejected, and negative if the altar should create an explosion.
	 */
	public List<ItemStack> getDistillationYield(World world, ItemStack it);
}
