package iceandshadow2.ias.world;

/**
 * Implementation of something resembling a 2D Worley noise generator, using something resembling Manhattan distance.
 * Used by IaS2 to determine biome placement.
 */
public class NoiseGenWorley implements IChunkNoiseGen {
	private BoxPointGen genPos, genNeg;
	
	public NoiseGenWorley(BoxPointGen pos, BoxPointGen neg) {
		genPos = pos;
		genNeg = neg;
	}

	@Override
	public float[] generate(float[] prealloc, int xchunk, int zchunk, int margin) {
		int width = margin + 16;
		if(prealloc.length < width*width)
			prealloc = new float[width*width];
		
		return prealloc;
	}

}
