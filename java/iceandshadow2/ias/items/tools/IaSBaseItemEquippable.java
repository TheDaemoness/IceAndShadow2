package iceandshadow2.ias.items.tools;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmuteLens;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxItems;

public abstract class IaSBaseItemEquippable extends IaSBaseItemSingle {
	
	public static final String nbtAmmoID = "iasAmmo";

	public IaSBaseItemEquippable(EnumIaSModule mod, String id) {
		super(mod, id);
		setMaxStackSize(1);
	}
	
	public Item getItemForLoading(ItemStack ammo) {
		if(ammo == null || ammo.getItem() == null)
			return null;
		return this;
	}
	public Item getItemForUnloading() {
		return this;
	}

	
	public boolean canAcceptAmmo(ItemStack ammo) {
		return getItemForLoading(ammo) != null;
	}
	
	public ItemStack ammoGet(ItemStack is) {
		if(is.hasTagCompound()) {
			NBTTagCompound tags = is.getTagCompound();
			if(tags.hasKey(nbtAmmoID, tags.getId()))
				tags = tags.getCompoundTag(nbtAmmoID);
			else
				return null;
			final ItemStack ret = new ItemStack(this);
			ret.readFromNBT(tags);
		}
		return null;
	}
	public ItemStack ammoExtract(ItemStack is) {
		final ItemStack ammo = ammoGet(is);
		if(ammo != null)
			is.getTagCompound().removeTag(nbtAmmoID);
		final Item itemForUnloading = getItemForUnloading();
		if(itemForUnloading != is.getItem()) {
			is.func_150996_a(itemForUnloading);
		}
		is.setItemDamage(is.getItemDamage() & (~1));
		return null;
	}
	public ItemStack ammoInsert(ItemStack is, ItemStack ammo) {
		if(!canAcceptAmmo(ammo))
			return null;
		ItemStack ret = null;
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		else
			ret = ammoExtract(is);
		NBTTagCompound ammodata = new NBTTagCompound();
		ammo.writeToNBT(ammodata);
		ammo.stackSize = 0;
		is.getTagCompound().setTag(nbtAmmoID, ammodata);
		final Item itemForLoading = getItemForLoading(ammo);
		if(itemForLoading != is.getItem()) {
			is.func_150996_a(itemForLoading);
		}
		is.setItemDamage(is.getItemDamage() | 1);
		return ret;
	}

}
