package iceandshadow2.nyx.blocks;

import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxBlockRopeY extends NyxBlockRope {

	public NyxBlockRopeY(String texName) {
		super(texName);
		this.setBlockBounds(0.45F, 0.0F, 0.45F, 0.55F, 1.0F, 0.55F);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		bl = w.getBlock(x, y+1, z);
		if(bl instanceof NyxBlockRopeY || bl instanceof NyxBlockHookClimbing)
			return;
		w.setBlockToAir(x, y, z);
	}

	@Override
	public void onBlockClicked(World w, int x, int y, int z, EntityPlayer pl) {
		if(!pl.isSneaking())
			return;
		int origy = y;
		for(; y < 255; ++y) {
			if(w.getBlock(x, y, z) instanceof NyxBlockHookClimbing) {
				if(!w.isRemote) {
					if(origy+1 != y) {
						EntityItem eieio = new EntityItem(w, 0.5+x, y-0.25, 0.5+z, new ItemStack(NyxItems.rope));
						w.spawnEntityInWorld(eieio);
					} else
						IaSPlayerHelper.giveItem(pl, new ItemStack(NyxItems.rope));
					w.func_147480_a(x, y-1, z, false);
				}
			}
		}
	}

}
