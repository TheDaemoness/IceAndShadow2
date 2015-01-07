package iceandshadow2.nyx.tileentities;

import iceandshadow2.ias.IaSTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NyxTeSingleItemStorage extends IaSTileEntity {
	public ItemStack item;

	@Override
	public void writeToNBT(NBTTagCompound par1) {
		super.writeToNBT(par1);
		NBTTagCompound eyetemme = par1.getCompoundTag("nyxItem");
		item.writeToNBT(eyetemme);
		par1.setTag("nyxItem", eyetemme);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1) {
		super.readFromNBT(par1);
		if(par1.hasKey("nyxItem"))
			item.readFromNBT(par1.getCompoundTag("nyxItem"));
	}
}
