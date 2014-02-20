package iceandshadow2.util;

public enum EnumIaSModule {
	MODULE_IAS("ias"),
	MODULE_NYX("nyx"),
	MODULE_URBAE("urbae"),
	MODULE_ABYSS("abyss");
	
	public final String prefix;
	EnumIaSModule(String str) {
		prefix = str;
	}
}
