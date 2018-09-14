package iceandshadow2.ias.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public enum EnumIaSAspect {
	NULL(true), ALIEN(false), NATIVE(false), LAND(false), FROZEN(false), NYX(false), POISONWOOD(false),
	INFESTATION(false), EXOUSIUM(true), NAVISTRA(true), ANCIENT(false), STYX(false), PURE(false);

	public static EnumIaSAspect getAspect(Object o) {
		if (o instanceof IIaSAspect) {
			final EnumIaSAspect ret = ((IIaSAspect) o).getAspect();
			return (ret == null || ret == NULL) ? ALIEN : ret;
		}
		if (o instanceof EntityItem)
			return getAspect(((EntityItem) o).getEntityItem());
		if (o instanceof ItemStack) {
			final Item it = ((ItemStack) o).getItem();
			if (it instanceof IIaSTool)
				return getAspect(IaSToolMaterial.extractMaterial((ItemStack) o));
			return getAspect(it);
		}
		if (o instanceof ItemBlock)
			return getAspect(((ItemBlock) o).field_150939_a);
		if (o instanceof EntityPlayer)
			return ALIEN;
		if (o == null)
			return NULL;
		final SideOnly side = o.getClass().getAnnotation(SideOnly.class);
		if (side != null && side.value() == Side.CLIENT)
			return NULL;
		return ALIEN;
	}

	public static EnumRarity getRarity(EnumIaSAspect aspect) {
		return aspect == EnumIaSAspect.STYX || aspect == EnumIaSAspect.PURE ? EnumRarity.uncommon : EnumRarity.common;
	}

	public boolean indestructible;

	EnumIaSAspect(boolean noExousium) {
		indestructible = noExousium;
	}
}
