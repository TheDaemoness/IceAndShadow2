package iceandshadow2.nyx.world.biome;

import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.entities.mobs.EntityNyxWightToxic;
import iceandshadow2.nyx.world.gen.GenPoisonTrees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class NyxBiomeForestDense extends NyxBiome {

	public NyxBiomeForestDense(int par1, boolean register, float heightRoot, float heightVari, boolean isRare) {
		super(par1, register, heightRoot, heightVari, isRare);
		setBlocks(NyxBlocks.permafrost, NyxBlocks.permafrost);

		spawnableMonsterList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityNyxWightToxic.class, 40, 1, 1));
		
		setColor(127 << 16 | 32 << 8 | 127);
	}

	@Override
	protected void genFoliage(World par1World, Random par2Random, int xchunk, int zchunk) {

		for (int i = 0; i < 10; ++i) {
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
			final WorldGenerator var5 = getRandomWorldGenForTrees(par2Random);
			var5.generate(par1World, par2Random, x, y, z);
		}
	}

	public WorldGenerator getRandomWorldGenForTrees(Random rand) {
		return new GenPoisonTrees();
	}
}
