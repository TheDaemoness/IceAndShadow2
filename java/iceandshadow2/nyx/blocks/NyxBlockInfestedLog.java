package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBlockDirectional;
import iceandshadow2.ias.interfaces.IIaSNoInfest;
import iceandshadow2.nyx.blocks.mixins.NyxBlockFunctionsInfested;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockInfestedLog extends IaSBlockDirectional implements
IIaSNoInfest {

	public NyxBlockInfestedLog(String par1) {
		super(EnumIaSModule.NYX, par1, Material.wood);
		setLuminescence(0.2F);
		setLightColor(0.0F, 1.0F, 0.7F);
		setHardness(7.5F);
		setResistance(3.0F);
		setStepSound(Block.soundTypeWood);
		setTickRandomly(true);
	}

	/**
	 * ejects contained items into the world, and notifies neighbours of an
	 * update, as appropriate
	 */
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		final byte var7 = 4;
		final int var8 = var7 + 1;

		if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8,
				par2 + var8, par3 + var8, par4 + var8)) {
			for (int var9 = -var7; var9 <= var7; ++var9) {
				for (int var10 = -var7; var10 <= var7; ++var10) {
					for (int var11 = -var7; var11 <= var7; ++var11) {
						final Block var12 = par1World.getBlock(par2 + var9,
								par3 + var10, par4 + var11);

						if (var12 != null)
							var12.beginLeavesDecay(par1World, par2 + var9, par3
									+ var10, par4 + var11);
					}
				}
			}
		}
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	/**
	 * Returns an item stack containing a single instance of the current block
	 * type. 'i' is the block's subtype/damage and is ignored for blocks which
	 * do not support subtypes. Blocks which cannot be harvested should return
	 * null.
	 */
	@Override
	protected ItemStack createStackedBlock(int par1) {
		return new ItemStack(this, 1, 0);
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and
	 * wood.
	 */
	@Override
	public int damageDropped(int par1) {
		return 0;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		return NyxBlockFunctionsInfested.getCollisionBoundingBoxFromPool(
				par1World, par2, par3, par4);
	}

	@Override
	public boolean isWood(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4,
			EntityPlayer par5EntityPlayer) {
		NyxBlockFunctionsInfested.onBlockClicked(par1World, par2, par3, par4,
				par5EntityPlayer);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {
		NyxBlockFunctionsInfested.onEntityCollidedWithBlock(par1World, par2,
				par3, par4, par5Entity);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		NyxBlockFunctionsInfested.updateTick(w, x, y, z, r);
	}

}
