package iceandshadow2.nyx.world.gen;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.util.IaSBlockHelper;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenPoisonTrees extends WorldGenerator {

	@Override
	public boolean generate(World par1World, Random par2Random, int x, int y, int z) {
		final int var6 = par2Random.nextInt(4) + 6;
		final int var7 = 1 + par2Random.nextInt(2);
		final int var8 = var6 - var7;
		final int var9 = 2 + par2Random.nextInt(2);
		boolean var10 = true;

		if (y >= 1 && y + var6 + 1 <= 256) {
			int var11;
			int var13;
			int var21;
			int var15;

			for (var11 = y; var11 <= y + 1 + var6 && var10; ++var11) {
				if (var11 - y < var7) {
					var21 = 0;
				} else {
					var21 = var9;
				}

				for (var13 = x - var21; var13 <= x + var21 && var10; ++var13) {
					for (int var14 = z - var21; var14 <= z + var21 && var10; ++var14) {
						if (var11 >= 0 && var11 < 256) {
							final Block block = par1World.getBlock(var13, var11, var14);

							if (!par1World.isAirBlock(var13, var11, var14)
									&& !block.isLeaves(par1World, var13, var11, var14))
								var10 = false;
						} else {
							var10 = false;
						}
					}
				}
			}

			if (!var10) {
				return false;
			} else {
				final Block bl = par1World.getBlock(x, y - 1, z);

				if ((bl == Blocks.snow || bl == NyxBlocks.permafrost) && y < 256 - var6 - 1) {
					var21 = par2Random.nextInt(2);
					var13 = 1;
					byte var22 = 0;
					int var17;
					int var16;

					for (var15 = 0; var15 <= var8; ++var15) {
						var16 = y + var6 - var15;

						for (var17 = x - var21; var17 <= x + var21; ++var17) {
							final int var18 = var17 - x;

							for (int var19 = z - var21; var19 <= z + var21; ++var19) {
								final int var20 = var19 - z;

								final Block block = par1World.getBlock(var17, var16, var19);

								if ((Math.abs(var18) != var21 || Math.abs(var20) != var21 || var21 <= 0)
										&& (block == null
												|| block.canBeReplacedByLeaves(par1World, var17, var16, var19))) {
									par1World.setBlock(var17, var16, var19, NyxBlocks.poisonLeaves);
								}
							}
						}

						if (var21 >= var13) {
							var21 = var22;
							var22 = 1;
							++var13;

							if (var13 > var9) {
								var13 = var9;
							}
						} else {
							++var21;
						}
					}

					var15 = par2Random.nextInt(3);

					for (var16 = 0; var16 < var6 - var15; ++var16) {
						final Block b = par1World.getBlock(x, y + var16, z);
						if (IaSBlockHelper.isAir(b) || b == NyxBlocks.poisonLeaves)
							par1World.setBlock(x, y + var16, z, NyxBlocks.poisonLog);
					}

					return true;
				} else
					return false;
			}
		} else
			return false;
	}

}
