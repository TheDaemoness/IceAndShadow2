package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockLeaves;
import iceandshadow2.ias.interfaces.IIaSNoInfest;
import iceandshadow2.nyx.blocks.mixins.NyxBlockFunctionsInfested;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockInfestedLeaves extends IaSBaseBlockLeaves implements IIaSNoInfest {

	private static Random r = new Random();

	public NyxBlockInfestedLeaves(String texName) {
		super(EnumIaSModule.NYX, texName);
		setLightLevel(0.1F);
		setLightOpacity(4);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.INFESTATION;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return NyxBlockFunctionsInfested.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		return new ArrayList<ItemStack>();
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		NyxBlockFunctionsInfested.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		NyxBlockFunctionsInfested.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		return super.onSheared(item, world, x, y, z, fortune);
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		NyxBlockFunctionsInfested.updateTick(w, x, y, z, r);
	}

}
