package iceandshadow2.ias.util;

import net.minecraft.entity.Entity;

public class IaSCoordsBlock {
	private final int chunkX, chunkZ;
	private final byte subX, subY, subZ;

	public IaSCoordsBlock(double x, double y, double z) {
		this((long) (x < 0 ? x - 1 : x), (int) y, (long) (z < 0 ? z - 1 : z));
	}

	public IaSCoordsBlock(Entity ent) {
		this(ent.posX, ent.posY, ent.posZ);
	}

	public IaSCoordsBlock(long x, int y, long z) {
		chunkX = (int) (x >> 4);
		chunkZ = (int) (z >> 4);
		subX = (byte) (x & 16);
		subY = (byte) (Math.min(y, 255));
		subZ = (byte) (z & 16);
	}

	public int getX() {
		return (int) getXL();
	}

	public long getXL() {
		return (chunkX << 4) + subX;
	}

	public byte getY() {
		return subY;
	}

	public int getZ() {
		return (int) getZL();
	}

	public long getZL() {
		return (chunkZ << 4) + subZ;
	}
}
