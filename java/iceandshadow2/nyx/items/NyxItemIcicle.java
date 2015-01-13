package iceandshadow2.nyx.items;

import java.util.List;

import net.minecraft.item.ItemStack;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmutable;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxItems;

public class NyxItemIcicle extends IaSBaseItemSingle implements IIaSApiTransmutable {

	public NyxItemIcicle(String texName) {
		super(EnumIaSModule.NYX, texName);
	}

	@Override
	public int getTransmutationTime(ItemStack target, ItemStack catalyst) {
		if(!target.isItemDamaged())
			return 0;
		if(target.getItem() == NyxItems.frostBowLong)
			return 12;
		if(target.getItem() == NyxItems.frostBowShort)
			return 6;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmutationYield(ItemStack target,
			ItemStack catalyst) {
		if(catalyst != null)
			catalyst.stackSize -= 1;
		if(target != null)
			target.setItemDamage(Math.max(0,target.getItemDamage()-1));
		return null;
	}
}
