package iceandshadow2.nyx.entities.util;

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
		this.setSize(0.0F, 0.0F);
	}

	public EntityTransmutationCountdown(World w, int x, int y, int z, int time) {
		this(w);
		this.setPosition(x + 0.5, y + 1.25, z + 0.5);
		this.dataWatcher.updateObject(16, time);
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
		this.dataWatcher.addObject(16, 0);
	}

	public int getAge() {
		return age;
	}

	public int getTransmutationTime() {
		return this.dataWatcher.getWatchableObjectInt(16);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		final int x = (int) (this.posX - 0.5);
		final int y = (int) (this.posY - 0.5);
		final int z = (int) (this.posZ - 0.5);
		final TileEntity te = this.worldObj.getTileEntity(x, y, z);
		if (!(te instanceof NyxTeTransmutationAltar)) {
			this.setDead();
			return;
		}
		final NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar) te;
		if (this.worldObj.getBlock(x, y + 1, z).getMaterial() != Material.air) {
			this.setDead();
			return;
		}
		if (!tte.canAttemptTransmutation()) {
			this.setDead();
			return;
		}
		++age;
		if (age >= this.dataWatcher.getWatchableObjectInt(16)
				&& !this.worldObj.isRemote) {
			if (this.worldObj.getBlock(x, y, z) instanceof NyxBlockAltarTransmutation) {
				final NyxBlockAltarTransmutation bl = (NyxBlockAltarTransmutation) this.worldObj
						.getBlock(x, y, z);
				bl.doTransmutation(this.worldObj, x, y, z, this.worldObj.rand);
				this.worldObj.markBlockForUpdate(x, y, z);
				this.worldObj.markTileEntityChunkModified(x, y, z, tte);
				this.setDead();
				return;
			}
		}
		final double xposMod = 0.4 + this.worldObj.rand.nextDouble() / 5, zposMod = 0.4 + this.worldObj.rand
				.nextDouble() / 5;
		if (age % 3 == 0)
			return;
		if (this.worldObj.isRemote && tte.handler != null) {
			if (!tte.handler.spawnParticles(tte.target, tte.catalyst,
					this.worldObj, this))
				IaSFxManager.spawnItemParticle(this.worldObj, tte.catalyst,
						this.posX - 0.5 + xposMod, this.posY
						+ this.worldObj.rand.nextDouble() / 2,
						this.posZ - 0.5 + zposMod, (0.5 - xposMod) / 10, -0.05
						- this.worldObj.rand.nextDouble() * 0.1,
						(0.5 - zposMod) / 10, false, false);
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound dat) {
		dat.setInteger("nyxTimeLived", this.age);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound dat) {
		if (dat.hasKey("nyxTimeLived"))
			age = dat.getInteger("nyxTimeLived");
	}

}
