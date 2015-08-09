package iceandshadow2.nyx.world.gen.ruins;

import iceandshadow2.IaSFlags;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.util.gen.Sculptor;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenRuinsCentral extends GenRuins {

	@Override
	public boolean canGenerateHere(World var1, Random r, int x, int y, int z) {
		return true;
	}

	@Override
	public void buildPass(World w, Random r, int x, int y, int z) {
		y = 156;
		for(int xit = -6; xit <= 6; xit += 2) {
			for(int zit = -6; zit <= 6; zit += 2)
				y = Math.min(w.getPrecipitationHeight(x+xit, z+zit), y);
		}
		Sculptor.sphere(w, x, y, z, 16, NyxBlocks.permafrost, 0);
		Sculptor.cylinder(w, x, y, z, 16, 256-y, Blocks.air, 0);
		Sculptor.cylinder(w, x, y, z, 12, 1, NyxBlocks.brickPale, 0);
		Sculptor.cube(w, x-7, y+4, z-1, x+7, y+4, z+1, Blocks.obsidian, 0);
		Sculptor.cube(w, x-1, y+4, z-7, x+1, y+4, z+7, Blocks.obsidian, 0);
		Sculptor.cube(w, x-2, y+4, z-2, x+2, y+4, z+2, Blocks.obsidian, 0);
		Sculptor.cube(w, x-1, y+4, z-1, x+1, y+4, z+1, NyxBlocks.cryingObsidian, 1);
		Sculptor.walls(w, x-7, y, z-7, x+7, y+9, z+7, Blocks.obsidian, 0);
		Sculptor.walls(w, x-6, y+10, z-6, x+6, y+10, z+6, Blocks.obsidian, 0);
		Sculptor.cube(w, x-7, y+5, z, x+7, y+6, z, Blocks.air, 0);
		Sculptor.cube(w, x, y+5, z-7, x, y+6, z+7, Blocks.air, 0);
		for(int i = 0; i < 3; ++i) {
			final int maxradi = 2+(i==0?1:0);
			Sculptor.blast(w, 
					x-6+r.nextInt(3), 
					y+5+r.nextInt(4), 
					z-7+i*5+r.nextInt(3), 2+r.nextInt(maxradi));
			Sculptor.blast(w, 
					x+8-r.nextInt(3), 
					y+5+r.nextInt(4), 
					z-7+i*5+r.nextInt(3), 2+r.nextInt(maxradi));
			Sculptor.blast(w,
					x-7+i*5+r.nextInt(3),
					y+5+r.nextInt(4),
					z-6+r.nextInt(3), 2+r.nextInt(maxradi));
			Sculptor.blast(w, 
					x-7+i*5+r.nextInt(3), 
					y+5+r.nextInt(4), 
					z+8-r.nextInt(3), 2+r.nextInt(maxradi));
		}
		for(int xit=-4; xit<=4; xit += 8) {
			for(int zit=-4; zit<=4; zit += 8)
				Sculptor.cube(w, x+xit, y, z+zit, x+xit, y+4, z+zit, Blocks.obsidian, 0);
		}
	}

	@Override
	public void damagePass(World w, Random r, int x, int y, int z) {
	}


	@Override
	public void rewardPass(World w, Random r, int x, int y, int z) {
		y = w.getPrecipitationHeight(x, z);
	}
	
	@Override
	public String getLowercaseName() {
		return "central-ruins";
	}

}
