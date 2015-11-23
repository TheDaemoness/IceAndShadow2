package iceandshadow2.api;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * An interface for handlers of IaS's transmutation lens system. This may be
 * implemented by an item that will go in the target slot of a transmutation altar.
 * They may also be implemented by functions
 * These functions are only called if the item in the target slot implements this.
 * In other words, no making lenses out of objects that the mod creator didn't specifically intend.
 * This takes precedence over IIaSApiTransmute.
 */
public interface IIaSApiTransmuteLens {

	/**
	 * Gets the transmutation duration.
	 * Also used to check if a transmutation is possible.
	 *
	 * @param lenstype
	 *            The type of the equipped lens.
	 *            Null indicates a third-party lens.
	 * @param lens
	 *            The equipped lens.
	 *            Used to check if a transmutation should be possible with this lens.
	 * @param target
	 *            The item stack being distilled.
	 * @return The number of ticks it takes to process the item stack, or 0 if not possible.
	 */
	public int getTransmuteLensRate(EnumIaSLenses lenstype, ItemStack lens, ItemStack target);

	/**
	 * Gets the yield of the transmutation.
	 * As a rule of thumb, the lens should NOT be consumed by the transmutation.
	 *
	 * @param lens
	 *            The equipped lens.
	 *            Provided for mod authors who want to do fun things with NBT.
	 * @param target
	 *            The item stack being transmuted.
	 * @return The item stacks yielded from doing the transmutation. Null is acceptable.
	 */
	public List<ItemStack> getTransmuteLensYield(ItemStack lens, ItemStack target);

	/**
	 * Used to get a new texture for the top of the transmutation altar.
	 *
	 * @param lens
	 *            The equipped lens.
	 * @return The icon to be used for the altar while this lens is equipped, or null if no change.
	 */
	public IIcon getAltarTopTexture(ItemStack lens);

	/**
	 * Used to handle particle spawning while transmutation is happening. The
	 * altar already spawns "item-breaking particles". This is for additional
	 * particles. Please do not fully disable the particles. They are the
	 * primary indication of transmutation happening.
	 *
	 * @param target
	 *            The item stack being transmuted (on the altar).
	 * @param catalyst
	 *            The item stack being used for transmutation (held in the
	 *            player's hand)
	 * @param world
	 *            The world in which the particles will be spawned.
	 * @param ent
	 *            The invisible entity above the altar at which the particles
	 *            will be spawned.
	 * @return True if item breaking particles should NOT be spawned, false if
	 *         they should.
	 */
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent);
}
