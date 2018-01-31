package iceandshadow2.api;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public enum EnumIaSToolClass {
	AXE(0, 3.0F, ImmutableSet.of("axe"), false), PICKAXE(1, 2.0F, ImmutableSet.of("pickaxe"), false), SPADE(2, 1.0F,
			ImmutableSet.of("shovel"), //SOME CONSISTENCY MIGHT BE NICE.
			false), SWORD(0, 4.0F, ImmutableSet.of("sword"), true), KNIFE(1, 2.0F, ImmutableSet.of("sword"), true);

	public static EnumIaSToolClass fromId(int itemDamage, boolean isSword) {
		for (final EnumIaSToolClass cl : EnumIaSToolClass.values())
			if (cl.id == itemDamage && cl.isSword == isSword)
				return cl;
		return null;
	}

	private int id;
	private String tool;
	private float attackDmg;
	private final ImmutableSet<String> classes;

	private final boolean isSword;

	EnumIaSToolClass(int id, float dmg, ImmutableSet cls, boolean isSword) {
		this.id = id;
		attackDmg = dmg;
		if(cls == null || cls.isEmpty()) {
			classes = ImmutableSet.of();
			tool = null;
		} else {
			classes = cls;
			tool = (String)cls.toArray()[0];
		}
		this.isSword = isSword;
	}

	public float getBaseDamage() {
		return attackDmg;
	}

	public int getClassId() {
		return id;
	}

	public String getPrimaryToolClass() {
		return tool;
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
		for (int i = 1; i < s.length(); ++i) {
			sl += Character.toLowerCase(s.charAt(i));
		}
		return sl;
	}
}
