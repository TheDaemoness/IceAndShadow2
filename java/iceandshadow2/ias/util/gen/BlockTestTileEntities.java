package iceandshadow2.ias.util.gen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockTestTileEntities extends BlockTest {
	@Override
	public boolean test(World w, int x, int y, int z, Block bl) {
		return w.getTileEntity(x, y, z) != null;
	}
}
