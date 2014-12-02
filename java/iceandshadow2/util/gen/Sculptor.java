package iceandshadow2.util.gen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class Sculptor {
	/**
	 * Create a cube-shaped space centered at (x,y,z).
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param xdelta The number of blocks to remove before or after the specified x coordinate.
	 * @param ydelta The number of blocks to remove before or after the specified y coordinate.
	 * @param zdeltaThe number of blocks to remove before or after the specified z coordinate.
	 */
	public static void cube(World world, int x, int y, int z, int xdelta, int ydelta, int zdelta, Block bl, int meta) {
		for(int yit = -ydelta; yit <= ydelta; ++yit) {
			for(int xit = -xdelta; xit <= xdelta; ++xit) {
				for(int zit = -zdelta; zit <= -zdelta; ++zit)
					world.setBlock(x+xit, y+yit, z+zit, bl, meta, 0x2);
			}
		}
	}
	/**
	 * Create a dome-shaped space with the center of the flat base at (x,y,z).
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 */
	public static void dome(World world, int x, int y, int z, int radius, Block bl, int meta) {
		for(int yit = 0; yit <= radius; ++yit) {
			for(int xit = -radius; xit <= radius; ++xit) {
				for(int zit = -radius; zit <= radius; ++zit) {
					if(Math.sqrt((double)(xit*xit + yit*yit + zit*zit)) < radius)
						world.setBlock(x+xit, y+yit, z+zit, bl, meta, 0x2);
				}
			}
		}
	}
	
	/**
	 * Create a rugged empty space centered at (x,y,z).
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 */
	public static void blast(World world, int x, int y, int z, double radius) {
		int less = -(int)Math.floor(-radius);
		int more = (int)Math.ceil(radius);
		for(int yit = less; yit <= more; ++yit) {
			for(int xit = less; xit <= more; ++xit) {
				for(int zit = less; zit <= more; ++zit) {
					if(Math.sqrt((double)(xit*xit + yit*yit + zit*zit)) < Math.sqrt(world.rand.nextDouble())*radius)
						world.setBlockToAir(x+xit, y+yit, z+zit);
				}
			}
		}
	}
	
	/**
	 * Create a sphere-shaped space centered at (x,y,z).
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 */
	public static void sphere(World world, int x, int y, int z, int radius, Block bl, int meta) {
		for(int yit = -radius; yit <= radius; ++yit) {
			for(int xit = -radius; xit <= radius; ++xit) {
				for(int zit = -radius; zit <= radius; ++zit) {
					if(Math.sqrt((double)(xit*xit + yit*yit + zit*zit)) < radius)
						world.setBlock(x+xit, y+yit, z+zit, bl, meta, 0x2);
				}
			}
		}
	}
	
	/**
	 * Create a cylinder-shaped space starting from (x,y,z) and rising to (x,y+height,z).
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 * @param height
	 */
	public static void cylinder(World world, int x, int y, int z, int radius, int height, Block bl, int meta) {
		for(int yit = 0; yit <= height; ++yit) {
			for(int xit = -radius; xit <= radius; ++xit) {
				for(int zit = -radius; zit <= radius; ++zit) {
					if(Math.sqrt((double)(xit*xit + zit*zit)) < radius)
						world.setBlock(x+xit, y+yit, z+zit, bl, meta, 0x2);
				}
			}
		}
	}
	
	/**
	 * Create the walls of a cube centered at x, y, and z.
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param xdelta The number of blocks to remove before or after the specified x coordinate.
	 * @param ydelta The number of blocks to remove before or after the specified y coordinate.
	 * @param zdeltaThe number of blocks to remove before or after the specified z coordinate.
	 */
	public static void walls(World world, int x, int y, int z, int xdelta, int ydelta, int zdelta, Block bl, int meta) {
		for(int yit = -ydelta; yit <= ydelta; ++yit) {
			for(int xit = -xdelta; xit < xdelta; ++xit) {
				world.setBlock(x+xit, y+yit, z-zdelta, bl, meta, 0x2);
				world.setBlock(x-xit, y+yit, z+zdelta, bl, meta, 0x2);
			}
			for(int zit = -zdelta; zit < -zdelta; ++zit) {
				world.setBlock(x-xdelta, y+yit, z+zit, bl, meta, 0x2);
				world.setBlock(x+xdelta, y+yit, z-zit, bl, meta, 0x2);
				
			}
		}
	}
}
