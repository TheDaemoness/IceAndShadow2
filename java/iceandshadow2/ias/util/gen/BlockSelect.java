package iceandshadow2.ias.util.gen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public abstract class BlockSelect {
	public abstract Block getBlock(World w, int x, int y, int z);

	public abstract int getMeta(World w, int x, int y, int z);
}
