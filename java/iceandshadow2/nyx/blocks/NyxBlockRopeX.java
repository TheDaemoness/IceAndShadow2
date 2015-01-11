package iceandshadow2.nyx.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class NyxBlockRopeX extends NyxBlockRope {

	public NyxBlockRopeX(String texName) {
		super(texName);
		this.setBlockBounds(0.0F, 0.45F, 0.45F, 1.0F, 0.55F, 0.55F);
	}

	@Override
	public void onFallenUpon(World w, int x, int y, int z, Entity tree,
			float dist) {
		tree.setPosition(tree.posX, tree.posY-0.1, tree.posZ);
		tree.addVelocity(0.0, 0.0, w.rand.nextDouble()*0.1F+0.05);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		// TODO Auto-generated method stub

	}

}
