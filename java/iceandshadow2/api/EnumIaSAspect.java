package iceandshadow2.api;

public enum EnumIaSAspect {
	CREATIVE,
	LAND,
	FROZEN,
	NYX,
	POISONWOOD,
	INFESTATION,
	EXOUSIUM,
	NAVISTRA,
	ANCIENT,
	BLOOD,
	PURE;
	
	public static EnumIaSAspect getAspect(Object o) {
		if(o instanceof IIaSAspect)
			return ((IIaSAspect)o).getAspect();
		return null;
	}
}
