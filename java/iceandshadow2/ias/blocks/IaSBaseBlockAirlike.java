package iceandshadow2.ias.blocks;

import java.util.ArrayList;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class IaSBaseBlockAirlike extends IaSBaseBlockTechnical {
	public IaSBaseBlockAirlike(EnumIaSModule mod, String texName, Material mat) {
		super(mod, texName, mat);
		this.setBlockBounds(0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F);	
	}
	
	@Override
	public boolean canCollideCheck(int meta, boolean p_149678_2_) {
		return false;
	}
	
	//Apparently this stops updates during chunk generation, which should speed things up.
    @Override
    public boolean func_149698_L() {
        return false;
    }
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_,
			int p_149646_5_) {
		return false;
	}

	@Override
	public boolean canDropFromExplosion(Explosion p_149659_1_) {
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
	public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_,
			Vec3 p_149731_5_, Vec3 p_149731_6_) {
		return Blocks.air.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
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
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return false;
	}
}
