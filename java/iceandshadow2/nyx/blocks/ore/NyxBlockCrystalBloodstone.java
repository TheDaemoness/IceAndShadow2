package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBlockDeco;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.util.EntityOrbNourishment;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockCrystalBloodstone extends IaSBlockDeco {

	public NyxBlockCrystalBloodstone(String texName) {
		super(EnumIaSModule.NYX, texName, Material.dragonEgg);
		setLuminescence(0.2F);
		setLightColor(0.5F, 0.0F, 0.0F);
		setResistance(1.5F);
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(NyxItems.bloodstone, 1, 0));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 9;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {
		return super.getSelectedBoundingBoxFromPool(w, x, y, z).contract(0.1, 0.15, 0.1).offset(0.0, -0.15, 0.0);
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldObj, int x, int y, int z, int q) {
		if (!worldObj.isRemote)
			worldObj.spawnEntityInWorld(new EntityOrbNourishment(worldObj, x + 0.5, y + 0.25, z + 0.5, 5));
		super.onBlockDestroyedByPlayer(worldObj, x, y, z, q);
	}
}
