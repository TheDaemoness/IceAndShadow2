package iceandshadow2.nyx.tileentities;

import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IIaSApiTransmuteLens;
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
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

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

	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound syncData = new NBTTagCompound();
		writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, syncData);
	}

	public boolean handlePlace(EntityPlayer ep, ItemStack is) {
		if (!(is.getItem() instanceof IIaSApiTransmuteLens))
			if (catalyst == null) {
				catalyst = is;
				return true;
			}
		if (target == null) {
			if (!canPlace(is)) {
				IaSPlayerHelper.messagePlayer(ep, "Something about doing that seems unsafe.");
				return false;
			}
			target = is;
			return true;
		}
		return false;
	}

	public ItemStack handleRemove(boolean isSneaking) {
		final boolean lensFlag;
		if (target != null)
			lensFlag = target.getItem() instanceof IIaSApiTransmuteLens;
		else
			lensFlag = false;
		ItemStack temp;
		if (target != null && (!lensFlag || isSneaking)) {
			temp = target;
			target = null;
			return temp;
		}
		if (catalyst != null) {
			temp = catalyst;
			catalyst = null;
			return temp;
		}
		return null;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound par1) {
		super.readFromNBT(par1);
		target = new ItemStack(Items.egg);
		catalyst = new ItemStack(Items.feather);

		if (par1.hasKey("nyxItemTarget"))
			target.readFromNBT(par1.getCompoundTag("nyxItemTarget"));
		else
			target = null;

		if (par1.hasKey("nyxItemCatalyst"))
			catalyst.readFromNBT(par1.getCompoundTag("nyxItemCatalyst"));
		else
			catalyst = null;

		if (canAttemptTransmutation())
			handler = IaSRegistry.getHandlerTransmutation(target, catalyst);
	}

	public void scheduleUpdate(int x, int y, int z, int time) {
		final Entity cd = new EntityTransmutationCountdown(worldObj, x, y, z, time);
		worldObj.spawnEntityInWorld(cd);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1) {
		super.writeToNBT(par1);

		NBTTagCompound eyetemme;
		if (target != null) {
			eyetemme = par1.getCompoundTag("nyxItemTarget");
			target.writeToNBT(eyetemme);
			par1.setTag("nyxItemTarget", eyetemme);
		}
		if (catalyst != null) {
			eyetemme = par1.getCompoundTag("nyxItemCatalyst");
			catalyst.writeToNBT(eyetemme);
			par1.setTag("nyxItemCatalyst", eyetemme);
		}
	}
}
