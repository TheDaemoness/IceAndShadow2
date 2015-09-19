package iceandshadow2.api;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public interface IIaSBlockThawable {
	public Block onThaw(World w, int x, int y, int z);
}
