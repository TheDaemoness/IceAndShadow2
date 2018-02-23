package iceandshadow2;

import iceandshadow2.ias.api.EnumIaSAspect;

public enum EnumIaSModule {
	IAS("ias", null), NYX("nyx", EnumIaSAspect.NATIVE), STYX("nyxcosmos", EnumIaSAspect.STYX);

	public final String prefix;
	public final EnumIaSAspect aspect;

	EnumIaSModule(String str, EnumIaSAspect a) {
		prefix = str;
		aspect = a;
	}
}
