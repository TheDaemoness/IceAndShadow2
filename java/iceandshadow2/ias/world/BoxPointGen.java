package iceandshadow2.ias.world;

import iceandshadow2.ias.util.BlockPos2;
import iceandshadow2.ias.util.ChunkRandom;
import net.minecraft.world.World;

/**
 * Generates a grid with each cell being filled by one randomly-positioned point.
 */
public class BoxPointGen implements IChunkPointGen {
	protected final int scale;
	protected final long seed;
	
	public BoxPointGen(World w, int scale) {
		if(scale < 1)
			throw new IllegalArgumentException("scale ("+scale+") must be >= 1");
		this.scale = scale;
		this.seed = w.getSeed();
	}
	/**
	 * Converts an x or z block coordinate to a box coordinate.
	 */
	public long getBoxCoordinate(long distance) {
		return Math.floorDiv((distance + scale/2), scale);
	}
	public long getBlockCoordinate(long distance) {
		return distance*scale - scale/2;
	}
	public BlockPos2 getPointForBox(long xBox /*MS NO SUE*/, long zBox, int margin) {
		if(margin*2 >= scale)
			throw new IllegalArgumentException("margin * 2 ("+(margin*2)+") must be < scale ("+scale+")");
		final long blockX = getBlockCoordinate(xBox),
				blockZ = getBlockCoordinate(zBox);
		final ChunkRandom r = new ChunkRandom(seed, 1924058234, (int)xBox, (int)zBox);
		final long x = blockX + margin + r.nextInt(scale-margin*2),
				z= blockZ + margin + r.nextInt(scale-margin*2);
		return new BlockPos2(x, z);
	}
	public BlockPos2 getPointForBox(long xBox, long zBox) {
		return getPointForBox(xBox, zBox, 0);
	}
	
	public boolean[] generate(boolean[] prealloc, int xchunk, int zchunk) {
		final long xBox = getBoxCoordinate((long)xchunk*16),
				zBox = getBoxCoordinate((long)zchunk*16);
		if(prealloc == null || prealloc.length < 256)
			prealloc = new boolean[256];
		for(int i = 0; i < 4; ++i) {
			final int index = getPointForBox(xBox+(i&1), zBox+(i>>1)).toChunkColumnIndex(xchunk, zchunk);
			if(index >= 0)
				prealloc[index] = true;
		}
		return prealloc;
	}
}
