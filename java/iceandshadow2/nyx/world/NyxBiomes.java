package iceandshadow2.nyx.world;

import iceandshadow2.nyx.world.biome.NyxBiome;
import net.minecraft.world.biome.BiomeGenBase;

public class NyxBiomes {

	public static BiomeGenBase 
		nyxLowMountains = new NyxBiome(255, false, 1.0F, 2.5F, false),
		nyxLowHills = new NyxBiome(254, false, 1.5F, 0.1F, false),
		nyxHighHills = new NyxBiome(253, false, 2.3F, 1.7F, false),
		nyxHighMountains = new NyxBiome(252, false, 0.5F, 4.0F, false),
		nyxLowForest = new NyxBiome(251, false, 0.8F, 1.8F, false),
		nyxHighForest = new NyxBiome(250, false, 1.8F, 2.4F, false);
	
	public static BiomeGenBase 
		nyxRugged = new NyxBiome(249, false, 2.2F, 1.8F, true),
		nyxInfested = new NyxBiome(248, false, 1.8F, 2.2F, true);
	
}
