package iceandshadow2.nyx.tileentities;

import iceandshadow2.ias.IaSTileEntity;
import iceandshadow2.ias.blocks.IaSBaseBlockTeRefCount;
import iceandshadow2.adapters.EnumNBTType;
import net.minecraft.nbt.NBTTagCompound;

public class NyxTeRefCount extends IaSTileEntity {
	public long refcount; // Overly defensive.

	public NyxTeRefCount() {
		refcount = 0;
	}

	public void addRef() {
		if (blockType == null)
			blockType = worldObj.getBlock(xCoord, yCoord, zCoord);
		if (refcount == 0)
			((IaSBaseBlockTeRefCount) blockType).onReferenceBegin(worldObj, xCoord, yCoord, zCoord);
		++refcount;
	}

	@Override
	public void readFromNBT(NBTTagCompound in) {
		super.readFromNBT(in);
		refcount = EnumNBTType.LONG.get(in, "nyxRefCount");
	}

	public void rmRef() {
		if (blockType == null)
			blockType = worldObj.getBlock(xCoord, yCoord, zCoord);
		if (refcount == 1) {
			refcount = 0;
			((IaSBaseBlockTeRefCount) blockType).onReferenceEnd(worldObj, xCoord, yCoord, zCoord);
		} else if (refcount > 0)
			--refcount;
	}

	@Override
	public void writeToNBT(NBTTagCompound in) {
		super.writeToNBT(in);
		EnumNBTType.set(in, "nyxRefCount", refcount);
	}
}
