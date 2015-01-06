package iceandshadow2.api;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * An interface for handlers of blocks that take ItemStacks in and output them.
 * This most notably includes the distiller block.
 * This is for handlers that will be registered with IaSRegistry to deal with input and output from such blocks.
 */
public interface IIaSApiItemStackIO {
	public void canHandleInput(World w, int x, int y, int z);
	public void canHandleOutput(World w, int x, int y, int z);
	public ItemStack handleInput(World w, int x, int y, int z);
	public void handleOutput(World w, int x, int y, int z, List<ItemStack> output);
}
