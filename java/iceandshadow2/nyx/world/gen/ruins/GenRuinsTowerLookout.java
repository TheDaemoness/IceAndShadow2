package iceandshadow2.nyx.world.gen.ruins;

import iceandshadow2.IaSFlags;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.util.gen.Sculptor;

import java.util.Random;
import java.util.logging.Level;

import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenRuinsTowerLookout extends GenRuins {
	
	@Override
	public String getLowercaseName() {
		return "ruins-tower-lookout";
	}
	
	/**
	 * Determines whether or not ruins can be generated here. Does not do any building.
	 */
	public boolean canGenerateHere(World var1, Random var2, int x, int y, int z) {
		for(int xdelta = -36; xdelta <= 36; ++xdelta) {
			for(int zdelta = -36; zdelta <= 36; ++zdelta)
				for(int ydelta = -4; ydelta <= 8; ++ydelta) {
					if(var1.getBlock(x+xdelta, y+ydelta, z+zdelta) == Blocks.ladder)
						return false;
				}
		}
		return true;
	}
	
	/**
	 * Generates the basic structure of the building. May also even out terrain that the building is on.
	 */
	public void buildPass(World world, Random var2, int x, int y, int z) {
		Sculptor.terrainFlatten(world, x-3, y-1, z-3, x+3, 4, z+3);
		
		Sculptor.cube(world, x-2, y-2, z-2, x+2, y-2, z+2, world.getBiomeGenForCoords(x, z).fillerBlock, 0);
		Sculptor.cube(world, x-3, y-1, z-3, x+3, y-1, z+3, world.getBiomeGenForCoords(x, z).fillerBlock, 0);
		Sculptor.cube(world, x-2, y-1, z-2, x+2, y, z+2, NyxBlocks.frozenBrick, 0);
		Sculptor.cube(world, x-4, y, z-4, x+4, y+13, z+4, Blocks.air, 0);
		
		Sculptor.walls(world, x-2, y, z-2, x+2, y+10, z+2, NyxBlocks.frozenBrick, 0);
		Sculptor.walls(world, x-3, y+11, z-3, x+3, y+11, z+3, NyxBlocks.frozenBrick, 0);
		Sculptor.corners(world, x-2, y+10, z-2, x+2, y+10, z+2, NyxBlocks.frozenBrick, 0);
	}
	
	/**
	 * "Ruins" the basic structure and adds a few decorative and functional touches to the building, like ladders, doorways, and spawners.
	 */
	public void damagePass(World var1, Random var2, int x, int y, int z) {
		/*
		 * Direction (f) variable cheat sheet: 0: South (+z) 1: West (-x) 2:
		 * North (-z) 3: East (+x)
		 */
		
		// Destruction pass.
		for (int ydim = 4; ydim <= 12; ydim += 2) {
			int xpos = x-2+var2.nextInt(5);
			int zpos = z-2+var2.nextInt(5);
			if(var2.nextInt(3) != 0)
				Sculptor.blast(var1, xpos, y+ydim, zpos, 1.5+var2.nextDouble());
		}

		int f = var2.nextInt(4);
		for (int xdim = -2; xdim <= 2; ++xdim) {
			for (int zdim = -2; zdim <= 2; ++zdim) {

				// Check to make sure we're dealing with a wall.
				if (MathHelper.abs_int(xdim) == 2
						|| MathHelper.abs_int(zdim) == 2) {
					// Swiss cheese pass.
					for (int ydim = 1; ydim < 10; ++ydim) {
						if (!var1.isAirBlock(x + xdim, y + ydim, z + zdim) && var2.nextInt(5) == 0)
							var1.setBlock(x + xdim, y + ydim, z + zdim, Blocks.air);
					}
				}
				
				// LADDER!
				for (int ydim = 10; ydim > -1; --ydim) {
					if (f == 0) {
						if (Blocks.ladder.canBlockStay(var1, x, y + ydim, z - 1))
							var1.setBlock(x, y + ydim, z - 1, Blocks.ladder,
									0x3, 0x2);
					} else if (f == 1) {
						if (Blocks.ladder.canBlockStay(var1, x + 1, y + ydim, z))
							var1.setBlock(x + 1, y + ydim, z, Blocks.ladder,
									0x4, 0x2);
					} else if (f == 2) {
						if (Blocks.ladder.canBlockStay(var1, x, y + ydim, z + 1))
							var1.setBlock(x, y + ydim, z + 1, Blocks.ladder,
									0x2, 0x2);
					} else if (f == 3) {
						if (Blocks.ladder.canBlockStay(var1, x - 1, y + ydim, z))
							var1.setBlock(x - 1, y + ydim, z, Blocks.ladder,
									0x5, 0x2);
					}
				}

				// Add an entrance to the tower.
				for (int ydim = 0; ydim < 2; ++ydim) {
					if (f == 0)
						var1.setBlockToAir(x, y + ydim, z + 2);
					else if (f == 1)
						var1.setBlockToAir(x - 2, y + ydim, z);
					else if (f == 2)
						var1.setBlockToAir(x, y + ydim, z - 2);
					else
						var1.setBlockToAir(x + 2, y + ydim, z);
				}
			}
		}
	}
	
	/**
	 * Adds primarily reward chests. Not all ruins will have rewards, but most will and a coder is free to have this return instantly.
	 */
	public void rewardPass(World var1, Random var2, int x, int y, int z) {
		
	}


}
