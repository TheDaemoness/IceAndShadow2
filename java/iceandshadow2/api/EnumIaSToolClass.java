package iceandshadow2.api;

import java.util.HashSet;
import java.util.Set;

public enum EnumIaSToolClass {
	AXE(0, 3.0F, new String[]{"axe"}),
	PICKAXE(1, 2.0F, new String[]{"pickaxe"}),
	SPADE(2, 1.0F, new String[]{"spade"}),
	SWORD(3, 4.0F, new String[]{"sword"}),
	KNIFE(4, 2.0F, new String[]{});
	private int id;
	private float dmg;
	private Set<String> classes;
	
	EnumIaSToolClass(int id, float dmg, String[] cls) {
		this.id = id;
		this.dmg = dmg;
		classes = new HashSet<String>();
		for(String s : cls)
			classes.add(s);
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
		return dmg;
	}

	public Set<String> getClassSet() {
		return classes;
	}
}
