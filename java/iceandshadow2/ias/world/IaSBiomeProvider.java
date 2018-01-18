package iceandshadow2.ias.world;

public interface IaSBiomeProvider {
	public IaSBiomeGen[] getBiomesForChunk(IaSBiomeGen[] array, int xchunk, int zchunk);
}
