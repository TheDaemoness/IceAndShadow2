package iceandshadow2.api;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Used to indicate what should be done with a given item stack on death.
 */
public enum EnumIaSDeathAction {
	DEFAULT,
	DROP,
	KEEP,
	DESTROY,
	ALTER_DROP,
	ALTER_KEEP;
	
	public boolean keep() {
		return this == KEEP || this == ALTER_KEEP;
	}
	
	public boolean drop() {
		return this == DROP || this == ALTER_DROP;
	}
	
	public boolean alter() {
		return this == ALTER_DROP || this == ALTER_KEEP;
	}
}
