package iceandshadow2.nyx.entities.util;

import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;
import iceandshadow2.nyx.entities.mobs.EntityNyxWightToxic;
import iceandshadow2.util.IaSBlockHelper;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityWightTeleport extends EntityThrowable {

	private EntityLivingBase target;

	public EntityWightTeleport(World par1World) {
		super(par1World);
	}

	public EntityWightTeleport(World par1World, double par2, double par4,
			double par6) {
		super(par1World, par2, par4, par6);
	}

	public EntityWightTeleport(World par1World,
			EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
	}

	public EntityWightTeleport(World par1World,
			EntityLivingBase par2EntityLivingBase, EntityLivingBase target) {
		this(par1World, par2EntityLivingBase);
		this.target = target;
	}

	@Override
	protected float func_70182_d() {
		return 2.0F;
	}

	@Override
	protected float func_70183_g() {
		return 0.0F;
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity() {
		return 0.02F;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition pom) {
		if(pom.typeOfHit == MovingObjectType.BLOCK) {
			this.worldObj
			.playSoundAtEntity(this,
					"IceAndShadow2:mob_nyxwight_tele_arrive",
					0.8F, 
					this.rand.nextFloat() * 0.1F + 0.9F);
			if(!this.worldObj.isRemote) {
				Block bl = this.worldObj.getBlock(pom.blockX, pom.blockY, pom.blockZ);
				if(bl.getMaterial() == Material.leaves)
					return;
				if(bl.isReplaceable(this.worldObj, pom.blockX, pom.blockY, pom.blockZ))
					return;
				EntityMob spawn = new EntityNyxWightToxic(this.worldObj);
				Block bl1 = this.worldObj.getBlock(pom.blockX, pom.blockY+1, pom.blockZ);
				Block bl2 = this.worldObj.getBlock(pom.blockX, pom.blockY+2, pom.blockZ);
				while(!IaSBlockHelper.isAir(bl1) && !IaSBlockHelper.isAir(bl2)) {
					pom.blockY+=1;
					bl1 = this.worldObj.getBlock(pom.blockX, pom.blockY+1, pom.blockZ);
					bl2 = this.worldObj.getBlock(pom.blockX, pom.blockY+2, pom.blockZ);
				}
				spawn.setPositionAndRotation(pom.blockX+0.5, pom.blockY+1, pom.blockZ+0.5,
						(float)(worldObj.rand.nextFloat()*360F),0.0F);
				if(target != null && !target.isDead)
					spawn.setTarget(target);
				this.worldObj.spawnEntityInWorld(spawn);
			}
			this.setDead();
		} else if (pom.typeOfHit == MovingObjectType.ENTITY) {
			this.motionX *= 0.1;
			this.motionY = -0.3;
			this.motionZ *= 0.1;
		}
	}
}