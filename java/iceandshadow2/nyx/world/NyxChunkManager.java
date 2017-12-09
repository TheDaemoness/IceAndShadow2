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
	private final GenLayer genBiomes;

	/** A GenLayer containing the indices into BiomeGenBase.biomeList[] */
	private final GenLayer biomeIndexLayer;

	/** The BiomeCache object for this world. */
	private final BiomeCache biomeCache;

	public NyxChunkManager(BiomeGenBase[] biomesToGen, GenLayer genBiomes, GenLayer biomeIndexLayer, World par1World) {
		biomeGenList = new BiomeGenBase[256];

		for (int i = 0; i < biomesToGen.length; ++i)
			biomeGenList[biomesToGen[i].biomeID] = biomesToGen[i];

		this.genBiomes = genBiomes;
		this.biomeIndexLayer = biomeIndexLayer;
		biomeCache = new BiomeCache(this);
	}

	/**
	 * checks given Chunk's Biomes against List of allowed ones
	 */
	@Override
	public boolean areBiomesViable(int par1, int par2, int par3, List par4List) {
		IntCache.resetIntCache();
		final int var5 = par1 - par3 >> 2;
		final int var6 = par2 - par3 >> 2;
		final int var7 = par1 + par3 >> 2;
		final int var8 = par2 + par3 >> 2;
		final int var9 = var7 - var5 + 1;
		final int var10 = var8 - var6 + 1;
		final int[] var11 = genBiomes.getInts(var5, var6, var9, var10);

		for (int var12 = 0; var12 < var9 * var10; ++var12) {
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
	public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random) {
		if (par4List == null)
			return null;

		IntCache.resetIntCache();
		final int var6 = par1 - par3 >> 2;
		final int var7 = par2 - par3 >> 2;
		final int var8 = par1 + par3 >> 2;
		final int var9 = par2 + par3 >> 2;
		final int var10 = var8 - var6 + 1;
		final int var11 = var9 - var7 + 1;
		final int[] var12 = genBiomes.getInts(var6, var7, var10, var11);
		ChunkPosition var13 = null;
		int var14 = 0;

		for (int var15 = 0; var15 < var10 * var11; ++var15) {
			final int var16 = var6 + var15 % var10 << 2;
			final int var17 = var7 + var15 / var10 << 2;
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
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5,
			boolean par6) {
		IntCache.resetIntCache();

		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
			par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];

		if (par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0) {
			final BiomeGenBase[] var9 = biomeCache.getCachedBiomes(par2, par3);
			System.arraycopy(var9, 0, par1ArrayOfBiomeGenBase, 0, par4 * par5);
			return par1ArrayOfBiomeGenBase;
		} else {
			final int[] var7 = biomeIndexLayer.getInts(par2, par3, par4, par5);

			for (int var8 = 0; var8 < par4 * par5; ++var8)
				par1ArrayOfBiomeGenBase[var8] = biomeGenList[var7[var8]];

			return par1ArrayOfBiomeGenBase;
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
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int x, int z, int xlim,
			int zlim) {
		IntCache.resetIntCache();

		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < xlim * zlim)
			par1ArrayOfBiomeGenBase = new BiomeGenBase[xlim * zlim];

		final int[] var6 = genBiomes.getInts(x, z, xlim, zlim);

		for (int var7 = 0; var7 < xlim * zlim; ++var7)
			par1ArrayOfBiomeGenBase[var7] = biomeGenList[var6[var7]];

		return par1ArrayOfBiomeGenBase;
	}

	@Override
	public List getBiomesToSpawnIn() {
		return new ArrayList<BiomeGenBase>();
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
		IntCache.resetIntCache();

		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
			par1ArrayOfFloat = new float[par4 * par5];

		final int[] var6 = biomeIndexLayer.getInts(par2, par3, par4, par5);

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
		return par1;
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
