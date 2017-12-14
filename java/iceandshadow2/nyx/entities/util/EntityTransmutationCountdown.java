package iceandshadow2.nyx.entities.util;

import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.nyx.blocks.utility.NyxBlockAltarTransmutation;
import iceandshadow2.nyx.tileentities.NyxTeTransmutationAltar;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class EntityTransmutationCountdown extends Entity {

	private int age = 0;

	public EntityTransmutationCountdown(World w) {
		super(w);
		setSize(0.0F, 0.0F);
	}

	public EntityTransmutationCountdown(World w, int x, int y, int z, int time) {
		this(w);
		setPosition(x + 0.5, y + 1.25, z + 0.5);
		dataWatcher.updateObject(16, time);
	}

	@Override
	public boolean canAttackWithItem() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(16, 0);
	}

	public int getAge() {
		return age;
	}

	public int getTransmutationTime() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		final int x = (int) (posX - 0.5);
		final int y = (int) (posY - 0.5);
		final int z = (int) (posZ - 0.5);
		final TileEntity te = worldObj.getTileEntity(x, y, z);
		if (!(te instanceof NyxTeTransmutationAltar)) {
			setDead();
			return;
		}
		final NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar) te;
		if (worldObj.getBlock(x, y + 1, z).getMaterial() != Material.air) {
			setDead();
			return;
		}
		if (!tte.canAttemptTransmutation()) {
			setDead();
			return;
		}
		++age;
		if (age >= dataWatcher.getWatchableObjectInt(16) && !worldObj.isRemote)
			if (worldObj.getBlock(x, y, z) instanceof NyxBlockAltarTransmutation) {
				final NyxBlockAltarTransmutation bl = (NyxBlockAltarTransmutation) worldObj.getBlock(x, y, z);
				bl.doTransmutation(worldObj, x, y, z, worldObj.rand);
				worldObj.markBlockForUpdate(x, y, z);
				worldObj.markTileEntityChunkModified(x, y, z, tte);
				setDead();
				return;
			}
		final double xposMod = 0.4 + worldObj.rand.nextDouble() / 5, zposMod = 0.4 + worldObj.rand.nextDouble() / 5;
		if (age % 3 == 0)
			return;
		final IIaSApiTransmute particleHandler = (tte.catalyst != null
				&& tte.catalyst.getItem() instanceof IIaSApiTransmute) ? (IIaSApiTransmute) tte.catalyst.getItem()
						: tte.handler;
		if (worldObj.isRemote && particleHandler != null && tte.handler != null)
			if (!particleHandler.spawnTransmuteParticles(tte.target, tte.catalyst, worldObj, this))
				IaSFxManager.spawnItemParticle(worldObj, tte.catalyst, posX - 0.5 + xposMod,
						posY + worldObj.rand.nextDouble() / 2, posZ - 0.5 + zposMod, (0.5 - xposMod) / 10,
						-0.05 - worldObj.rand.nextDouble() * 0.1, (0.5 - zposMod) / 10, false, false);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound dat) {
		dat.setInteger("nyxTimeLived", age);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound dat) {
		if (dat.hasKey("nyxTimeLived"))
			age = dat.getInteger("nyxTimeLived");
	}

}
