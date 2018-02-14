package iceandshadow2.ias.util.gen;

import iceandshadow2.boilerplate.IteratorConcat;
import iceandshadow2.ias.util.BlockPos2;
import iceandshadow2.ias.util.BlockPos3;
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
				for (int zit = less; zit <= more; ++zit)
					if (Math.sqrt(xit * xit + yit * yit + zit * zit) < Math.sqrt(world.rand.nextDouble()) * radius) {
						world.getBlock(x + xit, y + yit, z + zit).onBlockExploded(world, x + xit, y + yit, z + zit,
								null);
					}
			}
		}
	}

	/**
	 * Create the vertical corners of a cube centered at x, y, and z (no
	 * corners).
	 *
	 * @param world
	 * @param xSub
	 * @param y
	 * @param zSub
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
		final IteratorConcatBlock bic = new IteratorConcatBlock();
		bic.add(new SupplierBlockPosCuboid(xLow, yLow, zLow, xLow, yHigh, zLow));
		bic.add(new SupplierBlockPosCuboid(xLow, yLow, zHigh, xLow, yHigh, zHigh));
		bic.add(new SupplierBlockPosCuboid(xHigh, yLow, zLow, xHigh, yHigh, zLow));
		bic.add(new SupplierBlockPosCuboid(xHigh, yLow, zHigh, xHigh, yHigh, zHigh));
		for(BlockPos3 bp : bic) {
			bp.block(world, bl, meta);
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
		final ISupplierBlock bli = new SupplierBlockPosCuboid(xLow, yLow, zLow, xHigh, yHigh, zHigh);
		for(BlockPos3 bp3 : bli) {
			bp3.block(world, bl, meta);
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
	public static void cylinder(World world, int x, int y, int z, double radius, int height, Block bl, int meta) {
		final int intradius = (int)Math.ceil(radius);
		final ISupplierBlock bli = new SupplierBlockPosCuboid(
				-intradius, 0, -intradius,
				intradius, height, intradius);
		bli.addFilter(new BlockFilterRadius<BlockPos2>(new BlockPos2(x, z), radius));
		bli.offsetCenter(x, y, z);
		for(BlockPos3 bp3 : bli) {
			bp3.block(world, bl, meta);
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
	public static void dome(World world, int x, int y, int z, double radius, Block bl, int meta) {
		final int intradius = (int)Math.ceil(radius);
		final ISupplierBlock bli = new SupplierBlockPosCuboid(
				-intradius, 0, -intradius,
				intradius, intradius, intradius);
		bli.addFilter(new BlockFilterRadius<BlockPos3>(new BlockPos3(x, y, z), radius));
		bli.offsetCenter(x, y, z);
		for(BlockPos3 bp3 : bli) {
			bp3.block(world, bl, meta);
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
		final int intradius = (int)Math.ceil(radius);
		final ISupplierBlock bli = new SupplierBlockPosCuboid(
				-intradius, -intradius, -intradius,
				intradius, intradius, intradius);
		bli.addFilter(new BlockFilterRadius<BlockPos3>(new BlockPos3(x, y, z), radius));
		bli.offsetCenter(x, y, z);
		for(BlockPos3 bp3 : bli) {
			bp3.block(world, bl, meta);
		}
	}

	/**
	 * Extrudes the terrain below a certain region.
	 *
	 * @param world
	 * @param xSub
	 * @param y
	 * @param zSub
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
				for (int yit = y - yDown; yit <= y; ++yit)
					if (setblock) {
						world.setBlock(xit, yit, zit, fillah);
					} else if (world.getBlock(xit, yit, zit) == fillah) {
						setblock = true;
					}
			}
		}
	}

	/**
	 * Create the walls of a cube centered at x, y, and z (no corners).
	 *
	 * @param world
	 * @param xSub
	 * @param y
	 * @param zSub
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
		final IteratorConcatBlock bic = new IteratorConcatBlock();
		bic.add(new SupplierBlockPosCuboid(
				xLow+1, yLow, zLow, 
				xHigh-1, yHigh, zLow));
		bic.add(new SupplierBlockPosCuboid(
				xLow+1, yLow, zHigh, 
				xHigh-1, yHigh, zHigh));
		bic.add(new SupplierBlockPosCuboid(
				xLow, yLow, zLow+1, 
				xLow, yHigh, zHigh-1));
		bic.add(new SupplierBlockPosCuboid(
				xHigh, yLow, zLow+1, 
				xHigh, yHigh, zHigh-1));
		for(BlockPos3 bp : bic) {
			bp.block(world, bl, meta);
		}
	}
}
