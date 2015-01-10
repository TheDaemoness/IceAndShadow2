package iceandshadow2.nyx.tileentities;

import iceandshadow2.ias.IaSTileEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class NyxTeTransmutationAltar extends IaSTileEntity {
	public ItemStack target;
	public ItemStack catalyst;
	public int transmutesLeft;
	
	public boolean handlePlace(ItemStack is) {
		if(catalyst == null) {
			catalyst = is;
			return true;
		}
		else if(target == null) {
			target = is;
			return true;
		}
		return false;
	}
	
	public ItemStack handleRemove() {
		ItemStack temp;
		if(target != null) {
			temp = target;
			target = null;
			return temp;
		}
		if(catalyst != null) {
			temp = catalyst;
			catalyst = null;
			return catalyst;
		}
		return null;
	}
	
	public void startTransmute(World w, int x, int y, int z) {
		if(catalyst == null || target == null)
			return;
	}

	@Override
	public void writeToNBT(NBTTagCompound par1) {
		super.writeToNBT(par1);
		NBTTagCompound eyetemme = par1.getCompoundTag("nyxItemTarget");
		target.writeToNBT(eyetemme);
		par1.setTag("nyxItemTarget", eyetemme);
		
		eyetemme = par1.getCompoundTag("nyxItemCatalyst");
		catalyst.writeToNBT(eyetemme);
		par1.setTag("nyxItemCatalyst", eyetemme);
		
		par1.setInteger("nyxTransmutes", transmutesLeft);
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
		
		if(par1.hasKey("nyxTransmutes"))
			transmutesLeft = par1.getInteger("nyxTransmutes");
	}
}
