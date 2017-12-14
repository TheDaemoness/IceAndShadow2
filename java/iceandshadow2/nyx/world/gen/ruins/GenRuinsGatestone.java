package iceandshadow2.nyx.world.gen.ruins;

import iceandshadow2.IaSFlags;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.util.gen.Sculptor;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.styx.Styx;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenRuinsGatestone extends GenRuins {

	/**
	 * Generates the basic structure of the building. May also even out terrain
	 * that the building is on.
	 */
	public void buildPass(World var1, Random var2, int x, int y, int z) {
		y = Math.min(y, 250);
		Sculptor.cylinder(var1, y, x, z, 3, 6, Blocks.air, 0);
		Sculptor.terrainFlatten(var1, x-2, y-1, z-2, x+2, 4, z+2);
		var1.setBlock(x, 1, z, Styx.escape);
		var1.setBlock(x, 2, z, Styx.reserved);
		Sculptor.cube(var1, x-1, y, z-1, x+1, y+5, z+1, Styx.reserved, 0);
		Sculptor.cube(var1, x-1, y-2, z-1, x+1, y-1, z+1, Blocks.bedrock, 0);
		var1.setBlock(x, y, z, NyxBlocks.gatestone, var2.nextInt(2), 2);
		var1.setBlock(x, y+3, z, Styx.gatestone, 0, 2);
		Sculptor.cube(var1, x-1, 0, z-1, x+1, 0, z+1, Blocks.bedrock, 0);
	}

	/**
	 * Determines whether or not ruins can be generated here. Does not do any
	 * building.
	 */
	public boolean canGenerateHere(World var1, Random var2, int x, int y, int z) {
		return true;
	}

	/**
	 * "Ruins" the basic structure and adds a few decorative and functional
	 * touches to the building, like ladders, doorways, and spawners.
	 */
	
	public void damagePass(World var1, Random var2, int x, int y, int z) {}

	public String getLowercaseName() {
		return "gatestone";
	}

	/**
	 * Adds primarily reward chests. Not all ruins will have rewards, but most
	 * will and a coder is free to have this return instantly.
	 */
	public void rewardPass(World var1, Random var2, int x, int y, int z) {}

}
