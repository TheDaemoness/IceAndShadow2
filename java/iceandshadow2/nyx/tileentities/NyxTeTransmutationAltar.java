package iceandshadow2.nyx.tileentities;

import iceandshadow2.api.IIaSApiTransmutable;
import iceandshadow2.ias.IaSTileEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class NyxTeTransmutationAltar extends IaSTileEntity {
	public ItemStack target;
	public ItemStack catalyst;
	public long finishOn;
	public IIaSApiTransmutable handler;
	
	public boolean handlePlace(ItemStack is) {
		if(catalyst == null) {
			catalyst = is;
			return true;
		}
		if(target == null) {
			target = is;
			return true;
		}
		return false;
	}
	
	public ItemStack handleRemove() {
		ItemStack temp;
		if(target != null) {
			finishOn = 0;
			temp = target;
			target = null;
			return temp;
		}
		if(catalyst != null) {
			finishOn = 0;
			temp = catalyst;
			catalyst = null;
			return temp;
		}
		return null;
	}
	
	public void scheduleUpdate(int x, int y, int z, int time) {
		this.worldObj.scheduleBlockUpdate(x, y, z, this.worldObj.getBlock(x, y, x), time);
		finishOn = this.worldObj.getTotalWorldTime()+time;
	}
	
	//Sanity check to avoid interrupted transmutations messing with new transmutations.
	public boolean isTransmutationDone() {
		return catalyst != null && target != null && this.worldObj.getTotalWorldTime() == finishOn;
	}

	@Override
	public void writeToNBT(NBTTagCompound par1) {
		super.writeToNBT(par1);

		NBTTagCompound eyetemme;
		if(target != null) {
			eyetemme = par1.getCompoundTag("nyxItemTarget");
			target.writeToNBT(eyetemme);
			par1.setTag("nyxItemTarget", eyetemme);
		}
		if(catalyst != null) {
			eyetemme = par1.getCompoundTag("nyxItemCatalyst");
			catalyst.writeToNBT(eyetemme);
			par1.setTag("nyxItemCatalyst", eyetemme);
		}
		
		par1.setLong("nyxTransmuteTime", finishOn);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1) {
		super.readFromNBT(par1);
		target = new ItemStack(Items.egg);
		catalyst = new ItemStack(Items.feather);
		
		if(par1.hasKey("nyxItemTarget"))
			target.readFromNBT(par1.getCompoundTag("nyxItemTarget"));
		else
			target = null;
		
		if(par1.hasKey("nyxItemCatalyst"))
			catalyst.readFromNBT(par1.getCompoundTag("nyxItemCatalyst"));
		else
			catalyst = null;
		
		if(par1.hasKey("nyxTransmuteTime"))
			finishOn = par1.getLong("nyxTransmuteTime");
	}

	public boolean canAttemptTransmutation() {
		return catalyst != null && target != null && finishOn == 0;
	}
}
