package iceandshadow2.nyx.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class NyxBlockRopeZ extends NyxBlockRope {

	public NyxBlockRopeZ(String texName) {
		super(texName);
		this.setBlockBounds(0.45F, 0.45F, 0.0F, 0.55F, 0.55F, 1.0F);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		Block n = w.getBlock(x, y, z+1);
		if(!(n instanceof NyxBlockRopeZ) && !(n instanceof NyxBlockHookTightropeZ))
			w.setBlockToAir(x, y, z);
		n = w.getBlock(x, y, z-1);
		if(!(n instanceof NyxBlockRopeZ) && !(n instanceof NyxBlockHookTightropeZ))
			w.setBlockToAir(x, y, z);
	}

}
