package iceandshadow2.ias.world;

/**
 * Interface for classes that generate random points in chunks.
 */
public interface IChunkPointGen {
	public boolean[] generate(boolean[] prealloc, int xchunk, int zchunk);
}
