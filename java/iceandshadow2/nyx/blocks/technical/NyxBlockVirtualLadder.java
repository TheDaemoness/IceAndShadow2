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
	
	public static boolean testClimbable(IBlockAccess w, int x, int y, int z) {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if(w.getBlock(x+dir.offsetX, y, z+dir.offsetZ) instanceof IIaSBlockClimbable)
				return true;
		}
		return false;
	}
	
	public NyxBlockVirtualLadder(String texName) {
		super(EnumIaSModule.NYX, texName, Material.fire);
		setLightOpacity(0);
		this.setLightLevel(0.1F);
	}

	@Override
	public int tickRate(World p_149738_1_) {
		return 2;
	}

	@Override
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity e) {
		if(e instanceof EntityPlayer) {
			for(int xit = -1; xit <= 1; ++xit) {
				for(int yit = -1; yit <= 1; ++yit) {
					for(int zit = -1; zit <= 1; ++zit) {
						if(canPlaceBlockAt(w, x+xit,y+yit,z+zit)) 
							w.setBlock(x+xit, y+yit, z+zit, NyxBlocks.virtualLadder);
					}
				}
			}
		}
		super.onEntityCollidedWithBlock(w, x, y, z, e);
	}

	@Override
	public boolean canPlaceBlockAt(World w, int x, int y, int z) {
		if(!w.isAirBlock(x, y, z))
			return false;
		return testClimbable(w, x, y, z);
	}
	
	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		w.scheduleBlockUpdateWithPriority(x, y, z, this, tickRate(w), 2);
		super.onBlockAdded(w, x, y, z);
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		boolean byClimbable = testClimbable(w, x, y, z);
		boolean isUsed =
				w.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+2, y+2, z+2)).size() > 0;
		if(!byClimbable || !isUsed)
			w.setBlock(x, y, z, Blocks.air, 0, 3);
		else
			w.scheduleBlockUpdateWithPriority(x, y, z, this, tickRate(w), 2);
	}
	
	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
		if(!(entity instanceof EntityPlayer))
			return false;
		return testClimbable(world, x, y, z);
	}
}
