package iceandshadow2.ias;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class IaSTileEntity extends TileEntity {
	
	public void markUpdate() {
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
		getWorldObj().markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound syncData = new NBTTagCompound();
		writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, syncData);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}
}
