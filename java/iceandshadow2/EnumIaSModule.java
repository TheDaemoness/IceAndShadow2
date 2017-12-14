package iceandshadow2;

public enum EnumIaSModule {
	IAS("ias"), NYX("nyx"), STYX("nyxcosmos");

	public final String prefix;

	EnumIaSModule(String str) {
		prefix = str;
	}
}
