package iceandshadow2.ias.util.gen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockTestUnbreakable extends BlockTest {
	@Override
	public boolean test(World w, int x, int y, int z, Block bl) {
		return bl.getBlockHardness(w, x, y, z) == -1;
	}
}
