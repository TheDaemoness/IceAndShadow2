package iceandshadow2.nyx.world.gen.ruins;

import iceandshadow2.ias.util.gen.Sculptor;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.styx.Styx;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GenRuinsInfestedPhylactery extends GenRuins {

	/**
	 * Generates the basic structure of the building. May also even out terrain
	 * that the building is on.
	 */
	@Override
	public void buildPass(World var1, Random var2, int x, int y, int z) {
		var1.setBlock(x, y-1, z, Blocks.obsidian);
		var1.setBlock(x, y, z, NyxBlocks.infestSpawner, 1, 2);
		
	}

	/**
	 * Determines whether or not ruins can be generated here. Does not do any
	 * building.
	 */
	@Override
	public boolean canGenerateHere(World var1, Random var2, int x, int y, int z) {
		return var2.nextInt(3) != 0;
	}

	/**
	 * "Ruins" the basic structure and adds a few decorative and functional
	 * touches to the building, like ladders, doorways, and spawners.
	 */

	@Override
	public void damagePass(World var1, Random var2, int x, int y, int z) {
	}

	@Override
	public String getLowercaseName() {
		return "infested-phylactery";
	}

	/**
	 * Adds primarily reward chests. Not all ruins will have rewards, but most
	 * will and a coder is free to have this return instantly.
	 */
	@Override
	public void rewardPass(World var1, Random var2, int x, int y, int z) {
	}

}
