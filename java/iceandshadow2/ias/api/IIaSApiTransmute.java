package iceandshadow2.ias.api;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * An interface for handlers of IaS's transmutation system. This can either be
 * implemented by Item or Block derivatives, or implemented by a handler and
 * registered with IaSRegistry. A handler implemented by the target item/block
 * will be called first. If that handler cannot do the transmutation, if the
 * catalyst implements the handler, that will be called next. If that fails,
 * then the handlers in IaSRegistry will be called until one of them can do the
 * transmutation. IaS guarantees that if multiple handlers in IaSRegistry can do
 * the transmutation, the first one registered will be run.
 */
public interface IIaSApiTransmute {

	/**
	 * Gets the time to perform a transmuation, in ticks (1/20 of a second). Also
	 * called to check if this handler can perform a transmutation. This handler
	 * should not change the item stacks in any way.
	 *
	 * @param target   The item stack being transmuted (on the altar).
	 * @param catalyst The item stack being used for transmutation (held in the
	 *                 player's hand)
	 * @param player   The player doing the transmutation.
	 * @return A number of ticks it should take to do the transmuation, or some
	 *         number <= 0 if this handler cannot.
	 */
	public int getTransmuteTime(ItemStack target, ItemStack catalyst);

	/**
	 * Used to handle a transmutation in a transmutation altar on Nyx. This function
	 * is responsible for reducing the sizes of the item stacks.
	 *
	 * @param target   The item stack being transmuted (on the altar).
	 * @param catalyst The item stack being used for transmutation (held in the
	 *                 player's hand)
	 * @param world    A world object, for convenience and its RNG.
	 * @return The item stack yielded from doing the transmutation, or null if the
	 *         transmutation should destroy the target.
	 */
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world);

	/**
	 * Used to handle particle spawning while transmutation is happening. The altar
	 * already spawns "item-breaking particles". This is for additional particles.
	 * Please do not fully disable the particles. They are the primary indication of
	 * transmutation happening.
	 *
	 * @param target   The item stack being transmuted (on the altar).
	 * @param catalyst The item stack being used for transmutation (held in the
	 *                 player's hand)
	 * @param world    The world in which the particles will be spawned.
	 * @param ent      The invisible entity above the altar at which the particles
	 *                 will be spawned.
	 * @return True if item breaking particles should NOT be spawned, false if they
	 *         should.
	 */
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent);
}
