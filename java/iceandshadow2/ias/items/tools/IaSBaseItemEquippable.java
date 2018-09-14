package iceandshadow2.ias.items.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public abstract class IaSBaseItemEquippable extends IaSBaseItemSingle {

	public static final String nbtAmmoID = "iasAmmo";

	public IaSBaseItemEquippable(EnumIaSModule mod, String id) {
		super(mod, id);
		setMaxStackSize(1);
	}

	public ItemStack ammoExtract(ItemStack is) {
		final ItemStack ammo = ammoGet(is);
		if (ammo != null)
			is.getTagCompound().removeTag(nbtAmmoID);
		final Item itemForUnloading = getItemForUnloading();
		if (itemForUnloading != is.getItem())
			is.func_150996_a(itemForUnloading);
		is.setItemDamage(is.getItemDamage() & (~1));
		return null;
	}

	public ItemStack ammoGet(ItemStack is) {
		if (is.hasTagCompound()) {
			NBTTagCompound tags = is.getTagCompound();
			if (tags.hasKey(nbtAmmoID, tags.getId()))
				tags = tags.getCompoundTag(nbtAmmoID);
			else
				return null;
			final ItemStack ret = new ItemStack(this);
			ret.readFromNBT(tags);
		}
		return null;
	}

	public ItemStack ammoInsert(ItemStack is, ItemStack ammo) {
		if (!canAcceptAmmo(ammo))
			return null;
		ItemStack ret = null;
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		else
			ret = ammoExtract(is);
		final NBTTagCompound ammodata = new NBTTagCompound();
		ammo.writeToNBT(ammodata);
		ammo.stackSize = 0;
		is.getTagCompound().setTag(nbtAmmoID, ammodata);
		final Item itemForLoading = getItemForLoading(ammo);
		if (itemForLoading != is.getItem())
			is.func_150996_a(itemForLoading);
		is.setItemDamage(is.getItemDamage() | 1);
		return ret;
	}

	public boolean canAcceptAmmo(ItemStack ammo) {
		return getItemForLoading(ammo) != null;
	}

	public Item getItemForLoading(ItemStack ammo) {
		if (ammo == null || ammo.getItem() == null)
			return null;
		return this;
	}

	public Item getItemForUnloading() {
		return this;
	}

}
