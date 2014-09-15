package iceandshadow2.nyx.world.biome;

import iceandshadow2.nyx.world.gen.NyxGenThornyVines;

import java.util.Random;

import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class NyxBiomeMountains extends NyxBiome {

	public NyxBiomeMountains(int par1, boolean register, float heightRoot,
			float heightVari, boolean isRare) {
		super(par1, register, heightRoot, heightVari, isRare);
	}
	
	public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
		super.decorate(par1World, par2Random, par3, par4);
		
		int x = par3 + par2Random.nextInt(16) + 8;
		int z = par4 + par2Random.nextInt(16) + 8;
		
		/*
		if (par2Random.nextInt(16) == 0) {
			WorldGenerator aden = new WorldGenNyxNecromancerDen();
			aden.generate(par1World, par2Random, x, 66+par2Random.nextInt(18), z);
		}*/
        NyxGenThornyVines nyxvines = new NyxGenThornyVines();
        for (int var6 = 0; var6 < 25; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(16) + 8;
            byte var8 = 64;
            int var9 = par4 + par2Random.nextInt(16) + 8;
            nyxvines.generate(par1World, par2Random, var7, var8, var9);
    		par1World.updateLightByType(EnumSkyBlock.Block, var7, var8, var9);
        }
    }
}
