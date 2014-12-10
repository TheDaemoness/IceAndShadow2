package iceandshadow2.api;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public enum EnumIaSToolClass {
	AXE(0, 3.0F, ImmutableSet.of("axe")),
	PICKAXE(1, 2.0F, ImmutableSet.of("pickaxe")),
	SPADE(2, 1.0F, ImmutableSet.of("spade")),
	SWORD(3, 4.0F, ImmutableSet.of("sword")),
	KNIFE(4, 2.0F, ImmutableSet.of());
	
	private int id;
	private float attackDmg;
	private final Set<String> classes;
	
	EnumIaSToolClass(int id, float dmg, Set cls) {
		this.id = id;
		this.attackDmg = dmg;
		classes = cls;
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		String sl = s.substring(0,1);
		for(int i = 1; i < s.length(); ++i)
			sl += Character.toLowerCase(s.charAt(i));
		return sl;
	}
	
	public int getClassId() {
		return id;
	}
	public float getBaseDamage() {
		return attackDmg;
	}

	public Set<String> getToolClassSet() {
		return classes;
	}

	public static EnumIaSToolClass fromId(int itemDamage) {
		for(EnumIaSToolClass cl : values()) {
			if(cl.id == itemDamage)
				return cl;
		}
		return null;
	}
}
