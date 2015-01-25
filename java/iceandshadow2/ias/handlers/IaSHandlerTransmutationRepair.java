package iceandshadow2.ias.handlers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.api.IIaSApiTransmutable;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;

public class IaSHandlerTransmutationRepair implements IIaSApiTransmutable {

	@Override
	public int getTransmutationTime(ItemStack target, ItemStack catalyst) {
		if (!target.isItemDamaged())
			return 0;
		final Item it = target.getItem();
		if (it instanceof ItemArmor) {
			if (catalyst.getItem() == ((ItemArmor) it).getArmorMaterial().customCraftingMaterial) {
				if (catalyst.getItem() == NyxItems.echirIngot
						&& catalyst.getItemDamage() <= 0)
					return 0;
				return 850;
			}
		}
		if (it instanceof IIaSTool) {
			if (((IIaSTool)it).canRepair()) {
				if (IaSToolMaterial.extractMaterial(target).isRepairable(target,catalyst))
					return 450;
			}
			return 0;
		}
		if (it.getIsRepairable(target, catalyst))
			return 450;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmutationYield(ItemStack target,
			ItemStack catalyst, World world) {
		final Item it = target.getItem();
		target.setItemDamage(Math.max(0,
				target.getItemDamage() - it.getMaxDamage(target) / 5));
		--catalyst.stackSize;
		return null;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent) {
		return false;
	}

}
