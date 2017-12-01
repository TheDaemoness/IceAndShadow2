package iceandshadow2.ias.handlers;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.nyx.NyxItems;

public class IaSHandlerTransmutationHeat implements IIaSApiTransmute {

	private static int getTime(ItemStack target) {
		if (target.getItem() == Items.blaze_rod)
			return 2400;
		if (target.getItem() == Items.blaze_powder)
			return 800;
		if (target.getItem() == Items.magma_cream)
			return 800;
		if (target.getItem() == Items.fire_charge)
			return 400;
		if (target.getItem() == Items.lava_bucket)
			return 20000;
		return GameRegistry.getFuelValue(target);
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (catalyst.getItem() != NyxItems.resin)
			return 0;
		final int time = IaSHandlerTransmutationHeat.getTime(target);
		if (time < 200)
			return 0;
		return (int) Math.sqrt(time) * Math.max(4, target.stackSize);
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		if (target.getItem() == Items.lava_bucket)
			is.add(new ItemStack(Items.bucket, 1));
		final int timeInit = IaSHandlerTransmutationHeat.getTime(target);
		int resinCredit = 0;
		for (int i = 0; i < 4 && target.stackSize > 0; ++i) {
			int time = timeInit;
			while (time >= 200 && (catalyst.stackSize > 0 || resinCredit > 0)) {
				if (time >= 12800 && catalyst.stackSize >= 16) {
					catalyst.stackSize -= 16;
					is.add(new ItemStack(NyxItems.heat, 1, 3));
					time -= 12800;
				} else if (time >= 3200 && catalyst.stackSize >= 4) {
					catalyst.stackSize -= 4;
					is.add(new ItemStack(NyxItems.heat, 1, 2));
					time -= 3200;
				} else if (time >= 800 && catalyst.stackSize >= 1) {
					catalyst.stackSize -= 1;
					is.add(new ItemStack(NyxItems.heat, 1, 1));
					time -= 800;
				} else {
					if (resinCredit == 0 && catalyst.stackSize >= 1) {
						resinCredit = 4;
						catalyst.stackSize -= 1;
					}
					if (resinCredit > 0) {
						final int count = Math.min(resinCredit, time / 200);
						resinCredit -= count;
						is.add(new ItemStack(NyxItems.heat, count, 0));
						time -= 200 * count;
					}
				}
			}
			if (time < timeInit)
				--target.stackSize;
		}
		return is;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}
}
