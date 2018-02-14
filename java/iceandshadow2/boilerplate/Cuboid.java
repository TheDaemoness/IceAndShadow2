package iceandshadow2.boilerplate;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * IaS2's homebrew axis-aligned cuboid.
 */
public class Cuboid {
	public final Vec3 mid, halfwidth;
	
	public Cuboid() {
		this(1, 1, 1);
	}
	public Cuboid(double x, double y, double z) {
		mid = Vec3.createVectorHelper(x/2, y/2, z/2);
		halfwidth = mid;
	}
	public Cuboid(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
		mid = Vec3.createVectorHelper(
			(xMin+xMax)/2, 
			(yMin+yMax)/2, 
			(zMin+zMax)/2);
		halfwidth = Vec3.createVectorHelper(
			Math.abs(xMax-xMin)/2, 
			Math.abs(yMax-yMin)/2, 
			Math.abs(zMax-zMin)/2);
	}
	public Cuboid(Block bl) {
		this(
			bl.getBlockBoundsMinX(),
			bl.getBlockBoundsMinY(),
			bl.getBlockBoundsMinZ(),
			bl.getBlockBoundsMaxX(),
			bl.getBlockBoundsMaxY(),
			bl.getBlockBoundsMaxZ()
		);
	}
	public Cuboid(AxisAlignedBB bb) {
		this(
			bb.minX,
			bb.minY,
			bb.minZ,
			bb.maxX,
			bb.maxY,
			bb.maxZ
		);
	}
	
	public double xMin() {return mid.xCoord-halfwidth.xCoord;}
	public double yMin() {return mid.yCoord-halfwidth.yCoord;}
	public double zMin() {return mid.zCoord-halfwidth.zCoord;}
	public double xMax() {return mid.xCoord+halfwidth.xCoord;}
	public double yMax() {return mid.yCoord+halfwidth.yCoord;}
	public double zMax() {return mid.zCoord+halfwidth.zCoord;}
	
	public Vec3 rand(Random r) {
		return Vec3.createVectorHelper(
				mid.xCoord+halfwidth.xCoord*(-1+2*r.nextDouble()),
				mid.yCoord+halfwidth.yCoord*(-1+2*r.nextDouble()),
				mid.zCoord+halfwidth.zCoord*(-1+2*r.nextDouble()));
	}
	public AxisAlignedBB aabb(int x, int y, int z) {
		return aabb().offset(x, y, z);
	}
	public AxisAlignedBB aabb() {
		return AxisAlignedBB.getBoundingBox(xMin(), yMin(), zMin(), xMax(), yMax(), zMax());
	}
}
