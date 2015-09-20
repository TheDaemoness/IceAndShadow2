package iceandshadow2.nyx.entities.projectile;

import iceandshadow2.render.fx.IaSFxManager;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityShadowBall extends EntityThrowable {
	
	public boolean isStrong() {
		return (this.getDataWatcher().getWatchableObjectByte(16) & 0x1) != 0;
	}
	public boolean isUndeadHarming() {
		return (this.getDataWatcher().getWatchableObjectByte(16) & 0x2) != 0;
	}
	public EntityShadowBall setFlags(boolean strong, boolean harmUndead) {
		this.getDataWatcher().updateObject(16, (byte)((strong?0x1:0x0) | (harmUndead?0x2:0x0)));
		return this;
	}
	private void initFlags(boolean strong, boolean harmUndead) {
		this.getDataWatcher().addObject(16, (byte)((strong?0x1:0x0) | (harmUndead?0x2:0x0)));
	}

	public EntityShadowBall(World par1World) {
		super(par1World);
		this.getDataWatcher().addObject(16, (byte)(0));
	}

	@SideOnly(Side.CLIENT)
	public EntityShadowBall(World par1World, double par2, double par4,
			double par6) {
		this(par1World, par2, par4, par6, true, false);
	}

	public EntityShadowBall(World par1World, double par2, double par4,
			double par6, boolean hundead, boolean power) {
		super(par1World, par2, par4, par6);
		initFlags(power, hundead);
	}

	public EntityShadowBall(World par1World,
			EntityLivingBase par2EntityLivingBase) {
		this(par1World, par2EntityLivingBase, true, false);
	}

	public EntityShadowBall(World par1World,
			EntityLivingBase par2EntityLivingBase, boolean hundead,
			boolean power) {
		super(par1World, par2EntityLivingBase);
		initFlags(power, hundead);
	}

	@Override
	protected float func_70182_d() {
		return 0.5F;
	}

	@Override
	protected float func_70183_g() {
		return -20.0F;
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {

		if (par1MovingObjectPosition.typeOfHit == MovingObjectType.ENTITY) {
			/*
			 if(this.worldObj.isRemote) return;
			 if(par1MovingObjectPosition.entityHit instanceof
			 EntityNyxSkeletonNecro && this.getThrower() instanceof
			 EntityNyxSkeletonNecro) {
			 par1MovingObjectPosition.entityHit.attackEntityFrom(
			 DamageSource.causeIndirectMagicDamage(
			 par1MovingObjectPosition.entityHit,
			 (this.getThrower()==null?this:this.getThrower())), basepower +
			 basepower*this.rand.nextFloat()); this.setDead(); }
			 */
			return;
		}
		
		boolean strong = isStrong();

		if (!this.worldObj.isRemote) {
			final float basepower = strong ? 6.0F : 4.0F;

			final AxisAlignedBB axisalignedbb = this.boundingBox.expand(4.0D,
					2.0D, 4.0D);
			final List list1 = this.worldObj.getEntitiesWithinAABB(
					EntityLivingBase.class, axisalignedbb);

			if (list1 != null && !list1.isEmpty()) {
				final Iterator iterator = list1.iterator();

				while (iterator.hasNext()) {
					EntityLivingBase elmo = (EntityLivingBase) iterator
							.next();
					float d0 = (float) this.getDistanceSqToEntity(elmo);

					if (d0 < 16.0D) {
						float d1 = 1.0F - d0 * d0 / 512.0F;

						float power = basepower * d1 + basepower;

						elmo.addPotionEffect(new PotionEffect(
								Potion.blindness.id, 39, 0));

						if (!isUndeadHarming()
								&& elmo.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
							elmo.heal(power);
						else if (elmo.getEntityId() == this.getThrower()
								.getEntityId()) {
							if (elmo.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
								elmo.heal(power);
							else
								elmo.attackEntityFrom(DamageSource.magic,
										power / 2);
						} else
							elmo.attackEntityFrom(DamageSource
									.causeIndirectMagicDamage(elmo,
											this.getThrower() == null ? this
													: this.getThrower()), 
													power*(elmo.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD?3.0F:1.0F));
						elmo.addPotionEffect(new PotionEffect(
								Potion.blindness.id, 69, 1));
						this.getThrower().heal(power/Math.min(1+list1.size(), 4));
					}
				}
			}
		}

		final String id = strong ? "shadowSmokeLarge" : "shadowSmokeSmall";
		for (int i = 0; i < 48; ++i) {
			IaSFxManager.spawnParticle(this.worldObj, "blackMagic", this.posX
					- 3.5F + 7.0F * this.rand.nextDouble(), this.posY - 1.5F
					+ 3.0F * this.rand.nextDouble(), this.posZ - 3.5F + 7.0F
					* this.rand.nextDouble(), 0.0, -0.01, 0.0, false, true);
			IaSFxManager.spawnParticle(this.worldObj, id, this.posX - 3.5F
					+ 7.0F * this.rand.nextDouble(), this.posY - 1.5F + 3.0F
					* this.rand.nextDouble(), this.posZ - 3.5F + 7.0F
					* this.rand.nextDouble(), 0.0, -0.01, 0.0, false, false);
		}

		this.setDead();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		String id = isStrong() ? "shadowSmokeLarge" : "shadowSmokeSmall";
		IaSFxManager.spawnParticle(this.worldObj, id, this.posX, this.posY,
				this.posZ, true);
		IaSFxManager.spawnParticle(this.worldObj, id,
						this.posX+this.motionX, this.posY+this.motionY,
						this.posZ+this.motionZ, true);
	}
}
