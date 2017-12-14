package iceandshadow2.nyx.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxChunkManager extends WorldChunkManager {
	public BiomeGenBase[] biomeGenList;
	// private final GenLayer genBiomes;

	/** A GenLayer containing the indices into BiomeGenBase.biomeList[] */
	private final GenLayer biomeIndexLayer;

	/** The BiomeCache object for this world. */
	private final BiomeCache biomeCache;

	public NyxChunkManager(BiomeGenBase[] biomesToGen, GenLayer genBiomes, GenLayer biomeIndexLayer, World par1World) {
		biomeGenList = new BiomeGenBase[256];

		for (int i = 0; i < biomesToGen.length; ++i)
			biomeGenList[biomesToGen[i].biomeID] = biomesToGen[i];

		// this.genBiomes = genBiomes;
		this.biomeIndexLayer = biomeIndexLayer;
		biomeCache = new BiomeCache(this);
	}

	/**
	 * checks given Chunk's Biomes against List of allowed ones
	 */
	@Override
	public boolean areBiomesViable(int x, int z, int width, List par4List) {
		final int var5 = x - width >> 2;
		final int var6 = z - width >> 2;
		final int var7 = x + width >> 2;
		final int var8 = z + width >> 2;
		final int xlim = var7 - var5 + 1;
		final int zlim = var8 - var6 + 1;
		final int[] var11 = getInts(var5, var6, xlim, zlim);

		for (int var12 = 0; var12 < xlim * zlim; ++var12) {
			final BiomeGenBase var13 = biomeGenList[var11[var12]];

			if (!par4List.contains(var13))
				return false;
		}

		return true;
	}

	/**
	 * Calls the WorldChunkManager's biomeCache.cleanupCache()
	 */
	@Override
	public void cleanupCache() {
		biomeCache.cleanupCache();
	}

	/**
	 * Finds a valid position within a range, that is in one of the listed
	 * biomes. Searches {par1,par2} +-par3 blocks. Strongly favors positive y
	 * positions.
	 */
	@Override
	public ChunkPosition findBiomePosition(int x, int z, int width, List par4List, Random par5Random) {
		if (par4List == null)
			return null;

		final int var6 = x - width >> 2;
		final int var7 = z - width >> 2;
		final int var8 = x + width >> 2;
		final int var9 = z + width >> 2;
		final int xlim = var8 - var6 + 1;
		final int zlim = var9 - var7 + 1;
		final int[] var12 = getInts(var6, var7, xlim, zlim);
		ChunkPosition var13 = null;
		int var14 = 0;

		for (int var15 = 0; var15 < xlim * zlim; ++var15) {
			final int var16 = var6 + var15 % xlim << 2;
			final int var17 = var7 + var15 / xlim << 2;
			final BiomeGenBase var18 = biomeGenList[var12[var15]];
			if (var18 == null)
				continue;

			if (par4List.contains(var18) && (var13 == null || par5Random.nextInt(var14 + 1) == 0)) {
				var13 = new ChunkPosition(var16, 0, var17);
				++var14;
			}
		}

		return var13;
	}

	/**
	 * Return a list of biomes for the specified blocks. Args: listToReuse, x,
	 * y, width, length, cacheFlag (if false, don't check biomeCache to avoid
	 * infinite loop in BiomeCacheBlock)
	 */
	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomeArray, int x, int z, int xwidth, int zwidth,
			boolean useCache) {

		if (biomeArray == null || biomeArray.length < xwidth * zwidth)
			biomeArray = new BiomeGenBase[xwidth * zwidth];

		if (useCache && xwidth == 16 && zwidth == 16 && (x & 15) == 0 && (z & 15) == 0) {
			final BiomeGenBase[] var9 = biomeCache.getCachedBiomes(x, z);
			System.arraycopy(var9, 0, biomeArray, 0, xwidth * zwidth);
			return biomeArray;
		} else {
			final int[] biomes = getInts(x, z, xwidth, zwidth);

			for (int i = 0; i < xwidth * zwidth; ++i)
				biomeArray[i] = biomeGenList[biomes[i]];

			return biomeArray;
		}
	}

	/**
	 * Returns the BiomeGenBase related to the x, z position on the world.
	 */
	@Override
	public BiomeGenBase getBiomeGenAt(int par1, int par2) {
		return biomeCache.getBiomeGenAt(par1, par2);
	}

	/**
	 * Returns an array of biomes for the location input.
	 */
	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int x, int z, int xlim, int zlim) {

		if (biomes == null || biomes.length < xlim * zlim)
			biomes = new BiomeGenBase[xlim * zlim];

		final int[] biomeInts = getInts(x, z, xlim, zlim);

		for (int i = 0; i < xlim * zlim; ++i)
			biomes[i] = biomeGenList[biomeInts[i]];

		return biomes;
	}

	@Override
	public List getBiomesToSpawnIn() {
		return new ArrayList<BiomeGenBase>();
	}

	protected int[] getInts(int x, int z, int xlim, int zlim) {
		IntCache.resetIntCache();
		return biomeIndexLayer.getInts(x, z, xlim, zlim);
	}

	@Override
	public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
		return original;
	}

	/**
	 * Returns a list of rainfall values for the specified blocks. Args:
	 * listToReuse, x, z, width, length.
	 */
	@Override
	public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {

		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
			par1ArrayOfFloat = new float[par4 * par5];

		final int[] var6 = getInts(par2, par3, par4, par5);

		for (int var7 = 0; var7 < par4 * par5; ++var7) {
			float var8 = biomeGenList[var6[var7]].getIntRainfall() / 65536.0F;

			if (var8 > 1.0F)
				var8 = 1.0F;

			par1ArrayOfFloat[var7] = var8;
		}

		return par1ArrayOfFloat;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Return an adjusted version of a given temperature based on the y height
	 */
	@Override
	public float getTemperatureAtHeight(float par1, int par2) {
		return 0;
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like
	 * temperature and humidity onto the WorldChunkManager Args: oldBiomeList,
	 * x, z, width, depth
	 */
	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4,
			int par5) {
		return this.getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
	}
}
