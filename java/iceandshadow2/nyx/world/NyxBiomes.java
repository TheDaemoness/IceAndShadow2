package iceandshadow2.nyx.world;

import iceandshadow2.IaSFlags;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.biome.NyxBiome;
import iceandshadow2.nyx.world.biome.NyxBiomeForestDense;
import iceandshadow2.nyx.world.biome.NyxBiomeForestSparse;
import iceandshadow2.nyx.world.biome.NyxBiomeInfested;
import iceandshadow2.nyx.world.biome.NyxBiomeMountains;
import iceandshadow2.nyx.world.biome.NyxBiomeRugged;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

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
		nyxMesas = new NyxBiome(IaSFlags.biome_id_nyxMesas, true, 2.5F, 1.0F, false).
				setBiomeName("NyxMesas");
		nyxHillForest = new NyxBiomeForestDense(IaSFlags.biome_id_nyxHillForest, true, 0.8F, 1.8F, false).
				setBiomeName("NyxHillForest");
		nyxMesaForest = new NyxBiomeForestSparse(IaSFlags.biome_id_nyxMesaForest, true, 2.5F, 1.0F, false).
				setBiomeName("NyxMesaForest");

		nyxRugged = new NyxBiomeRugged(IaSFlags.biome_id_nyxRugged, true, 1.0F, 1.2F, false).
				setBiomeName("NyxRugged");
		nyxInfested = new NyxBiomeInfested(IaSFlags.biome_id_nyxInfested, true, 1.5F, 0.1F, false).
				setBiomeName("NyxInfested");
		
	}
	
	public static void registerBiomes() {
		BiomeDictionary.registerBiomeType(nyxLowMountains, 
				Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY,
				Type.SNOWY, Type.WASTELAND, Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(nyxHighMountains, 
				Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY,
				Type.SNOWY, Type.WASTELAND, Type.MOUNTAIN);
		
		BiomeDictionary.registerBiomeType(nyxHills, 
				Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY,
				Type.SNOWY, Type.WASTELAND, Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(nyxMesas, 
				Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY,
				Type.SNOWY, Type.WASTELAND, Type.MESA);
		
		BiomeDictionary.registerBiomeType(nyxHillForest, 
				Type.COLD, Type.DENSE, Type.CONIFEROUS, Type.DRY, 
				Type.SNOWY, Type.FOREST, Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(nyxMesaForest, 
				Type.COLD, Type.DRY, Type.CONIFEROUS,
				Type.SNOWY, Type.MESA);
		
		BiomeDictionary.registerBiomeType(nyxRugged, 
				Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY, Type.SNOWY, 
				Type.WASTELAND, Type.MOUNTAIN, Type.SPOOKY);
		
		BiomeDictionary.registerBiomeType(nyxInfested, 
				Type.COLD, Type.DENSE, Type.DRY, Type.SNOWY, 
				Type.FOREST, Type.MOUNTAIN, Type.SPOOKY);
	}

}
