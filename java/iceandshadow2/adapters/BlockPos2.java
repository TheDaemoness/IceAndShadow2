package iceandshadow2.adapters;

import iceandshadow2.boilerplate.IntPair;
import iceandshadow2.ias.util.IaSWorldHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class BlockPos2 extends IntPair {
	protected byte xSub, zSub;

	public BlockPos2(BlockPos2 other) {
		super(other.xValue, other.zValue);
		xSub = other.xSub;
		zSub = other.zSub;
	}

	public BlockPos2(Chunk ck /* Not unfortunate */) {
		super(ck.xPosition, ck.zPosition);
		xSub = zSub = 0;
	}

	public BlockPos2(Entity ent) {
		this((long) Math.floor(ent.posX), (long) Math.floor(ent.posZ));
	}

	public BlockPos2(long x, long z) {
		super((int) (x >> 4), (int) (z >> 4));
		xSub = (byte) (x & 15);
		zSub = (byte) (z & 15);
	}

	public Block block(World w, int y) {
		return chunk(w).getBlock(xSub, y, zSub);
	}

	public Block block(World w, int y, Block newBlock) {
		return block(w, y, newBlock, 0);
	}

	public Block block(World w, int y, Block newBlock, int newMeta) {
		if (y < 0 || y > 255)
			return null;
		final Chunk ck = chunk(w);
		final Block oldBlock = ck.getBlock(xSub, y, zSub);
		final boolean update = ck.func_150807_a(xSub, y, zSub, newBlock, newMeta);
		ck.worldObj.markAndNotifyBlock(xSub, y, zSub, ck, oldBlock, newBlock, 2 + (update ? 1 : 0));
		return oldBlock;
	}

	public Chunk chunk(World w) {
		return IaSWorldHelper.loadChunk(w, (long) xValue << 4, (long) zValue << 4);
	}

	public double distEuclid(BlockPos2 b) {
		return Math.sqrt(distEuclid2(b));
	}

	public double distEuclid2(BlockPos2 b) {
		final long dx = ((long) b.xValue << 4) - ((long) xValue << 4) + b.xSub - xSub;
		final long dz = ((long) b.zValue << 4) - ((long) zValue << 4) + b.zSub - zSub;
		return dx * dx + dz * dz;
	}

	public double distManhattan(BlockPos2 b) {
		final long dx = ((long) b.xValue << 4) - ((long) xValue << 4) + b.xSub - xSub;
		final long dz = ((long) b.zValue << 4) - ((long) zValue << 4) + b.zSub - zSub;
		return Math.abs(dx) + Math.abs(dz);
	}

	public boolean equals(BlockPos2 b) {
		return b.xSub == xSub && b.zSub == zSub && b.xValue == xValue && b.zValue == zValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BlockPos2)
			return equals((BlockPos2) obj);
		return super.equals(obj);
	}

	public void plus(BlockPos2 b) {
		xSub += b.xSub;
		zSub += b.zSub;
		xValue += b.xValue + (xSub >> 4);
		zValue += b.zValue + (zSub >> 4);
		xSub &= 15;
		zSub &= 15;
	}

	/**
	 * Converts the sub-chunk x/z values to an index value used by IaS's worldgen
	 * system for column data in chunks (like biome values).
	 */
	public int toChunkColumnIndex() {
		return xSub + (zSub << 4);
	}

	/**
	 * As toChunkColumnIndex(), but returns -1 if the chunk coordinates do not equal
	 * the values passed.
	 */
	public int toChunkColumnIndex(int xchunk, int zchunk) {
		if (xchunk != xValue || zchunk != zValue)
			return -1;
		return toChunkColumnIndex();
	}

	public int variant(World w, int y) {
		return w.getChunkFromChunkCoords(xValue, zValue).getBlockMetadata(xSub, y, zSub);
	}

	@Override
	public int x() {
		return (xValue << 4) + xSub;
	}

	@Override
	public int x(int newX) {
		final int temp = x();
		xValue = newX >> 4;
		xSub = (byte) (newX & 15);
		return temp;
	}

	@Override
	public int z() {
		return (zValue << 4) + zSub;
	}

	@Override
	public int z(int newZ) {
		final int temp = z();
		zValue = newZ >> 4;
		zSub = (byte) (newZ & 15);
		return temp;
	}
}
