package iceandshadow2.ias.api;

/**
 * Used to indicate what should be done with a given item stack on death.
 */
public enum EnumIaSDeathAction {
	DEFAULT, // Default behavior, allows fallthrough to other handlers.
	DROP, // Drops the ItemStack on death.
	KEEP, // Keeps the ItemStack on death.
	DESTROY, // Destroys the ItemStack on death.
	ALTER_DROP, // Alters and drops the ItemStack on death.
	ALTER_KEEP, // Alters and keeps the ItemStack on death.
	ALTER_BOTH; // Drops an ItemStack, but keeps an altered version.

	public boolean alter() {
		return this == ALTER_DROP || this == ALTER_KEEP || this == ALTER_BOTH;
	}

	public boolean drop() {
		return this == DROP || this == ALTER_DROP;
	}

	public boolean keep() {
		return this == KEEP || this == ALTER_KEEP;
	}
}
