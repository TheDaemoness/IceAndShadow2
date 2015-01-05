package iceandshadow2.api;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * An interface for handlers of IaS's experience altar sacrifice system.
 * This can either be implemented by Item or Block derivatives, or implemented by a handler and registered with IaSRegistry.
 * If an Item or Block has a handler, it will always be run instead of any in IaSRegistry.
 * If multiple handlers return experience for the same item, the first one registered is the one that will be used.
 */
public interface IIaSApiSacrificeXp {
	/**
	 * Gets the experience spat out by an experience altar for a given item stack.
	 * @param is The item stack put into an experience altar.
	 * @param rand A random object, for convenience.
	 * @return The experience to be returned, or 0 if the item should be destroyed with no yield.
	 * Negative numbers are treated as 0.
	 */
	public int getXpValue(ItemStack is, Random rand);
}
