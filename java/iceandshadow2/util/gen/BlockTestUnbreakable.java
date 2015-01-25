package iceandshadow2.util.gen;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockTestUnbreakable extends BlockTest {
	public boolean test(World w, int x, int y, int z, Block bl) {
		return bl.getBlockHardness(w, x, y, z) == -1;
	}
}
