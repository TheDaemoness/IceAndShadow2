package iceandshadow2.nyx.world.biome;

import java.util.Random;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.gen.GenPoisonTrees;
import iceandshadow2.util.IaSBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class NyxBiomeForestSparse extends NyxBiome {

	public NyxBiomeForestSparse(int par1, boolean register, float heightRoot,
			float heightVari, boolean isRare) {
		super(par1, register, heightRoot, heightVari, isRare);
		this.setBlocks(Blocks.snow, NyxBlocks.permafrost);
	}

	@Override
	protected void genFoliage(World par1World, Random par2Random, int xchunk,
			int zchunk) {

		for (int i = 0; i < 7; ++i) {
			final int x = xchunk + par2Random.nextInt(16) + 8;
			final int z = zchunk + par2Random.nextInt(16) + 8;
			int y;
			if (i % 2 == 0)
				y = 192;
			else
				y = 64;
			while (y >= 64 && y <= 192) {
				final Block bid = par1World.getBlock(x, y, z);
				if (bid == Blocks.snow_layer)
					break;
				if (i % 2 == 0) {
					if (!IaSBlockHelper.isAir(bid)) {
						++y;
						break;
					}
					--y;
				} else {
					if (IaSBlockHelper.isAir(bid))
						break;
					++y;
				}
			}
			if (y == 0)
				continue;
			final WorldGenerator var5 = this
					.getRandomWorldGenForTrees(par2Random);
			if (var5.generate(par1World, par2Random, x, y, z))
				++i;
		}
	}

	public WorldGenerator getRandomWorldGenForTrees(Random rand) {
		return new GenPoisonTrees();
	}
}
