package iceandshadow2.nyx.world.biome;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class NyxBiomeRugged extends NyxBiome {

	public NyxBiomeRugged(int par1, boolean register, float heightRoot,
			float heightVari, boolean isRare) {
		super(par1, register, heightRoot, heightVari, isRare);
		this.setBlocks(NyxBlocks.stone, NyxBlocks.stone);
		this.doGenNifelhium = false;
		this.doGenUnstableIce = false;

		this.setColor(96 << 16 | 96 << 8 | 96);

		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(
				EntityNyxSkeleton.class, 60, 2, 3));
	}

	@Override
	public void decorate(World par1World, Random par2Random, int xchunk,
			int zchunk) {
		for (int xit = 0; xit < 16; ++xit) {
			for (int zit = 0; zit < 16; ++zit) {
				final int x = xchunk + xit + 8;
				final int z = zchunk + zit + 8;
				final int y = par1World.getTopSolidOrLiquidBlock(x, z);
				if (y > 72
						&& par1World.getBiomeGenForCoords(x, z) instanceof NyxBiomeRugged) {
					final int ydown = par2Random.nextInt(5);
					for (int yit = 0; yit < ydown; ++yit) {
						final Block bl = par1World.getBlock(x, y - yit, z);
						if (bl == Blocks.obsidian)
							continue;
						if (bl == NyxBlocks.gatestone)
							continue;
						par1World.setBlockToAir(x, y - yit, z);
					}
				}
			}
		}
		while(par2Random.nextBoolean()) {
			final int x = xchunk + par2Random.nextInt(16) + 8;
			final int z = zchunk + par2Random.nextInt(16) + 8;
			final int y = par1World.getTopSolidOrLiquidBlock(x, z);
			final Block bl = par1World.getBlock(x, y-1, z);
			if(par1World.getBiomeGenForCoords(x, z) instanceof NyxBiomeRugged) {
				if (bl != Blocks.obsidian && bl != NyxBlocks.gatestone && bl != NyxBlocks.exousicIce) {
					par1World.setBlock(x, y, z, NyxBlocks.crystalExousium);
					par1World.updateLightByType(EnumSkyBlock.Sky, x, y, z);
					break;
				}
			}
		}
		super.decorate(par1World, par2Random, xchunk, zchunk);
	}
}
