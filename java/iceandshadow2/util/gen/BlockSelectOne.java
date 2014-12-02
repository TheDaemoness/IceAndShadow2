package iceandshadow2.util.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
