package iceandshadow2.nyx.forge;

import iceandshadow2.nyx.NyxItems;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class NyxFuelHandler implements IFuelHandler {
	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.getItem() == NyxItems.devora) {
			if (fuel.getItemDamage() == 1)
				return 200;
			else
				return 2400;
		}
		if (fuel.getItem() instanceof ItemBlock) {
			// Fukkit.
		}
		return 0;
	}
}
