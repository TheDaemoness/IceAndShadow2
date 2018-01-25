package iceandshadow2.nyx.world.biome;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.entities.mobs.EntityNyxSpider;
import iceandshadow2.nyx.world.gen.GenThornyVines;

import java.util.Random;

import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class NyxBiomeMountains extends NyxBiome {

	public NyxBiomeMountains(int par1, boolean register, float heightRoot, float heightVari, boolean isRare) {
		super(par1, register, heightRoot, heightVari, isRare);
		setBlocks(NyxBlocks.snow, NyxBlocks.stone);

		spawnableMonsterList.add(new SpawnListEntry(EntityNyxSpider.class, 10, 2, 3));
		
		setColor(96 << 16 | 96 << 8 | 96);
	}
	
	@Override
	public boolean deepSurfaceLayer() {
		return true;
	}

	@Override
	protected void genFoliage(World par1World, Random par2Random, int xchunk, int zchunk) {
		/*
		 * if (par2Random.nextInt(16) == 0) { WorldGenerator aden = new
		 * WorldGenNyxNecromancerDen(); aden.generate(par1World, par2Random, x,
		 * 66+par2Random.nextInt(18), z); }
		 */
		final GenThornyVines nyxvines = new GenThornyVines();
		for (int var6 = 0; var6 < 25; ++var6) {
			final int var7 = xchunk + par2Random.nextInt(16) + 8;
			final byte var8 = 64;
			final int var9 = zchunk + par2Random.nextInt(16) + 8;
			nyxvines.generate(par1World, par2Random, var7, var8, var9);
			par1World.updateLightByType(EnumSkyBlock.Block, var7, var8, var9);
		}
	}
}
