package iceandshadow2.nyx.world;

import iceandshadow2.IaSFlags;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.biome.NyxBiome;
import iceandshadow2.nyx.world.biome.NyxBiomeForestDense;
import iceandshadow2.nyx.world.biome.NyxBiomeForestSparse;
import iceandshadow2.nyx.world.biome.NyxBiomeInfested;
import iceandshadow2.nyx.world.biome.NyxBiomeMountains;
import iceandshadow2.nyx.world.biome.NyxBiomeExousic;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class NyxBiomes {

	public static BiomeGenBase nyxMountains, nyxHillForest, nyxMesaForest;

	public static BiomeGenBase // Special.
	nyxExousic, nyxInfested;

	public static void init() {
		NyxBiomes.nyxMountains = new NyxBiomeMountains(IaSFlags.biome_id_nyxMountains, true, 1.0F, 2.5F, false)
				.setBiomeName("NyxMountains");

		NyxBiomes.nyxHillForest = new NyxBiomeForestDense(IaSFlags.biome_id_nyxForest, true, 0.4F, 0.5F, false)
				.setBiomeName("NyxForest");
		NyxBiomes.nyxMesaForest = new NyxBiomeForestSparse(IaSFlags.biome_id_nyxMesa, true, 1.5F, 0.15F, false)
				.setBiomeName("NyxMesa");

		NyxBiomes.nyxExousic = new NyxBiomeExousic(IaSFlags.biome_id_nyxRugged, true, 1.0F, 1.2F, false)
				.setBiomeName("NyxExousic");
		NyxBiomes.nyxInfested = new NyxBiomeInfested(IaSFlags.biome_id_nyxInfested, true, 1.25F, 0.3F, false)
				.setBiomeName("NyxInfested");
	}

	public static boolean isRare(BiomeGenBase b) {
		return b.biomeID == NyxBiomes.nyxExousic.biomeID || b.biomeID == NyxBiomes.nyxInfested.biomeID;
	}

	public static void registerBiomes() {
		BiomeDictionary.registerBiomeType(NyxBiomes.nyxMountains, Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY,
				Type.SNOWY, Type.WASTELAND, Type.MOUNTAIN);

		BiomeDictionary.registerBiomeType(NyxBiomes.nyxHillForest, Type.COLD, Type.DENSE, Type.CONIFEROUS, Type.DRY,
				Type.SNOWY, Type.FOREST, Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(NyxBiomes.nyxMesaForest, Type.COLD, Type.DRY, Type.CONIFEROUS, Type.SNOWY,
				Type.MESA);

		BiomeDictionary.registerBiomeType(NyxBiomes.nyxExousic, Type.COLD, Type.SPARSE, Type.DEAD, Type.DRY, Type.SNOWY,
				Type.WASTELAND, Type.MOUNTAIN, Type.SPOOKY);

		BiomeDictionary.registerBiomeType(NyxBiomes.nyxInfested, Type.COLD, Type.DENSE, Type.DRY, Type.SNOWY,
				Type.FOREST, Type.MOUNTAIN, Type.SPOOKY);
	}

}
