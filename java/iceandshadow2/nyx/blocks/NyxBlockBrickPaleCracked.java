package iceandshadow2.nyx.blocks;

import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class NyxBlockBrickPaleCracked extends NyxBlockBrickPale {
	public NyxBlockBrickPaleCracked(String id) {
		super(id);
		setResistance(NyxBlockStone.RESISTANCE / 2);
		setHardness(NyxBlockStone.HARDNESS / 2);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
		NyxBlocks.stone.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_); // Opportunism.
	}
}
