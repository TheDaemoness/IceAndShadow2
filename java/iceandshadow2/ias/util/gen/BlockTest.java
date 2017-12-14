package iceandshadow2.ias.util.gen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public abstract class BlockTest {
	public abstract boolean test(World w, int x, int y, int z, Block bl);
}
