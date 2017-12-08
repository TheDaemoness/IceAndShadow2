package iceandshadow2.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IIaSPassiveEffectItem {
	public void onItemUpdateTick(EntityLivingBase owner, ItemStack item, boolean equipped);
}
