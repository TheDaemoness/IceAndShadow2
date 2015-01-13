package iceandshadow2.nyx.blocks.ropes;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockHookTightropeZ extends NyxBlockHookTightrope {

	public NyxBlockHookTightropeZ(String texName) {
		super(texName);
		this.setBlockBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 1.0F);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> is = super.getDrops(world, x, y, z, metadata, fortune);
		if(world.getBlock(x, y, z+1) == NyxBlocks.ropeZ)
			is.add(new ItemStack(NyxItems.rope));
		if(world.getBlock(x, y, z-1) == NyxBlocks.ropeZ)
			is.add(new ItemStack(NyxItems.rope));
		return is;
	}
	
	@Override
	public void onNeighborBlockChange(World w, int x,
			int y, int z, Block cock) {
		if(w.isSideSolid(x, y, z-1, ForgeDirection.SOUTH))
			return;
		if(w.isSideSolid(x, y, z+1, ForgeDirection.NORTH))
			return;
		w.func_147480_a(x, y, z, true);
	}

}
