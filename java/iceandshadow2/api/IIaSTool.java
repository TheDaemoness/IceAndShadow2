package iceandshadow2.api;

/**
 * Implemented by IaS's tools and material-based weapons.
 * 
 * This is a list of functions they're guaranteed to have, for convenience.
 * An item can be checked to be a material-based tool or weapon via instanceof.
 */
public interface IIaSTool {
	public EnumIaSToolClass getIaSToolClass();
	public boolean canRepair();
}
