package iceandshadow2.nyx.entities.util;

import iceandshadow2.nyx.NyxBlocks;
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
		this.setPosition(x+0.5, y+1.25, z+0.5);
		this.dataWatcher.updateObject(16, time);
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
	protected boolean canTriggerWalking() {
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
	public boolean canAttackWithItem() {
		return false;
	}

	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		int x = (int)(this.posX - 0.5) - (this.posX < 0 ? 1:0);
		int y = (int)(this.posY - 0.5);
		int z = (int)(this.posZ - 0.5) - (this.posZ < 0 ? 1:0);
		TileEntity te = this.worldObj.getTileEntity(x, y, z);
		if(!(te instanceof NyxTeTransmutationAltar)) {
			this.setDead();
			return;
		}
		NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar)te;
		if(this.worldObj.getBlock(x, y+1, z).getMaterial() != Material.air) {
			this.setDead();
			return;
		}
		if(!tte.canAttemptTransmutation()) {
			this.setDead();
			return;
		}
		++age;
		if(age >= this.dataWatcher.getWatchableObjectInt(16)) {
			if(this.worldObj.getBlock(x, y, z) instanceof NyxBlockAltarTransmutation) {
				NyxBlockAltarTransmutation bl = (NyxBlockAltarTransmutation)this.worldObj.getBlock(x, y, z);
				bl.doTransmutation(this.worldObj, x, y, z, this.worldObj.rand);
				this.setDead();
				return;
			}
		}
		double xposMod = 0.4+this.worldObj.rand.nextDouble()/5, zposMod = 0.4+this.worldObj.rand.nextDouble()/5;
		if((age&1) == 0)
			return;
		if(this.worldObj.isRemote && tte.handler != null) {
			if(!tte.handler.spawnParticles(tte.target, tte.catalyst, this.worldObj, this))
				IaSFxManager.spawnItemParticle(this.worldObj, tte.catalyst, 
						this.posX-0.5+xposMod, 
						this.posY+this.worldObj.rand.nextDouble()/2,
						this.posZ-0.5+zposMod, 
						(0.5-xposMod)/10, 
						-0.05-this.worldObj.rand.nextDouble()*0.1, 
						(0.5-zposMod)/10, 
						false, false);
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound dat) {
		dat.setInteger("nyxTimeLived", this.age);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound dat) {
		if(dat.hasKey("nyxTimeLived"))
			age = dat.getInteger("nyxTimeLived");
	}

}
