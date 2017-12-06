package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBlockDeco;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSBlockHelper;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockCrystal extends IaSBlockDeco {
	public NyxBlockCrystal(String texName) {
		super(EnumIaSModule.NYX, texName, Material.glass);
		setStepSound(Block.soundTypeGlass);
		this.slipperiness = 2.0F;
	}

	@Override
	public boolean canPlaceBlockAt(World w, int x, int y, int z) {
		return super.canPlaceBlockAt(w, x, y, z) && w.getBlock(x, y-1, z).isSideSolid(w, x, y, z, ForgeDirection.UP);
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
		return super.getSelectedBoundingBoxFromPool(w, x, y, z).contract(0.0, 0.2, 0.0);
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {
		return getCollisionBoundingBoxFromPool(w, x, y, z);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void onEntityWalking(World w, int x, int y, int z, Entity e) {
		this.onFallenUpon(w, x, y, z, e, 0);
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity e, float distance) {
		super.onFallenUpon(world, x, y, z, e, distance);
		IaSBlockHelper.breakBlock(world, x, y, z, false);
	}
}
