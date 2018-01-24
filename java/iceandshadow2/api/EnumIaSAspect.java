package iceandshadow2.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public enum EnumIaSAspect {
	VIRTUAL, NATIVE, LAND, FROZEN, NYX, POISONWOOD, INFESTATION, EXOUSIUM, NAVISTRA, ANCIENT, STYX, PURE;

	public static EnumIaSAspect getAspect(Object o) {
		if (o == null)
			return VIRTUAL;
		if (o instanceof IIaSAspect)
			return ((IIaSAspect) o).getAspect();
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
			return null;
		final SideOnly side = o.getClass().getAnnotation(SideOnly.class);
		if (side != null && side.value() == Side.CLIENT)
			return VIRTUAL;
		return null;
	}

	public static EnumRarity getRarity(EnumIaSAspect aspect) {
		return aspect == EnumIaSAspect.STYX || aspect == EnumIaSAspect.PURE?EnumRarity.uncommon:EnumRarity.common;
	}
}
