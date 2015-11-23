package iceandshadow2.nyx.tileentities;

import iceandshadow2.ias.IaSTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NyxTeSingleItemStorage extends IaSTileEntity {
	public ItemStack item;

	@Override
	public void readFromNBT(NBTTagCompound par1) {
		super.readFromNBT(par1);
		if (par1.hasKey("nyxItem"))
			this.item.readFromNBT(par1.getCompoundTag("nyxItem"));
	}

	@Override
	public void writeToNBT(NBTTagCompound par1) {
		super.writeToNBT(par1);
		final NBTTagCompound eyetemme = par1.getCompoundTag("nyxItem");
		this.item.writeToNBT(eyetemme);
		par1.setTag("nyxItem", eyetemme);
	}
}
