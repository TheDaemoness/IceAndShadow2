package iceandshadow2.util.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockSelectAir extends BlockSelect {
	@Override
	public Block getBlock(World w, int x, int y, int z) {
		return Blocks.air;
	}

	@Override
	public int getMeta(World w, int x, int y, int z) {
		return 0;
	}
}
