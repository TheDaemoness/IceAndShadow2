package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemMultiTexturedGlow;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class NyxItemClockwork extends IaSBaseItemMultiTexturedGlow {

	public NyxItemClockwork(String id) {
		super(EnumIaSModule.NYX, id, 3);
	}

	@Override
	public EnumRarity getRarity(ItemStack is) {
		return is.getItemDamage()>1?EnumRarity.common:EnumRarity.uncommon;
	}
}
