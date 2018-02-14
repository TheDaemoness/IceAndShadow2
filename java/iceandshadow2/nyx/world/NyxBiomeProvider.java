package iceandshadow2.nyx.world;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import iceandshadow2.boilerplate.ChunkRandom;
import iceandshadow2.ias.util.IaSWorldHelper;
import iceandshadow2.nyx.world.biome.NyxBiome;
import net.minecraft.world.biome.BiomeGenBase;

class BiomePoint {
	public NyxBiome biome;
	public long x;
	public long z;
};

/**
 * Nyx's biome selection system.
 */
public class NyxBiomeProvider {
	public static final int SCALE=96;
	private static final Map<Long, BiomePoint> points = new TreeMap<Long, BiomePoint>();

	public static void clean() {
		synchronized(points) {
			points.clear();
		}
	}

	/**
	 * Precondition: array.length = 256.
	 */
	public static void fillArray(long seed, BiomeGenBase[] biomeArray, int xchunk, int zchunk) {
		final BiomePoint[] points = new BiomePoint[16];
		final long
			x = ((long)xchunk<<4),
			z = ((long)zchunk<<4);
		final int
			xBoxBase = (int)((x+SCALE/2)/SCALE),
			zBoxBase = (int)((z+SCALE/2)/SCALE);
		for(int i = 0; i < 16; ++i) {
			points[i] = getBiomePoint(seed, xBoxBase-1+(i&3), zBoxBase-1+(i>>2));
		}
		for(int i = 0; i < 256; ++i) {
			final long
				xFull = x+(i&15),
				zFull = z+(i>>4);
			final int
				xBox = (int)((xFull+SCALE/2)/SCALE),
				zBox = (int)((zFull+SCALE/2)/SCALE);
			biomeArray[i] = getBiomeAtPoint(seed, xFull, zFull, points, xBox-xBoxBase, zBox-zBoxBase);
		}
	}

	protected static BiomeGenBase getBiomeAtPoint(long seed, long x, long z, BiomePoint[] points, int xDelta, int zDelta) {
		BiomePoint bp = points[(xDelta+1)+(zDelta+1)*4];
		long closestDistance = (long)(IaSWorldHelper.distance2(bp.x-x, bp.z-z)/bp.biome.getWeight());
		BiomeGenBase retval = bp.biome;
		final int[] indices = {0, 1, 2, 3, 5, 6, 7, 8}; //Skip i = 4.
		for(final int i : indices) {
			bp  = points[(xDelta+i%3)+(zDelta+i/3)*4];
			final long newDistance = (long)(IaSWorldHelper.distance2(bp.x-x, bp.z-z)/bp.biome.getWeight());
			if(newDistance < closestDistance) {
				closestDistance = newDistance;
				retval = bp.biome;
			}
		}
		return retval;
	}
	protected static BiomePoint getBiomePoint(long seed, int xBox, int zBox) {
		final long key = NyxBiomeManager.key(xBox, zBox);
		synchronized(points) {
			BiomePoint bp = points.get(key);
			if(bp == null) {
				final Random r = new ChunkRandom(seed, 127846, xBox, zBox);
				bp = new BiomePoint();
				if(xBox == 0 && zBox == 0) {
					bp.x = 0;
					bp.z = 0;
				} else {
					bp.x = xBox*SCALE-SCALE/2+r.nextInt(SCALE);
					bp.z = zBox*SCALE-SCALE/2+r.nextInt(SCALE);
				}
				bp.biome = getBiomeForBoxPoint(r, getNumForBox(seed, r, xBox, zBox), bp.x, bp.z);
				points.put(key, bp);
			}
			return bp;
		}
	}
	protected static int getNumForBox(long seed, Random r, int xBox, int zBox) {
		if(xBox == 0 && zBox == 0)
			return 2;
		if(Math.abs(xBox) <= 1 && Math.abs(xBox) <= 0)
			return r.nextBoolean()?1:0;
		final int
			aD = (int)((seed*1607)&6)>>1,
			bD = (int)((seed*1352)&6)>>1;
		final int
			a = xBox+zBox*2,
			b = zBox-xBox*2;
		final boolean
			am = (a+aD)%3==0,
			bm = (b+bD)%3==0;
		return (am&&bm?1:0);
	}
	protected static NyxBiome getBiomeForBoxPoint(Random r, int biomeNum, long x, long z) {
		if(biomeNum == 2)
			return NyxBiomes.nyxMesaForest;
		final int
			distance = (int)(IaSWorldHelper.distance2(x, z)/2),
			type = r.nextInt(3);
		if(biomeNum == 0) {
			final boolean
				altMountains = r.nextBoolean(),
				severeMountains = distance>2000&&r.nextBoolean();
			if(type==0 && altMountains)
				return severeMountains?NyxBiomes.nyxExousic:NyxBiomes.nyxMesaForest;
			return NyxBiomes.nyxMountains;
		}
		else if(type == 0)
			return NyxBiomes.nyxMesaForest;
		else if(type == 1)
			return distance>1000?NyxBiomes.nyxInfested:NyxBiomes.nyxMesaForest;
		return NyxBiomes.nyxHillForest;
	}
}
