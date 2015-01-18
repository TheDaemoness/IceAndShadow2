package iceandshadow2.util.gen;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class SearchBlocks {
	
	public static BlockTest testAir = new BlockTestAir();
	public static BlockTest testTEs = new BlockTestTileEntities();
	public static BlockTest testUnbreakable = new BlockTestUnbreakable();
	
	public static boolean cube(World world, int xLow, int yLow, int zLow,
			int xHigh, int yHigh, int zHigh, BlockTest bl) {
		for (int yit = yLow; yit <= yHigh; ++yit) {
			for (int xit = xLow; xit <= xHigh; ++xit) {
				for (int zit = zLow; zit <= zHigh; ++zit) {
					if(bl.test(world, xit, yit, zit, world.getBlock(xit, yit, zit)))
						return true;
				}
			}
		}
		return false;
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
	public static void cylinder(World world, int x, int y, int z, int radius,
			int height, Block bl, int meta) {
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
	public static void dome(World world, int x, int y, int z, int radius,
			Block bl, int meta) {
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
	public static void sphere(World world, int x, int y, int z, int radius,
			Block bl, int meta) {
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
	public static void walls(World world, int xLow, int yLow, int zLow,
			int xHigh, int yHigh, int zHigh, Block bl, int meta) {
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
