package iceandshadow2.nyx.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxItems;

public class NyxItemIcicle extends IaSBaseItemSingle implements
IIaSApiTransmute {

	public NyxItemIcicle(String texName) {
		super(EnumIaSModule.NYX, texName);
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (!target.isItemDamaged())
			return 0;
		if (target.getItem() == NyxItems.frostBowLong)
			return 12;
		if (target.getItem() == NyxItems.frostBowShort)
			return 8;
		if (target.getItem() == NyxItems.frostSword)
			return 12;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target,
			ItemStack catalyst, World w) {
		catalyst.stackSize -= 1;
		if (target.getItem() == NyxItems.frostBowLong)
			target.setItemDamage(Math.max(0, target.getItemDamage() - 1));
		if (target.getItem() == NyxItems.frostBowShort)
			target.setItemDamage(Math.max(0, target.getItemDamage() - 2));
		if (target.getItem() == NyxItems.frostSword)
			target.setItemDamage(Math.max(0, target.getItemDamage() - 2));
		return null;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity pos) {
		return false;
	}
}
