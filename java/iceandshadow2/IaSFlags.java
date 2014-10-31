package iceandshadow2;

import iceandshadow2.nyx.world.biome.NyxBiomeBarren;
import iceandshadow2.nyx.world.biome.NyxBiomeInfested;
import net.minecraft.world.biome.BiomeGenBase;

public class IaSFlags {
	public static boolean flag_pvp = false;
	public static boolean flag_help_messages = true;
	public static boolean flag_death_system = true;
	public static boolean flag_portal_sounds = true;
	public static boolean flag_low_particles = false;

	//YOU LIED.
	public static int dim_nyx_id = -2;
	public static int dim_abyss_id = -3;
	
	//YOU LIED MORE.
	public static int 
		biome_id_nyxLowMountains = 255,
		biome_id_nyxHighMountains = 254,
		biome_id_nyxHills = 253,
		biome_id_nyxMesas = 252,
		biome_id_nyxHillForest = 251,
		biome_id_nyxMesaForest = 250,
		biome_id_nyxRugged = 249,
		biome_id_nyxInfested = 248;
	
	//LIAR!
		public static int entity_id_start = 512;
	
	public static BiomeGenBase 
		nyxRugged = new NyxBiomeBarren(249, true, 1.8F, 2.2F, false).setBiomeName("NyxRugged"),
		nyxInfested = new NyxBiomeInfested(248, true, 1.5F, 0.1F, false).setBiomeName("NyxInfested");
	
	//Mod plugin flags.
	public static boolean flag_mod_thaumcraft = false;
	public static boolean flag_mod_tconstruct = false;
}
