package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreDevora extends NyxBlockOre {

	public NyxBlockOreDevora(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 0);
		this.setHardness(4.0F);
		this.setLuminescence(0.3F);
		this.setLightColor(1.0F, 1.0F, 0.75F);
		this.setResistance(1.0F);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		final int e = 1 + world.rand.nextInt(2 + fortune);
		for (int i = 0; i < e; ++i)
			is.add(new ItemStack(NyxItems.devora, 1, 1));
		if (e < 2 + fortune || world.rand.nextBoolean())
			is.add(new ItemStack(NyxItems.devora, 1));
		else
			is.add(new ItemStack(NyxItems.devora, 2, 1));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 1;
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z,
			Explosion explosion) {
		if (!world.isRemote) {
			world.setBlockToAir(x, y, z);
			world.createExplosion(explosion.exploder, 0.5 + x, 0.5 + y,
					0.5 + z, 2.5F, true);
		}
	}

	@Override
	public void randomDisplayTick(World par1World, int par2, int par3,
			int par4, Random par5Random) {
		if (par5Random.nextBoolean())
			return;
		final double var9 = par3 + par5Random.nextFloat();
		double var13 = 0.0D;
		double var15 = 0.0D;
		double var17 = 0.0D;
		final int var19 = par5Random.nextInt(2) * 2 - 1;
		final int var20 = par5Random.nextInt(2) * 2 - 1;
		var13 = (par5Random.nextFloat() - 0.5D) * 0.125D;
		var15 = (par5Random.nextFloat() - 0.5D) * 0.125D;
		var17 = (par5Random.nextFloat() - 0.5D) * 0.125D;
		final double var11 = par4 + 0.5D + 0.25D * var20;
		var17 = par5Random.nextFloat() * 1.0F * var20;
		final double var7 = par2 + 0.5D + 0.25D * var19;
		var13 = par5Random.nextFloat() * 1.0F * var19;
		par1World.spawnParticle("lava", var7, var9, var11, var13, var15, var17);
	}

}
