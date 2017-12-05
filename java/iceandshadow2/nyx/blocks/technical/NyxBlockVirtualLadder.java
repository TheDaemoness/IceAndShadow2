package iceandshadow2.nyx.blocks.technical;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSBlockClimbable;
import iceandshadow2.ias.blocks.IaSBaseBlockAirlike;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.blocks.NyxBlockStone;
import iceandshadow2.util.IaSBlockHelper;

public class NyxBlockVirtualLadder extends IaSBaseBlockAirlike /*DO NOT MARK CLIMBABLE!*/ {
	
	public NyxBlockVirtualLadder(String texName) {
		super(EnumIaSModule.NYX, texName);
		setLightOpacity(0);
	}

	@Override
	public int tickRate(World p_149738_1_) {
		return 2;
	}
	
	@Override
	public void setClimbable(World w, int x, int y, int z, boolean yes) {
		if(yes)
			super.setClimbable(w, x, y, z, yes);
		else
			w.setBlockToAir(x, y, z);
	}

	@Override
	public boolean canPlaceBlockAt(World w, int x, int y, int z) {
		if(!w.isAirBlock(x, y, z))
			return false;
		return testClimbable(w, x, y, z);
	}
	
	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		this.setClimbable(w, x, y, z, true);
		super.onBlockAdded(w, x, y, z);
	}
}
