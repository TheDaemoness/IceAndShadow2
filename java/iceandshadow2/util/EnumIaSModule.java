package iceandshadow2.util;

public enum EnumIaSModule {
	IAS("ias"),
	NYX("nyx"),
	MAGIC("magic"),
	ABYSS("abyss");
	
	public final String prefix;
	EnumIaSModule(String str) {
		prefix = str;
	}
}
