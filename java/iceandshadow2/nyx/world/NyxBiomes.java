package iceandshadow2.nyx.world;

import iceandshadow2.IaSFlags;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.biome.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class NyxBiomes {

	public static BiomeGenBase 
	nyxLowMountains,
	nyxHighMountains,
	nyxHills,
	nyxMesas,
	nyxHillForest,
	nyxMesaForest;

	public static BiomeGenBase //Special.
	nyxRugged,
	nyxInfested;

	public static void init() {
		nyxLowMountains = new NyxBiomeMountains(IaSFlags.biome_id_nyxLowMountains, true, 1.0F, 2.5F, false).
				setBlocks(NyxBlocks.permafrost,NyxBlocks.permafrost).setBiomeName("NyxLowMountains");
		nyxHighMountains = new NyxBiome(IaSFlags.biome_id_nyxHighMountains, true, 0.5F, 4.0F, false).
				setBlocks(Blocks.snow_layer,NyxBlocks.permafrost).setBiomeName("NyxHighMountains");

		nyxHills = new NyxBiome(IaSFlags.biome_id_nyxHills, true, 1.5F, 0.1F, false).
				setBiomeName("NyxHills");
		nyxMesas = new NyxBiome(IaSFlags.biome_id_nyxMesas, true, 2.3F, 1.5F, false).
				setBiomeName("NyxMesas");
		nyxHillForest = new NyxBiomeForest(IaSFlags.biome_id_nyxHillForest, true, 0.8F, 1.8F, false).
				setBiomeName("NyxHillForest");
		nyxMesaForest = new NyxBiomeFrozen(IaSFlags.biome_id_nyxMesaForest, true, 2.4F, 1.8F, false).
				setBiomeName("NyxMesaForest");

		nyxRugged = new NyxBiomeBarren(IaSFlags.biome_id_nyxRugged, true, 1.8F, 2.2F, false).
				setBiomeName("NyxRugged");
		nyxInfested = new NyxBiomeInfested(IaSFlags.biome_id_nyxInfested, true, 1.5F, 0.1F, false).
				setBiomeName("NyxInfested");
		
	}
	
	public static void registerBiomes() {
		BiomeDictionary.registerBiomeType(nyxLowMountains, 
				BiomeDictionary.Type.COLD, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY,
				BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(nyxHighMountains, 
				BiomeDictionary.Type.COLD, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY,
				BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.MOUNTAIN);
		
		BiomeDictionary.registerBiomeType(nyxHills, 
				BiomeDictionary.Type.COLD, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY,
				BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(nyxMesas, 
				BiomeDictionary.Type.COLD, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY,
				BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.MOUNTAIN);
		
		BiomeDictionary.registerBiomeType(nyxHillForest, 
				BiomeDictionary.Type.COLD, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.DRY, 
				BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(nyxMesaForest, 
				BiomeDictionary.Type.COLD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.CONIFEROUS,
				BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.MOUNTAIN);
		
		BiomeDictionary.registerBiomeType(nyxRugged, 
				BiomeDictionary.Type.COLD, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.SNOWY, 
				BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.MAGICAL);
		
		BiomeDictionary.registerBiomeType(nyxInfested, 
				BiomeDictionary.Type.COLD, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.DRY, BiomeDictionary.Type.SNOWY, 
				BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.SPOOKY);
	}

}
