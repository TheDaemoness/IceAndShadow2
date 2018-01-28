package iceandshadow2.nyx.tileentities;

import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.IaSTileEntity;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.entities.util.EntityTransmutationCountdown;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NyxTeTransmutationAltar extends IaSTileEntity {
	public ItemStack target;
	public ItemStack catalyst;
	public IIaSApiTransmute handler;

	public boolean canAttemptTransmutation() {
		return catalyst != null && target != null;
	}

	public boolean canPlace(ItemStack is) {
		final EnumIaSAspect a = EnumIaSAspect.getAspect(is);
		return a != EnumIaSAspect.STYX;
	}

	public void dropItems() {
		if (!worldObj.isRemote) {
			if (catalyst != null) {
				final EntityItem cat = new EntityItem(worldObj, xCoord + 0.5F, yCoord + 0.80F, zCoord + 0.5F, catalyst);
				worldObj.spawnEntityInWorld(cat);
			}
			if (target != null) {
				final EntityItem tar = new EntityItem(worldObj, xCoord + 0.5F, yCoord + 0.80F, zCoord + 0.5F, target);
				worldObj.spawnEntityInWorld(tar);
			}
		}
		target = null;
		catalyst = null;
	}

	public boolean handlePlace(EntityPlayer ep, ItemStack is) {
		if (!(IaSRegistry.isPrimarilyTransfusionTarget(is)) || target != null)
			if (catalyst == null) {
				catalyst = is;
				markUpdate();
				return true;
			}
		if (target == null) {
			if (!canPlace(is)) {
				if(ep != null) {
					IaSPlayerHelper.messagePlayer(ep, "badIdea");
				}
				return false;
			}
			target = is.copy();
			markUpdate();
			return true;
		}
		return false;
	}

	public ItemStack handleRemove(EntityPlayer ep, boolean isSneaking) {
		if ((target != null ^ catalyst != null) && isSneaking) {
			final boolean which = target != null;
			if(handlePlace(ep, (which?target:catalyst))) {
				if(which) {
					target = null;
				} else {
					catalyst = null;
				}
			}
			return null;
		}
		ItemStack temp = null;
		if (target != null && !isSneaking) {
			temp = target;
			target = null;
			markUpdate();
		} else if (catalyst != null) {
			temp = catalyst;
			catalyst = null;
			markUpdate();
		}
		return temp;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1) {
		super.readFromNBT(par1);
		target = new ItemStack(Items.egg);
		catalyst = new ItemStack(Items.feather);

		if (par1.hasKey("nyxItemTarget")) {
			target.readFromNBT(par1.getCompoundTag("nyxItemTarget"));
		} else {
			target = null;
		}

		if (par1.hasKey("nyxItemCatalyst")) {
			catalyst.readFromNBT(par1.getCompoundTag("nyxItemCatalyst"));
		} else {
			catalyst = null;
		}

		if (canAttemptTransmutation()) {
			handler = IaSRegistry.getHandlerTransmutation(target, catalyst);
		}
	}

	public void scheduleUpdate(int x, int y, int z, int time) {
		final Entity cd = new EntityTransmutationCountdown(worldObj, x, y, z, time);
		worldObj.spawnEntityInWorld(cd);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1) {
		super.writeToNBT(par1);

		if (target != null) {
			par1.setTag("nyxItemTarget", target.writeToNBT(par1.getCompoundTag("nyxItemTarget")));
		}
		if (catalyst != null) {
			par1.setTag("nyxItemCatalyst", catalyst.writeToNBT(par1.getCompoundTag("nyxItemCatalyst")));
		}
	}
}
