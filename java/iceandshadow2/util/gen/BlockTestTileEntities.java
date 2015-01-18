package iceandshadow2.util.gen;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockTestTileEntities extends BlockTest {
	public boolean test(World w, int x, int y, int z, Block bl) {
		return w.getTileEntity(x, y, z) != null;
	}
}
