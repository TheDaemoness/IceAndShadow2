package iceandshadow2.nyx.blocks.technical;

import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.blocks.NyxBlockHookClimbing;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class NyxBlockRopeY extends NyxBlockRope {

	public NyxBlockRopeY(String texName) {
		super(texName);
		setBlockBounds(0.45F, 0.0F, 0.45F, 0.55F, 1.0F, 0.55F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		final float var5 = 0.375F;
		return AxisAlignedBB.getBoundingBox(par2 + var5, par3, par4 + var5,
				par2 + 1 - var5, par3 + 1, par4 + 1 - var5);
	}

	@Override
	public void onBlockClicked(World w, int x, int y, int z, EntityPlayer pl) {
		if (!pl.isSneaking())
			return;
		for (; y < 255; ++y) {
			if (w.getBlock(x, y, z) instanceof NyxBlockHookClimbing) {
				IaSPlayerHelper.giveItem(pl, new ItemStack(NyxItems.rope));
				w.func_147480_a(x, y - 1, z, false);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		bl = w.getBlock(x, y + 1, z);
		if (bl instanceof NyxBlockRopeY || bl instanceof NyxBlockHookClimbing)
			return;
		w.setBlockToAir(x, y, z);
	}

}
