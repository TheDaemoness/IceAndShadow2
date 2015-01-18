package iceandshadow2.api;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * An interface for handlers of blocks that take ItemStacks in and output them.
 * This most notably includes the distiller block. This is for handlers that
 * will be registered with IaSRegistry to deal with input and output from such
 * blocks. The handlers get called whenever an item stack needs to be processed.
 * The first one registered that can process the stack is the one used.
 */
public interface IIaSApiItemStackIO {
	
	/**
	 * Indicates whether this handler can handle extracting items from the given block.
	 */
	public boolean canHandleInput(World w, int x, int y, int z, Block bl);

	/**
	 * Indicates whether this handler can handle placing items into the given block.
	 */
	public boolean canHandleOutput(World w, int x, int y, int z, Block bl);
	
	/**
	 * For output handlers, indicates whether or not this handler is for a block that actively extracts items.
	 * For example, a hopper or powered wooden BuildCraft pipe actively extracts items, but a chest does not.
	 */
	public boolean extractsItems(World w, int x, int y, int z, Block bl);

	/**
	 * Does the actual work of removing an item from the target.
	 * This method is responsible from clearing any inventory slots.
	 * May be called multiple times.
	 * @param cmp Should be used to check whether the caller accepts particular item stack or not.
	 * This may be null, in which case the caller does not care about what type of item stack is returned.
	 * If non-null, cmp.fitsCriteria(<return value>) should return true for the returned item stack.
	 * Behavior is undefined if the returned stack is invalid. Usually the stack will be discarded.
	 */
	public ItemStack handleInput(World w, int x, int y, int z, IaSItemStackCriteria cmp);

	/**
	 * Does the actual work of placing item stacks into a target.
	 * This method is responsible for merging item stacks where possible.
	 * @return A list of item stacks that could not be placed into the inventory.
	 */
	public List<ItemStack> handleOutput(World w, int x, int y, int z,
			List<ItemStack> output);
}
