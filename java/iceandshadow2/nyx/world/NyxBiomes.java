package iceandshadow2.nyx.world;

import iceandshadow2.nyx.world.biome.NyxBiome;
import net.minecraft.world.biome.BiomeGenBase;

public class NyxBiomes {

	public static BiomeGenBase 
		nyxMountains = new NyxBiome(255, false, 0.0F, 2.0F, false),
		nyxHills = new NyxBiome(254, false, 0.0F, 2.0F, false),
		nyxMesas = new NyxBiome(253, false, 0.0F, 2.0F, false),
		nyxCliffs = new NyxBiome(252, false, 0.0F, 2.0F, false),
		nyxForest = new NyxBiome(251, false, 0.0F, 2.0F, false);
	
	public static BiomeGenBase 
		nyxRugged = new NyxBiome(250, false, 0.0F, 2.0F, true),
		nyxInfested = new NyxBiome(249, false, 0.0F, 2.0F, true);
	
}
