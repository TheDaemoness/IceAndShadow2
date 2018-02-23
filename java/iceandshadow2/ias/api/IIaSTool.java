package iceandshadow2.ias.api;

/**
 * Implemented by IaS's tools and material-based weapons.
 *
 * This is a list of functions they're guaranteed to have, for convenience. An
 * item can be checked to be a material-based tool or weapon via instanceof.
 */
public interface IIaSTool {
	public boolean canRepair();

	public EnumIaSToolClass getIaSToolClass();
}
