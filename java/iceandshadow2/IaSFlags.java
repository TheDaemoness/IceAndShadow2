package iceandshadow2;

import iceandshadow2.nyx.world.biome.NyxBiomeInfested;
import iceandshadow2.nyx.world.biome.NyxBiomeRugged;
import net.minecraft.world.biome.BiomeGenBase;

public class IaSFlags {
	public static boolean flag_death_system = true;
	public static boolean flag_low_particles = false;
	public static boolean flag_report_ruins_gen = false;

	//YOU LIED.
	public static int dim_nyx_id = -2;
	
	//YOU LIED MORE.
	public static short 
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
	
	//Mod plugin flags.
	public static boolean flag_mod_thaumcraft = false;
}
