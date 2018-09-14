package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.util.EntityOrbNourishment;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockCrystalBloodstone extends NyxBlockCrystal {

	public NyxBlockCrystalBloodstone(String texName) {
		super(texName);
		setLuminescence(0.2F);
		setLightColor(0.5F, 0.0F, 0.0F);
		setResistance(1.5F);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.STYX;
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

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity e, float distance) {
		if (e instanceof EntityPlayer)
			IaSPlayerHelper.drainXP((EntityPlayer) e, 20, "The bloodstone drains you.", true);
		super.onFallenUpon(world, x, y, z, e, distance);
	}
}
