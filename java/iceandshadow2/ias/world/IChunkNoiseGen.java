package iceandshadow2.ias.world;

public interface IChunkNoiseGen {
	float[] generate(float[] prealloc, int xchunk, int zchunk, int feather);
}
