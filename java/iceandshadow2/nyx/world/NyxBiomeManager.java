package iceandshadow2.nyx.world;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import iceandshadow2.IaSExecutor;
import iceandshadow2.IaSFuture;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * A cache for Nyx's biome selection system, which replaces the old
 * GenLayers-based system.
 */
public class NyxBiomeManager {
	private static final NyxBiomeManager obj = new NyxBiomeManager();

	public static BiomeGenBase getBiomeAt(BiomeGenBase[] biomes, int x, int z) {
		return biomes[x & 15 | (z & 15) << 4];
	}

	public static NyxBiomeManager instance() {
		return obj;
	}

	public static long key(final int xchunk, final int zchunk) {
		return Integer.toUnsignedLong(xchunk) | (Integer.toUnsignedLong(zchunk) << 32);
	}

	Map<Long, IaSFuture<BiomeGenBase[]>> pregenerated;

	private NyxBiomeManager() {
		pregenerated = new TreeMap<Long, IaSFuture<BiomeGenBase[]>>();
	}

	public NyxBiomeManager clean() {
		NyxBiomeProvider.clean();
		synchronized (pregenerated) {
			pregenerated.clear();
		}
		return this;
	}

	public NyxBiomeManager clean(int xchunk, int zchunk) {
		synchronized (pregenerated) {
			pregenerated.remove(key(xchunk, zchunk));
		}
		return this;
	}

	protected BiomeGenBase[] generateArray(long seed, final BiomeGenBase[] biomeArray, long xChunk, long zChunk) {
		NyxBiomeProvider.fillArray(seed, biomeArray, (int) xChunk, (int) zChunk);
		return biomeArray;
	}

	public IaSFuture<BiomeGenBase[]> getBiomeArray(final long seed, final int xchunk, final int zchunk) {
		final long key = key(xchunk, zchunk);
		synchronized (pregenerated) {
			IaSFuture<BiomeGenBase[]> biomeArray = pregenerated.get(key);
			if (biomeArray == null) {
				class DoGenBiome implements Callable<BiomeGenBase[]> {
					@Override
					public BiomeGenBase[] call() {
						final BiomeGenBase[] array = new BiomeGenBase[256];
						return generateArray(seed, array, xchunk, zchunk);
					}
				}
				;
				biomeArray = IaSExecutor.push(new DoGenBiome());
				pregenerated.put(key, biomeArray);
			}
			return biomeArray;
		}
	}

	public BiomeGenBase getBiomeAt(long seed, long x, long z) {
		return getBiomeAt(getBiomeArray(seed, (int) (x >> 4), (int) (z >> 4)).get(), (int) x, (int) z);
	}
}
