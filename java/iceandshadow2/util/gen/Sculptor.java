package iceandshadow2.util.gen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class Sculptor {
	/**
	 * Create a rugged empty space centered at (x,y,z).
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 */
	public static void blast(World world, int x, int y, int z, double radius) {
		final int less = (int) Math.floor(-radius);
		final int more = (int) Math.ceil(radius);
		for (int yit = less; yit <= more; ++yit) {
			for (int xit = less; xit <= more; ++xit) {
				for (int zit = less; zit <= more; ++zit) {
					if (Math.sqrt(xit * xit + yit * yit + zit * zit) < Math.sqrt(world.rand.nextDouble()) * radius)
						world.getBlock(x + xit, y + yit, z + zit).onBlockExploded(world, x + xit, y + yit, z + zit, null);
				}
			}
		}
	}

	/**
	 * Create the vertical corners of a cube centered at x, y, and z (no
	 * corners).
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param xdelta
	 *            The number of blocks to remove before or after the specified x
	 *            coordinate.
	 * @param ydelta
	 *            The number of blocks to remove before or after the specified y
	 *            coordinate.
	 * @param zdeltaThe
	 *            number of blocks to remove before or after the specified z
	 *            coordinate.
	 */
	public static void corners(World world, int xLow, int yLow, int zLow, int xHigh, int yHigh, int zHigh, Block bl,
			int meta) {
		for (int yit = yLow; yit <= yHigh; ++yit) {
			world.setBlock(xLow, yit, zLow, bl, meta, 0x2);
			world.setBlock(xLow, yit, zHigh, bl, meta, 0x2);
			world.setBlock(xHigh, yit, zLow, bl, meta, 0x2);
			world.setBlock(xHigh, yit, zHigh, bl, meta, 0x2);
		}
	}

	/**
	 * Creates a cube-shaped space.
	 *
	 * @param world
	 * @param xLow
	 * @param yLow
	 * @param zLow
	 * @param xHigh
	 * @param yHigh
	 * @param zHigh
	 * @param bl
	 * @param meta
	 */
	public static void cube(World world, int xLow, int yLow, int zLow, int xHigh, int yHigh, int zHigh, Block bl,
			int meta) {
		for (int yit = yLow; yit <= yHigh; ++yit) {
			for (int xit = xLow; xit <= xHigh; ++xit) {
				for (int zit = zLow; zit <= zHigh; ++zit)
					world.setBlock(xit, yit, zit, bl, meta, 0x2);
			}
		}
	}

	/**
	 * Create a cylinder-shaped space starting from (x,y,z) and rising to
	 * (x,y+height,z).
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 * @param height
	 */
	public static void cylinder(World world, int x, int y, int z, int radius, int height, Block bl, int meta) {
		for (int yit = 0; yit <= height; ++yit) {
			for (int xit = -radius; xit <= radius; ++xit) {
				for (int zit = -radius; zit <= radius; ++zit) {
					if (Math.sqrt(xit * xit + zit * zit) < radius)
						world.setBlock(x + xit, y + yit, z + zit, bl, meta, 0x2);
				}
			}
		}
	}

	/**
	 * Create a dome-shaped space with the center of the flat base at (x,y,z).
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 */
	public static void dome(World world, int x, int y, int z, int radius, Block bl, int meta) {
		for (int yit = 0; yit <= radius; ++yit) {
			for (int xit = -radius; xit <= radius; ++xit) {
				for (int zit = -radius; zit <= radius; ++zit) {
					if (Math.sqrt(xit * xit + yit * yit + zit * zit) < radius)
						world.setBlock(x + xit, y + yit, z + zit, bl, meta, 0x2);
				}
			}
		}
	}

	/**
	 * Create a sphere-shaped space centered at (x,y,z).
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 */
	public static void sphere(World world, int x, int y, int z, int radius, Block bl, int meta) {
		for (int yit = -radius; yit <= radius; ++yit) {
			for (int xit = -radius; xit <= radius; ++xit) {
				for (int zit = -radius; zit <= radius; ++zit) {
					if (Math.sqrt(xit * xit + yit * yit + zit * zit) < radius)
						world.setBlock(x + xit, y + yit, z + zit, bl, meta, 0x2);
				}
			}
		}
	}

	/**
	 * Extrudes the terrain below a certain region.
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param xdelta
	 *            The number of blocks to extrude before or after the specified
	 *            x coordinate.
	 * @param ydown
	 *            The number of blocks to fill upward from.
	 * @param zdelta
	 *            The number of blocks to extrude before or after the specified
	 *            z coordinate.
	 */
	public static void terrainFlatten(World world, int xLow, int y, int zLow, int xHigh, int yDown, int zHigh) {
		for (int xit = xLow; xit <= xHigh; ++xit) {
			for (int zit = zLow; zit <= zHigh; ++zit) {
				boolean setblock = false;
				final Block fillah = world.getBiomeGenForCoords(xit, zit).fillerBlock;
				for (int yit = y - yDown; yit <= y; ++yit) {
					if (setblock)
						world.setBlock(xit, yit, zit, fillah);
					else if (world.getBlock(xit, yit, zit) == fillah)
						setblock = true;
				}
			}
		}
	}

	/**
	 * Create the walls of a cube centered at x, y, and z (no corners).
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param xdelta
	 *            The number of blocks to remove before or after the specified x
	 *            coordinate.
	 * @param ydelta
	 *            The number of blocks to remove before or after the specified y
	 *            coordinate.
	 * @param zdeltaThe
	 *            number of blocks to remove before or after the specified z
	 *            coordinate.
	 */
	public static void walls(World world, int xLow, int yLow, int zLow, int xHigh, int yHigh, int zHigh, Block bl,
			int meta) {
		for (int yit = yLow; yit <= yHigh; ++yit) {
			for (int xit = xLow + 1; xit < xHigh; ++xit) {
				world.setBlock(xit, yit, zLow, bl, meta, 0x2);
				world.setBlock(xit, yit, zHigh, bl, meta, 0x2);
			}
			for (int zit = zLow + 1; zit < zHigh; ++zit) {
				world.setBlock(xLow, yit, zit, bl, meta, 0x2);
				world.setBlock(xHigh, yit, zit, bl, meta, 0x2);
			}
		}
	}
}
