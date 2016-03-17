package iceandshadow2.util;

import net.minecraft.entity.Entity;

public class IaSCoordsBlock {
	private final int chunkX, chunkZ;
	private final byte subX, subY, subZ;

	public IaSCoordsBlock(long x, int y, long z) {
		this.chunkX = (int)(x >> 4);
		this.chunkZ = (int)(z >> 4);
		this.subX = (byte)(x&16);
		this.subY = (byte)(Math.min(y, 255));
		this.subZ = (byte)(z&16);
	}

	public IaSCoordsBlock(double x, double y, double z) {
		this((long)(x<0?x-1:x),(int)y,(long)(z<0?z-1:z));
	}

	public IaSCoordsBlock(Entity ent) {
		this(ent.posX, ent.posY, ent.posZ);
	}

	public long getXL() {
		return (this.chunkX << 4) + this.subX;
	}
	public long getZL() {
		return (this.chunkZ << 4) + this.subZ;
	}
	public int getX() {
		return (int)getXL();
	}
	public int getZ() {
		return (int)getZL();
	}
	public byte getY() {
		return this.subY;
	}
}
