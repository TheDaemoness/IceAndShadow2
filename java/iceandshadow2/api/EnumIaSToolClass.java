package iceandshadow2.api;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public enum EnumIaSToolClass {
	AXE(0, 3.0F, ImmutableSet.of("axe"), false), PICKAXE(1, 2.0F, ImmutableSet
			.of("pickaxe"), false), SPADE(2, 1.0F, ImmutableSet.of("spade"),
			false), SWORD(0, 4.0F, ImmutableSet.of("sword"), true), KNIFE(1,
			2.0F, ImmutableSet.of(), true);

	public static EnumIaSToolClass fromId(int itemDamage, boolean isSword) {
		for (final EnumIaSToolClass cl : values()) {
			if (cl.id == itemDamage && cl.isSword == isSword)
				return cl;
		}
		return null;
	}

	private int id;
	private float attackDmg;
	private final Set<String> classes;

	private final boolean isSword;

	EnumIaSToolClass(int id, float dmg, Set cls, boolean isSword) {
		this.id = id;
		this.attackDmg = dmg;
		classes = cls;
		this.isSword = isSword;
	}

	public float getBaseDamage() {
		return attackDmg;
	}

	public int getClassId() {
		return id;
	}

	public Set<String> getToolClassSet() {
		return classes;
	}

	public boolean isWeapon() {
		return isSword;
	}

	@Override
	public String toString() {
		final String s = super.toString();
		String sl = s.substring(0, 1);
		for (int i = 1; i < s.length(); ++i)
			sl += Character.toLowerCase(s.charAt(i));
		return sl;
	}
}
