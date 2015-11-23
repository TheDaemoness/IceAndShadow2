package iceandshadow2.nyx.blocks.technical;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class NyxBlockRopeZ extends NyxBlockRope {

	public NyxBlockRopeZ(String texName) {
		super(texName);
		setBlockBounds(0.45F, 0.45F, 0.0F, 0.55F, 0.55F, 1.0F);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		Block n = w.getBlock(x, y, z + 1);
		if (!(n instanceof NyxBlockRopeZ)
				&& !(n instanceof NyxBlockHookTightropeZ)) {
			w.setBlockToAir(x, y, z);
			return;
		}
		n = w.getBlock(x, y, z - 1);
		if (!(n instanceof NyxBlockRopeZ)
				&& !(n instanceof NyxBlockHookTightropeZ))
			w.setBlockToAir(x, y, z);
	}

}
