package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockLeaves;
import iceandshadow2.ias.interfaces.IIaSNoInfest;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.blocks.mixins.NyxBlockFunctionsPoisonwood;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockPoisonLeaves extends IaSBaseBlockLeaves implements IIaSNoInfest {

	private static Random r = new Random();

	public NyxBlockPoisonLeaves(String texName) {
		super(EnumIaSModule.NYX, texName);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.POISONWOOD;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		if (!world.isRemote) {
			is.add(new ItemStack(Items.stick, 1 + world.rand.nextInt(2 + fortune)));
			if (world.rand.nextInt(20) == 0)
				is.add(new ItemStack(NyxItems.poisonFruit));
		}
		return is;
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		NyxBlockFunctionsPoisonwood.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		NyxBlockFunctionsPoisonwood.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		final ArrayList<ItemStack> islist = new ArrayList<ItemStack>(1);
		islist.add(new ItemStack(NyxItems.leaf, 1));
		return islist;
	}

}
