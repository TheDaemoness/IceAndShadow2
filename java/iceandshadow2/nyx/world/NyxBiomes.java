package iceandshadow2.nyx.world;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.biome.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class NyxBiomes {

	public static BiomeGenBase 
		nyxLowMountains = new NyxBiomeMountains(255, true, 1.0F, 2.5F, false).
			setBlocks(NyxBlocks.permafrost,NyxBlocks.permafrost).setBiomeName("NyxLowMountains"),
		nyxHighMountains = new NyxBiome(252, true, 0.5F, 4.0F, false).
			setBlocks(Blocks.snow_layer,NyxBlocks.permafrost).setBiomeName("NyxHighMountains"),
		
		nyxHills = new NyxBiome(254, true, 1.5F, 0.1F, false).setBiomeName("NyxHills"),
		nyxMesas = new NyxBiome(253, true, 2.3F, 1.5F, false).setBiomeName("NyxMesas"),
		nyxHillForest = new NyxBiomeForest(251, true, 0.8F, 1.8F, false).setBiomeName("NyxHillForest"),
		nyxMesaForest = new NyxBiomeFrozen(250, true, 2.4F, 1.8F, false).setBiomeName("NyxMesaForest");
	
	public static BiomeGenBase 
		nyxRugged = new NyxBiomeBarren(249, true, 1.8F, 2.2F, false).setBiomeName("NyxRugged"),
		nyxInfested = new NyxBiomeInfested(248, true, 1.5F, 0.1F, false).setBiomeName("NyxInfested");
	
}
