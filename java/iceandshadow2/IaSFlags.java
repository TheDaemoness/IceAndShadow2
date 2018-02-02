package iceandshadow2;

public class IaSFlags {
	/** Enables the alternate death system on Nyx. */
	public static boolean flag_death_system = true;
	
	/** Forces low particles even on fancy graphics. */
	public static boolean flag_low_particles = false;
	
	/** Prints the generation of ruins to standard output. */
	public static boolean flag_report_ruins_gen = false;
	
	/** Adds hint messages to item tooltips. */
	public static boolean flag_hints = true;

	/** Nyx's dimension ID. */
	public static int dim_nyx_id = -2;

	/** Nyx's biome IDs. */
	public static short biome_id_nyxMountains = 254, biome_id_nyxForest = 253, biome_id_nyxMesa = 252,
			biome_id_nyxRugged = 251, biome_id_nyxInfested = 250;

	/** The IDs at which IaS2 starts mapping its entities to Minecraft spawn eggs. */
	public static int entity_id_start = 512;

	/** Not yet implemented. Add extra support for Thaumcraft 4. */
	public static boolean flag_mod_thaumcraft = false;
}
