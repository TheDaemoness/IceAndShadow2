package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBlockDeco;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSBlockHelper;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockIcicles extends IaSBlockDeco {
	public NyxBlockIcicles(String texName) {
		super(EnumIaSModule.NYX, texName, Material.glass);
		setLuminescence(0.1F);
		setLightColor(0.8F, 0.8F, 1.0F);
		setResistance(1.5F);
		setStepSound(Block.soundTypeGlass);
		this.slipperiness=2.0F;
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x,
			int y, int z, int metadata) {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_,
			int p_149655_3_, int p_149655_4_) {
		return true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
		return super.getSelectedBoundingBoxFromPool(w, x, y, z).contract(0.0, 0.2, 0.0);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w,
			int x, int y, int z) {
		return getCollisionBoundingBoxFromPool(w, x, y, z);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(NyxItems.icicle, 2+world.rand.nextInt(2), 0));
		return is;
	}
	
	@Override
	public void onEntityWalking(World w, int x, int y, int z, Entity e) {
		this.onFallenUpon(w, x, y, z, e, 0);
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity e,
			float distance) {
		super.onFallenUpon(world, x, y, z, e, 4+distance*2);
		IaSBlockHelper.breakBlock(world, x, y, z, false);
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 1;
	}
}
