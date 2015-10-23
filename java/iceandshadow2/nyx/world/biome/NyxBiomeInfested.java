package iceandshadow2.nyx.world.biome;

import iceandshadow2.nyx.entities.mobs.EntityNyxGhoul;
import iceandshadow2.nyx.entities.mobs.EntityNyxSpider;
import iceandshadow2.nyx.world.gen.GenInfestedTrees;
import iceandshadow2.nyx.world.gen.ruins.GenRuins;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class NyxBiomeInfested extends NyxBiome {

	public NyxBiomeInfested(int par1, boolean register, float heightRoot,
			float heightVari, boolean isRare) {
		super(par1, register, heightRoot, heightVari, isRare);
		this.setBlocks(Blocks.snow, Blocks.snow);

		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityNyxSpider.class,
				60, 3, 5));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityNyxGhoul.class,
				10, 1, 1));

		this.setColor(64 << 16 | 255 << 8 | 192);
	}
	
	@Override
	protected boolean hasTowers() {
		return false;
	}
	
	@Override
	protected GenRuins supplyRuins() {
		return null;
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4) {
		super.decorate(par1World, par2Random, par3, par4);

		int count = 0;
		for (int i = 0; i < 32 && count < 3; ++i) {
			final int x = par3 + par2Random.nextInt(16) + 8;
			final int z = par4 + par2Random.nextInt(16) + 8;
			int y = 255;
			for (y = 255; y > 64; --y) {
				final Block bid = par1World.getBlock(x, y, z);
				if (bid == Blocks.snow) {
					++y;
					break;
				}
				if (bid == Blocks.snow_layer) {
					break;
				}
				if (!bid.isAir(par1World, x, y, z)) {
					y = 0;
					break;
				}
			}
			if (y == 0)
				continue;
			final WorldGenerator var5 = this.getRandomWorldGenForTrees(par2Random);
			if (var5.generate(par1World, par2Random, x, y, z))
				++count;
		}
	}

	public WorldGenerator getRandomWorldGenForTrees(Random rand) {
		return new GenInfestedTrees();
	}

}
