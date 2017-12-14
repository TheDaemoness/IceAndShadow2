package iceandshadow2.ias.util.gen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockSelectOne extends BlockSelect {
	protected Block bl;
	protected int met;

	public BlockSelectOne(Block ock, int ro) {
		bl = ock;
		met = ro;
	}

	@Override
	public Block getBlock(World w, int x, int y, int z) {
		return bl;
	}

	@Override
	public int getMeta(World w, int x, int y, int z) {
		return met;
	}
}
