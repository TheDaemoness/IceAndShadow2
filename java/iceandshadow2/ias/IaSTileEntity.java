package iceandshadow2.ias;

import java.lang.reflect.Field;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class IaSTileEntity extends TileEntity {
	
	@Override
	public void writeToNBT(NBTTagCompound par1) {
		super.writeToNBT(par1);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1) {
		super.readFromNBT(par1);
	}
}
