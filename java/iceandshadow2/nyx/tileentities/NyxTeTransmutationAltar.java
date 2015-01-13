package iceandshadow2.nyx.tileentities;

import iceandshadow2.api.IIaSApiTransmutable;
import iceandshadow2.ias.IaSTileEntity;
import iceandshadow2.nyx.entities.util.EntityTransmutationCountdown;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;

public class NyxTeTransmutationAltar extends IaSTileEntity {
	public ItemStack target;
	public ItemStack catalyst;
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
			temp = target;
			target = null;
			return temp;
		}
		if(catalyst != null) {
			temp = catalyst;
			catalyst = null;
			return temp;
		}
		return null;
	}

	public void scheduleUpdate(int x, int y, int z, int time) {
		Entity cd = new EntityTransmutationCountdown(this.worldObj, x, y, z, time);
		this.worldObj.spawnEntityInWorld(cd);
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
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}

	public boolean canAttemptTransmutation() {
		return catalyst != null && target != null;
	}

	public void dropItems() {
		if(this.worldObj.isRemote)
			return;
		EntityItem cat = new EntityItem(this.worldObj, this.xCoord, this.yCoord+0.5F, this.zCoord, this.catalyst);
		EntityItem tar = new EntityItem(this.worldObj, this.xCoord, this.yCoord+0.5F, this.zCoord, this.target);
		this.worldObj.spawnEntityInWorld(cat);
		this.worldObj.spawnEntityInWorld(tar);
	}
}
