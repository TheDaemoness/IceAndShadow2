package iceandshadow2.nyx.world.gen;

import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenOre {

	/**
	 * Standard ore generation helper.
	 */
	public static void genOreStandard(WorldGenerator wgen, World par1World, int x, int z, int lower, int upper,
			int rarity) {
		for (int var5 = 0; var5 < rarity; ++var5) {
			final int var6 = x + par1World.rand.nextInt(16);
			final int var7 = par1World.rand.nextInt(upper - lower) + lower;
			final int var8 = z + par1World.rand.nextInt(16);
			wgen.generate(par1World, par1World.rand, var6, var7, var8);
			par1World.updateLightByType(EnumSkyBlock.Block, var6, var7, var8);
			par1World.updateLightByType(EnumSkyBlock.Sky, var6, var7, var8);
		}
	}

	public static void genOreSurface(Block bloque, World par1World, int xchunk, int zchunk) {
		final int x = xchunk + par1World.rand.nextInt(16);
		final int z = zchunk + par1World.rand.nextInt(16);
		int ybest = 0, ytarget = 64;
		boolean foundtop = false;

		for (int y = 255; y >= ytarget; --y)
			if (par1World.getBlock(x, y, z) == NyxBlocks.stone)
				if (!foundtop) {
					foundtop = true;
					ybest = y;
					ytarget = par1World.rand.nextInt(y - 63) + 64;
				} else // Check for bordering air.
				if (par1World.isAirBlock(x + 1, y, z) || par1World.isAirBlock(x - 1, y, z)
						|| par1World.isAirBlock(x, y, z + 1) || par1World.isAirBlock(x, y, z - 1)
						|| par1World.isAirBlock(x, y + 1, z) || par1World.isAirBlock(x, y - 1, z))
					ybest = y;
		if (foundtop)
			par1World.setBlock(x, ybest, z, bloque);

	}

	public static void genOreWater(Block bloque, World par1World, int xchunk, int zchunk, int rate) {
		for (int times = 0; times < rate; ++times) {

			final int x = xchunk + par1World.rand.nextInt(16);
			final int z = zchunk + par1World.rand.nextInt(16);
			for (int y = 60; y > 0; --y)
				// Check for a Nyx Stone block.
				if (par1World.getBlock(x, y, z) == NyxBlocks.stone)
					// Check for bordering water.
					if (par1World.getBlock(x + 1, y, z).getMaterial() == Material.water
							|| par1World.getBlock(x - 1, y, z).getMaterial() == Material.water
							|| par1World.getBlock(x, y, z + 1).getMaterial() == Material.water
							|| par1World.getBlock(x, y, z - 1).getMaterial() == Material.water) {
						par1World.setBlock(x, y, z, bloque);
						if (par1World.rand.nextInt(3) == 0)
							return;
					}
		}
	}

}
