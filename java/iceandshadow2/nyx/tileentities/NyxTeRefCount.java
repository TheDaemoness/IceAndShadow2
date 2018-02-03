package iceandshadow2.nyx.tileentities;

import java.util.Map;
import java.util.TreeMap;

import iceandshadow2.ias.IaSTileEntity;
import iceandshadow2.ias.blocks.IaSBaseBlockTeRefCount;
import iceandshadow2.ias.util.EnumNBTType;
import net.minecraft.nbt.NBTTagCompound;

public class NyxTeRefCount extends IaSTileEntity {
	public long refcount; //Overly defensive.

	public NyxTeRefCount() {
		refcount = 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound in) {
		super.readFromNBT(in);
		refcount = EnumNBTType.LONG.get(in, "nyxRefCount");
	}

	@Override
	public void writeToNBT(NBTTagCompound in) {
		super.writeToNBT(in);
		EnumNBTType.set(in, "nyxRefCount", refcount);
	}

	public void addRef() {
		if(this.blockType == null)
			this.blockType = worldObj.getBlock(xCoord, yCoord, zCoord);
		if(refcount == 0)
			((IaSBaseBlockTeRefCount)this.blockType).onReferenceBegin(worldObj, xCoord, yCoord, zCoord);
		++refcount;
	}
	public void rmRef() {
		if(this.blockType == null)
			this.blockType = worldObj.getBlock(xCoord, yCoord, zCoord);
		if(refcount == 1) {
			refcount = 0;
			((IaSBaseBlockTeRefCount)this.blockType).onReferenceEnd(worldObj, xCoord, yCoord, zCoord);
		} else if(refcount > 0)
			--refcount;
	}
}
