package iceandshadow2.nyx.tileentities;

import iceandshadow2.ias.IaSTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NyxTeSingleItemStorage extends IaSTileEntity {
	public ItemStack item;

	@Override
	public void writeToNBT(NBTTagCompound par1) {
		super.writeToNBT(par1);
		item.writeToNBT(par1);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1) {
		super.readFromNBT(par1);
		item.readFromNBT(par1);
	}
}
