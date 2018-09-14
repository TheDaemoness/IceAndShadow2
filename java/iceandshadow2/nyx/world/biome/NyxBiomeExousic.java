package iceandshadow2.nyx.world.biome;

import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.blocks.NyxBlockStone;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class NyxBiomeExousic extends NyxBiome {

	public NyxBiomeExousic(int par1, boolean register, float heightRoot, float heightVari, boolean isRare) {
		super(par1, register, heightRoot, heightVari, isRare);
		setBlocks(Blocks.air, Blocks.air);
		doGenNifelhium = false;
		doGenUnstableIce = false;

		setColor(32 << 16 | 225 << 8 | 192);

		/*
		 * spawnableMonsterList.add(new SpawnListEntry(EntityNyxSkeleton.class, 65, 1,
		 * 3)); spawnableMonsterList.add(new SpawnListEntry(EntityNyxNecromancer.class,
		 * 5, 1, 1));
		 */
	}

	@Override
	public void decorate(World par1World, Random par2Random, int xchunk, int zchunk) {
		for (int xit = 0; xit < 16; ++xit)
			for (int zit = 0; zit < 16; ++zit) {
				final int x = xchunk + xit;
				final int z = zchunk + zit;
				final int y = IaSBlockHelper.getHeight(par1World, x, z);
				if (par1World.getBlock(x, y - 1, z) instanceof NyxBlockStone && par2Random.nextInt(12) == 0)
					if (par2Random.nextInt(16) == 0) {
						par1World.setBlock(x, y, z, NyxBlocks.exousicWater, 15, 3);
						par1World.setBlock(x, y - 1, z, NyxBlocks.oreExousium);
					} else
						par1World.setBlock(x, y, z, NyxBlocks.exousicWater, 0, 2);
			}
		super.decorate(par1World, par2Random, xchunk, zchunk);
	}

	@Override
	protected boolean hasTowers() {
		return false;
	}
}
