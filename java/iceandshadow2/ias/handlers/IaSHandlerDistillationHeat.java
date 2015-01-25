package iceandshadow2.ias.handlers;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import iceandshadow2.api.IIaSApiDistillable;
import iceandshadow2.nyx.NyxItems;

public class IaSHandlerDistillationHeat implements IIaSApiDistillable {

	private static int getTime(ItemStack target) {
		if (target.getItem() == Items.blaze_rod)
			return 2400;
		if (target.getItem() == Items.blaze_powder)
			return 800;
		if (target.getItem() == Items.coal)
			return 1600;
		if (target.getItem() == Items.fire_charge)
			return 400;
		if (target.getItem() == Items.lava_bucket)
			return 20000;
		return GameRegistry.getFuelValue(target);
	}

	@Override
	public int getDistillationRate(ItemStack target) {
		final int time = getTime(target);
		if (target == null || time < 200)
			return 0;
		int mod = 200;
		if (time >= 12800)
			mod = 12800;
		if (time % 3200 == 0)
			mod = 3200;
		if (time > 3200)
			mod = 800;
		if (time % 800 == 0)
			mod = 800;
		return Math.min(target.stackSize, 64 * mod / time);
	}

	@Override
	public ItemStack getDistillationYield(ItemStack target) {
		final int time = getTime(target);
		if (time >= 12800)
			return new ItemStack(NyxItems.heat, Math.min(64, time / 12800
					* target.stackSize), 3);
		if (time % 3200 == 0)
			return new ItemStack(NyxItems.heat, Math.min(64, time / 3200
					* target.stackSize), 2);
		if (time > 3200)
			return new ItemStack(NyxItems.heat, Math.min(64, time / 800
					* target.stackSize), 1);
		if (time % 800 == 0)
			return new ItemStack(NyxItems.heat, Math.min(64, time / 800
					* target.stackSize), 1);
		return new ItemStack(NyxItems.heat, Math.min(64, time / 200
				* target.stackSize), 0);
	}
}
