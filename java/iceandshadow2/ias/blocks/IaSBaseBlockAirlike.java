package iceandshadow2.ias.blocks;

import java.util.ArrayList;
import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.IIaSBlockClimbable;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/***
 * Parent class of all Nyxian air blocks.
 * Note that isAirBlock is NOT set. This is deliberate.
 * Also used to implement virtual ladders.
 * WARNING: Bit 8 of metadata is pinned and will frequently be set or unset. DO NOT RELY ON THIS NOT BEING THE CASE.
 */
public class IaSBaseBlockAirlike extends IaSBaseBlockTechnical {
	
	public static boolean testClimbable(IBlockAccess w, int x, int y, int z) {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if(w.getBlock(x+dir.offsetX, y, z+dir.offsetZ) instanceof IIaSBlockClimbable)
				return true;
		}
		return false;
	}

	public static void makeClimbable(World w, int x, int y, int z) {
		final Block bl = w.getBlock(x, y, z);
		if(bl == Blocks.air)
			w.setBlock(x, y, z, NyxBlocks.virtualLadder);
		else if (bl instanceof IaSBaseBlockAirlike)
			((IaSBaseBlockAirlike)bl).setClimbable(w, x, y, z, true);
	}
	
	public static void spreadClimbable(World w, int x, int y, int z) {
		for(int xit = -1; xit <= 1; ++xit) {
			for(int yit = -1; yit <= 1; ++yit) {
				for(int zit = -1; zit <= 1; ++zit) {
					if(testClimbable(w, x+xit, y+yit, z+zit))
						makeClimbable(w, x+xit, y+yit, z+zit);
				}
			}
		}
	}
	
	public IaSBaseBlockAirlike(EnumIaSModule mod, String texName) {
		super(mod, texName, new MaterialTransparent(MapColor.airColor));
		this.setBlockBounds(0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F);
        this.disableStats();   
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		if(w.getBlockMetadata(x, y, z)<8)
			return;
		boolean byClimbable = testClimbable(w, x, y, z);
		boolean isUsed = w.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+2, y+2, z+2)).size() > 0;
		if(byClimbable && isUsed)
			w.scheduleBlockUpdateWithPriority(x, y, z, this, tickRate(w), 2);
		else
			this.setClimbable(w, x, y, z, false);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity e) {
		if(w.getBlockMetadata(x, y, z) < 8)
			return;
		if(e instanceof EntityPlayer) {
			spreadClimbable(w, x, y, z);
		}
	}
	
	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
		if(world.getBlockMetadata(x, y, z)<8)
			return false;
		if(!(entity instanceof EntityPlayer) && (entity.isCollidedHorizontally || entity.isSneaking()))
			return false;
		return testClimbable(world, x, y, z);
	}

	//Override if the block needs to do something special upon changing climbability.
	public void setClimbable(World w, int x, int y, int z, boolean yes) {
		w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, x)&7 | (yes?8:0), 2);
		if(yes)
			w.scheduleBlockUpdateWithPriority(x, y, z, this, 3, 2);
	}

	@Override
	public boolean canCollideCheck(int meta, boolean p_149678_2_) {
		return false;
	}

	@Override
	public boolean canDropFromExplosion(Explosion p_149659_1_) {
		return false;
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_,
			Vec3 p_149731_5_, Vec3 p_149731_6_) {
		return Blocks.air.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_,
				p_149731_6_);
	}

	// Apparently this stops updates during chunk generation, which should speed
	// things up.
	@Override
	public boolean func_149698_L() {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_,
			int p_149668_4_) {
		return null;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>(0);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + EnumIaSModule.IAS.prefix + "Invisible";
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_,
			int p_149646_5_) {
		return false;
	}
}
