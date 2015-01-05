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
		this.setLuminescence(0.4F);
		this.setLightColor(1.0F, 1.0F, 0.75F);
		this.setResistance(1.0F);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		int e = world.rand.nextInt(5);
		for(int i = 0; i < e; ++i)
			is.add(new ItemStack(NyxItems.devora,1,1));
		if(world.rand.nextInt(Math.max(1,5-world.rand.nextInt(Math.min(1,fortune+1)))) == 0)
			is.add(new ItemStack(NyxItems.devora,1));
		return is;
	}
	
	@Override
	public void onBlockExploded(World world, int x, int y, int z,
			Explosion explosion) {
		if(!world.isRemote) {
			world.setBlockToAir(x, y, z);
			world.createExplosion(explosion.exploder, 0.5+x, 0.5+y, 0.5+z, 2.5F, true);
		}
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 1;
	}

	@Override
	public void randomDisplayTick(World par1World, int par2,
			int par3, int par4, Random par5Random) {
		if(par5Random.nextBoolean())
			return;
		double var9 = (double)((float)par3 + par5Random.nextFloat());
		double var13 = 0.0D;
		double var15 = 0.0D;
		double var17 = 0.0D;
		int var19 = par5Random.nextInt(2) * 2 - 1;
		int var20 = par5Random.nextInt(2) * 2 - 1;
		var13 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
		var15 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
		var17 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
		double var11 = (double)par4 + 0.5D + 0.25D * (double)var20;
		var17 = (double)(par5Random.nextFloat() * 1.0F * (float)var20);
		double var7 = (double)par2 + 0.5D + 0.25D * (double)var19;
		var13 = (double)(par5Random.nextFloat() * 1.0F * (float)var19);
		par1World.spawnParticle("lava", var7, var9, var11, var13, var15, var17);
	}
	
	
}
