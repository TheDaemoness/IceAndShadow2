package iceandshadow2.nyx.blocks.ropes;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockHookTightropeX extends NyxBlockHookTightrope {

	public NyxBlockHookTightropeX(String texName) {
		super(texName);
		this.setBlockBounds(0.0F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
	}

	@Override
	public void onNeighborBlockChange(World w, int x,
			int y, int z, Block cock) {
		if(w.isSideSolid(x-1, y, z, ForgeDirection.EAST))
			return;
		if(w.isSideSolid(x+1, y, z, ForgeDirection.WEST))
			return;
		w.func_147480_a(x, y, z, true);
	}

}
