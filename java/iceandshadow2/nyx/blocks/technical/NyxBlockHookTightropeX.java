package iceandshadow2.nyx.blocks.technical;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockHookTightropeX extends NyxBlockHookTightrope {

	public NyxBlockHookTightropeX(String texName) {
		super(texName);
		this.setBlockBounds(0.0F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		final ArrayList<ItemStack> is = super.getDrops(world, x, y, z, metadata,
				fortune);
		if (world.getBlock(x + 1, y, z) == NyxBlocks.ropeX)
			is.add(new ItemStack(NyxItems.rope));
		if (world.getBlock(x - 1, y, z) == NyxBlocks.ropeX)
			is.add(new ItemStack(NyxItems.rope));
		return is;
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block cock) {
		if (w.isSideSolid(x - 1, y, z, ForgeDirection.EAST))
			return;
		if (w.isSideSolid(x + 1, y, z, ForgeDirection.WEST))
			return;
		w.func_147480_a(x, y, z, true);
	}

}
