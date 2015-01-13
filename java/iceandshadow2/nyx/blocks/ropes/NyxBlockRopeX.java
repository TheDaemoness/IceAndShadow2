package iceandshadow2.nyx.blocks.ropes;

import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxBlockRopeX extends NyxBlockRope {

	public NyxBlockRopeX(String texName) {
		super(texName);
		this.setBlockBounds(0.0F, 0.45F, 0.45F, 1.0F, 0.55F, 0.55F);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		Block n = w.getBlock(x+1, y, z);
		if(!(n instanceof NyxBlockRopeX) && !(n instanceof NyxBlockHookTightropeX)) {
			w.setBlockToAir(x, y, z);
			return;
		}
		n = w.getBlock(x-1, y, z);
		if(!(n instanceof NyxBlockRopeX) && !(n instanceof NyxBlockHookTightropeX))
			w.setBlockToAir(x, y, z);
	}

}
