package iceandshadow2.api;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public enum EnumIaSAspect {
	NATIVE, LAND, FROZEN, NYX, POISONWOOD, INFESTATION, EXOUSIUM, NAVISTRA, ANCIENT, BLOOD, PURE, DRACONIUM;

	public static EnumIaSAspect getAspect(Object o) {
		if (o instanceof IIaSAspect)
			return ((IIaSAspect) o).getAspect();
		if (o instanceof ItemStack)
			return getAspect(((ItemStack) o).getItem());
		if (o instanceof ItemBlock)
			return getAspect(((ItemBlock) o).field_150939_a);
		return null;
	}
}
