package iceandshadow2.boilerplate;

import java.util.Random;

import net.minecraft.util.Vec3;

//Vec3 dependency is temporary until IaS2 gets a non-MC 3+ dimension vector class.

/**
 * IaS2's homebrew axis-aligned cuboid.
 */
public class Cuboid {
	public final Vec3 mid, halfwidth;

	public Cuboid() {
		this(1, 1, 1);
	}

	public Cuboid(double x, double y, double z) {
		mid = Vec3.createVectorHelper(x / 2, y / 2, z / 2);
		halfwidth = mid;
	}

	public Cuboid(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
		mid = Vec3.createVectorHelper((xMin + xMax) / 2, (yMin + yMax) / 2, (zMin + zMax) / 2);
		halfwidth = Vec3.createVectorHelper(Math.abs(xMax - xMin) / 2, Math.abs(yMax - yMin) / 2,
				Math.abs(zMax - zMin) / 2);
	}

	public Vec3 rand(Random r) {
		return Vec3.createVectorHelper(mid.xCoord + halfwidth.xCoord * (-1 + 2 * r.nextDouble()),
				mid.yCoord + halfwidth.yCoord * (-1 + 2 * r.nextDouble()),
				mid.zCoord + halfwidth.zCoord * (-1 + 2 * r.nextDouble()));
	}

	public double xMax() {
		return mid.xCoord + halfwidth.xCoord;
	}

	public double xMin() {
		return mid.xCoord - halfwidth.xCoord;
	}

	public double yMax() {
		return mid.yCoord + halfwidth.yCoord;
	}

	public double yMin() {
		return mid.yCoord - halfwidth.yCoord;
	}

	public double zMax() {
		return mid.zCoord + halfwidth.zCoord;
	}

	public double zMin() {
		return mid.zCoord - halfwidth.zCoord;
	}
}
