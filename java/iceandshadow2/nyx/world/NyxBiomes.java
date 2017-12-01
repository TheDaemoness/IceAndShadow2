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

	public static BiomeGenBase nyxLowMountains, nyxHighMountains, nyxHills, nyxMesas, nyxHillForest, nyxMesaForest;

	public static BiomeGenBase // Special.
	nyxRugged, nyxInfested;

	public static void init() {
		NyxBiomes.nyxLowMountains = new NyxBiomeMountains(IaSFlags.biome_id_nyxLowMountains, true, 1.0F, 2.5F, false)
				.setBlocks(NyxBlocks.permafrost, NyxBlocks.permafrost).setBiomeName("NyxLowMountains");
		NyxBiomes.nyxHighMountains = new NyxBiome(IaSFlags.biome_id_nyxHighMountains, true, 0.5F, 4.0F, false)
				.setBlocks(Blocks.snow_layer, NyxBlocks.permafrost).setBiomeName("NyxHighMountains");

		NyxBiomes.nyxHills = new NyxBiome(IaSFlags.biome_id_nyxHills, true, 1.5F, 0.1F, false).setBiomeName("NyxHills");
		NyxBiomes.nyxMesas = new NyxBiome(IaSFlags.biome_id_nyxMesas, true, 2.5F, 1.0F, false).setBiomeName("NyxMesas");
		NyxBiomes.nyxHillForest = new NyxBiomeForestDense(IaSFlags.biome_id_nyxHillForest, true, 0.8F, 1.8F, false)
				.setBiomeName("NyxHillForest");
		NyxBiomes.nyxMesaForest = new NyxBiomeForestSparse(IaSFlags.biome_id_nyxMesaForest, true, 2.5F, 1.0F, false)
				.setBiomeName("NyxMesaForest");

		NyxBiomes.nyxRugged = new NyxBiomeRugged(IaSFlags.biome_id_nyxRugged, true, 1.0F, 1.2F, false)
				.setBiomeName("NyxRugged");
		NyxBiomes.nyxInfested = new NyxBiomeInfested(IaSFlags.biome_id_nyxInfested, true, 1.5F, 0.1F, false)
				.setBiomeName("NyxInfested");
	}

	public static boolean isRare(BiomeGenBase b) {
		return b.biomeID == NyxBiomes.nyxRugged.biomeID || b.biomeID == NyxBiomes.nyxInfested.biomeID;
	}

	public static void registerBiomes() {
		BiomeDictionary.registerBiomeType(NyxBiomes.nyxLowMountains, Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY,
				Type.SNOWY, Type.WASTELAND, Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(NyxBiomes.nyxHighMountains, Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY,
				Type.SNOWY, Type.WASTELAND, Type.MOUNTAIN);

		BiomeDictionary.registerBiomeType(NyxBiomes.nyxHills, Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY, Type.SNOWY,
				Type.WASTELAND, Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(NyxBiomes.nyxMesas, Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY, Type.SNOWY,
				Type.WASTELAND, Type.MESA);

		BiomeDictionary.registerBiomeType(NyxBiomes.nyxHillForest, Type.COLD, Type.DENSE, Type.CONIFEROUS, Type.DRY,
				Type.SNOWY, Type.FOREST, Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(NyxBiomes.nyxMesaForest, Type.COLD, Type.DRY, Type.CONIFEROUS, Type.SNOWY,
				Type.MESA);

		BiomeDictionary.registerBiomeType(NyxBiomes.nyxRugged, Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY, Type.SNOWY,
				Type.WASTELAND, Type.MOUNTAIN, Type.SPOOKY);

		BiomeDictionary.registerBiomeType(NyxBiomes.nyxInfested, Type.COLD, Type.DENSE, Type.DRY, Type.SNOWY,
				Type.FOREST, Type.MOUNTAIN, Type.SPOOKY);
	}

}
