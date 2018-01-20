package iceandshadow2.nyx.world;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import iceandshadow2.IaSExecutor;
import iceandshadow2.IaSFuture;
import iceandshadow2.ias.util.BlockPos2;
import iceandshadow2.ias.util.IaSWorldHelper;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Nyx's biome selection system, which replaces the old GenLayers-based system.
 */
public class NyxBiomeProvider {
	private static final NyxBiomeProvider obj = new NyxBiomeProvider();
	public static NyxBiomeProvider instance() {
		return obj;
	}

	Map<Long, IaSFuture<BiomeGenBase[]>> pregenerated;
	private NyxBiomeProvider() {
		pregenerated = new TreeMap<Long, IaSFuture<BiomeGenBase[]>>(); 
	}
	public static BiomeGenBase getBiomeAt(BiomeGenBase[] biomes, int x, int z) {
		return biomes[(int)(x&15) | (int)((z&15)<<4)];
	}
	protected static long key(final int xchunk, final int zchunk) {
		return Integer.toUnsignedLong(xchunk) | (Integer.toUnsignedLong(zchunk) << 32);
	}

	public NyxBiomeProvider clean() {
		synchronized(pregenerated) {
			pregenerated.clear();
			return this;
		}
	}
	public NyxBiomeProvider clean(int xchunk, int zchunk) {
		synchronized(pregenerated) {
			pregenerated.remove(key(xchunk, zchunk));
			return this;
		}
	}
	public BiomeGenBase getBiomeAt(long x, long z) {
		return getBiomeAt(getBiomeArray((int)(x >> 4), (int)(z >> 4)).get(), (int)x, (int)z);
	}
	public IaSFuture<BiomeGenBase[]> getBiomeArray(final int xchunk, final int zchunk) {
		final long key = key(xchunk, zchunk);
		synchronized(pregenerated) {
			IaSFuture<BiomeGenBase[]> biomeArray = pregenerated.get(key);
			if(biomeArray == null) {
				class DoGenBiome implements Callable<BiomeGenBase[]> {
					@Override
					public BiomeGenBase[] call() {
						final BiomeGenBase[]
							array = new BiomeGenBase[256];
						return generateArray(array, xchunk, zchunk);
					}
				};
				biomeArray = IaSExecutor.push(new DoGenBiome());
				pregenerated.put(key, biomeArray);
			}
			return biomeArray;
		}
	}

	protected BiomeGenBase[] generateArray(final BiomeGenBase[] biomeArray, long xchunk, long zchunk) {		
		for(int i = 0; i < 256; ++i)
			biomeArray[i] = generateBiomeAt((i&15)|(xchunk<<4), (i>>4)|(zchunk<<4));
		return biomeArray;
	}
	
	protected BiomeGenBase generateBiomeAt(long x, long z) {
		final long distance = IaSWorldHelper.distance2(x, z)/2;
		if(distance < 24)
			return NyxBiomes.nyxMesaForest;
		if(distance < 32)
			return NyxBiomes.nyxHills;
		final boolean lowdensity = (x&160) == (z&160);
		final BiomeGenBase forest = (distance > 1000) && (lowdensity)?NyxBiomes.nyxInfested:NyxBiomes.nyxHillForest;
		final int mask = lowdensity?97:96;
		return (x&mask) == (z&mask)?forest:NyxBiomes.nyxLowMountains;
	}
}
