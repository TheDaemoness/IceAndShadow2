package iceandshadow2.ias.util.gen;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockTestAir extends BlockTest {
	@Override
	public boolean test(World w, int x, int y, int z, Block bl) {
		return bl.getMaterial() == Material.air;
	}
}
