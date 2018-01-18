package iceandshadow2.nyx.world;

import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import iceandshadow2.ias.util.BlockPos2;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Nyx's biome selection system, which replaces the old GenLayers-based system.
 */
public class NyxBiomeProvider {
	private static final NyxBiomeProvider obj = new NyxBiomeProvider();
	public static NyxBiomeProvider instance() {
		return obj;
	}
	
	ConcurrentMap<Long, BiomeGenBase[]> pregenerated;
	private NyxBiomeProvider() {
		pregenerated = new ConcurrentSkipListMap<Long, BiomeGenBase[]>(); 
	}
	
	public NyxBiomeProvider clean() {
		pregenerated.clear();
		return this;
	}
	public NyxBiomeProvider clean(int xchunk, int zchunk) {
		pregenerated.remove((long)xchunk | (zchunk << 32));
		return this;
	}
	public BiomeGenBase getBiomeAt(long x, long z) {
		return getBiomeAt(getBiomeArray(null, (int)(x >> 4), (int)(z >> 4)), x, z);
	}
	public BiomeGenBase getBiomeAt(BiomeGenBase[] biomes, long x, long z) {
		return biomes[(int)(x&15) | (int)((z&15)<<4)];
	}
	public BiomeGenBase[] getBiomeArray(BiomeGenBase[] prealloc, int xchunk, int zchunk) {
		final long key = (long)xchunk | (zchunk << 32);
		BiomeGenBase[] biomeArray = pregenerated.get(key);
		if(biomeArray == null) {
			if(prealloc == null || prealloc.length < 256)
				prealloc = new BiomeGenBase[256];
			biomeArray = generateArray(prealloc, key, xchunk, zchunk);
		}
		return biomeArray;
	}
	
	/**
	 * Actually generate the biome array.
	 * This is a very heavy operation.
	 */
	protected BiomeGenBase[] generateArray(BiomeGenBase[] biomeArray, long key, int xchunk, int zchunk) {
		//PLACEHOLDER.
		for(int xi = 0; xi < 16; ++xi)
			for(int zi = 0; zi < 16; ++zi) {
				biomeArray[(xi|(zi<<4))] = ((xchunk&6) == (zchunk&6))?NyxBiomes.nyxHillForest:NyxBiomes.nyxLowMountains;
			}
		pregenerated.put(key, biomeArray);
		return biomeArray;
	}
}
