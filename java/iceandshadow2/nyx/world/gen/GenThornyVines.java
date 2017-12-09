package iceandshadow2.nyx.world.gen;

import iceandshadow2.nyx.NyxBlocks;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenThornyVines extends WorldGenerator {

	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
		final int l = par3;

		for (final int i1 = par5; par4 < 256; ++par4)
			if (par1World.isAirBlock(par3, par4, par5)) {
				for (int j1 = 2; j1 <= 5; ++j1)
					if (Blocks.vine.canPlaceBlockOnSide(par1World, par3, par4, par5, j1)) {
						par1World.setBlock(par3, par4, par5, NyxBlocks.thornyVines,
								1 << Direction.facingToDirection[Facing.oppositeSide[j1]], 2);
						break;
					}
			} else {
				par3 = l + par2Random.nextInt(4) - par2Random.nextInt(4);
				par5 = i1 + par2Random.nextInt(4) - par2Random.nextInt(4);
			}

		return true;
	}

}
