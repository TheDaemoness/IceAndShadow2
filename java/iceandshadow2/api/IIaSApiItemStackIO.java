package iceandshadow2.api;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * An interface for handlers of blocks that take ItemStacks in and output them.
 * This includes the distiller block (input and output) and transmutation altars (output only).
 * This is for handlers the will be registered with IaSRegistry to deal with input and output from such
 * blocks. The handlers get called whenever an item stack needs to be processed.
 * The first one registered that can process the stack is the one used.
 * Mainly here to add support for pipes and new types of item storage.
 */
public interface IIaSApiItemStackIO {
	
	/**
	 * Indicates whether this handler can handle extracting items from the given block.
	 * @param x X coordinate of the block from which items will be extracted.
	 * @param tx X coordinate of the block items will be inserted into.
	 */
	public boolean canHandleInput(World w, int x, int y, int z, Block bl, int tx, int ty, int tz);

	/**
	 * Indicates whether this handler can handle placing items into the given block.
	 * @param x X coordinate of the block into which items will be inserted.
	 * @param tx X coordinate of the block items will be extracted from.
	 */
	public boolean canHandleOutput(World w, int x, int y, int z, Block bl, int tx, int ty, int tz);
	
	/**
	 * For input handlers, indicates whether or not this handler is for a block that actively inserts items.
	 * For example, a hopper or BuildCraft pipe actively inserts items into other blocks, but a chest does not.
	 * @param x X coordinate of the block from which items will be extracted.
	 * @param tx X coordinate of the block items will be inserted into.
	 */
	public boolean doesInsertItems(World w, int x, int y, int z, Block bl, int tx, int ty, int tz);
	
	/**
	 * For output handlers, indicates whether or not this handler is for a block that actively extracts items.
	 * For example, a hopper or powered wooden BuildCraft pipe actively extracts items, but a chest does not.
	 * @param x X coordinate of the block into which items will be inserted.
	 * @param tx X coordinate of the block items will be extracted from.
	 */
	public boolean doesExtractItems(World w, int x, int y, int z, Block bl, int tx, int ty, int tz);

	/**
	 * Does the actual work of removing an item from the target.
	 * This method is responsible from clearing any inventory slots.
	 * This method should NOT affect the block at <tx, ty, tz> in any way.
	 * May be called multiple times.
	 * @param x X coordinate of the block from which items will be extracted.
	 * @param tx X coordinate of the block items will be inserted into.
	 * @param cmp 
	 * 		Should be used to check whether the caller accepts particular item stack or not.
	 * This may be null, in which case the caller does not care about what type of item stack is returned.
	 * If non-null, cmp.fitsCriteria(<return value>) should return true for the returned item stack.
	 * Behavior is undefined if the returned stack is invalid. Usually the stack will be discarded.
	 */
	public ItemStack handleInput(World w, int x, int y, int z, IaSItemStackCriteria cmp, int tx, int ty, int tz);

	/**
	 * Does the actual work of placing item stacks into a 
	 * This method is responsible for merging item stacks where possible.
	 * This method should NOT affect the block at <tx, ty, tz> in any way.
	 * @param x X coordinate of the block into which items will be inserted.
	 * @param tx X coordinate of the block items will be extracted from.
	 * @return A list of item stacks that could not be placed into the inventory.
	 */
	public List<ItemStack> handleOutput(World w, int x, int y, int z, List<ItemStack> output, int tx, int ty, int tz);
}
