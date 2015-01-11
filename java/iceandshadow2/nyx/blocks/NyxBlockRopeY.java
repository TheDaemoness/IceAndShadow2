package iceandshadow2.nyx.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class NyxBlockRopeY extends NyxBlockRope {

	public NyxBlockRopeY(String texName) {
		super(texName);
		this.setBlockBounds(0.45F, 0.0F, 0.45F, 0.55F, 1.0F, 0.55F);
	}

	@Override
	public void onFallenUpon(World w, int x, int y, int z, Entity tree,
			float dist) {
		return;
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		// TODO Auto-generated method stub

	}

}
