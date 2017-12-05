package iceandshadow2.nyx.entities.projectile;

import iceandshadow2.nyx.NyxItems;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.util.IaSEntityHelper;
import iceandshadow2.util.IaSWorldHelper;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityIceArrow extends Entity implements IProjectile {
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private Block inTile = null;
	private int inData = 0;

	/** Seems to be some sort of timer for animating an arrow. */
	public int arrowShake = 0;

	/** The owner of this arrow. */
	public Entity shootingEntity;
	private int ticksInAir = 0;
	private double damage = 2.0D;

	private final int freezeTime;
	private final int freezeLevel;

	/** The amount of knockback an arrow applies when it hits a mob. */
	private int knockbackStrength;

	public EntityIceArrow(World par1World) {
		super(par1World);
		this.freezeLevel = 0;
		this.freezeTime = 1;
		this.renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
	}

	public EntityIceArrow(World par1World, double par2, double par4, double par6, int freezeLevel, int freezeTime) {
		super(par1World);
		this.freezeLevel = freezeLevel;
		this.freezeTime = freezeTime;
		this.renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
		setPosition(par2, par4, par6);
		this.yOffset = 0.0F;
	}

	public EntityIceArrow(World par1World, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving,
			float par4, float par5, int freezeLevel, int freezeTime) {
		super(par1World);
		this.freezeLevel = freezeLevel;
		this.freezeTime = freezeTime;
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLiving;
		this.posY = par2EntityLiving.posY + par2EntityLiving.getEyeHeight() - 0.10000000149011612D;
		final double var6 = par3EntityLiving.posX - par2EntityLiving.posX;
		final double var8 = par3EntityLiving.posY + par3EntityLiving.getEyeHeight() - 0.699999988079071D - this.posY;
		final double var10 = par3EntityLiving.posZ - par2EntityLiving.posZ;
		final double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);

		if (var12 >= 1.0E-7D) {
			final float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
			final float var15 = (float) -(Math.atan2(var8, var12) * 180.0D / Math.PI);
			final double var16 = var6 / var12;
			final double var18 = var10 / var12;
			setLocationAndAngles(par2EntityLiving.posX + var16, this.posY, par2EntityLiving.posZ + var18, var14, var15);
			this.yOffset = 0.0F;
			final float var20 = (float) var12 * 0.2F;
			setThrowableHeading(var6, var8 + var20, var10, par4, par5);
		}
	}

	public EntityIceArrow(World par1World, EntityLivingBase par2EntityLiving, float par3, int freezeLevel,
			int freezeTime) {
		super(par1World);
		this.freezeLevel = freezeLevel;
		this.freezeTime = freezeTime;
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLiving;

		setSize(0.5F, 0.5F);
		setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(),
				par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI);
		setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	@Override
	public boolean canAttackWithItem() {
		return false;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void entityInit() {
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}

	@Override
	public float getBrightness(float par1) {
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1) {
		return 15728880;
	}

	public double getDamage() {
		return this.damage;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind
	 * it.
	 */
	public boolean getIsCritical() {
		final byte var1 = this.dataWatcher.getWatchableObjectByte(16);
		return (var1 & 1) != 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		// Ice arrows tend to melt.
		if (isBurning())
			setDead();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			final float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D
					/ Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, var1) * 180.0D / Math.PI);
		}

		final Block blockIn = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);

		if (blockIn != null) {
			blockIn.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
			final AxisAlignedBB blockBB = blockIn.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile,
					this.zTile);

			if (blockBB != null && blockBB.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {

				final Vec3 vel = Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ);

				this.worldObj.playSoundAtEntity(this, "dig.glass",
						(float) (vel.lengthVector() / 5.0F > 1.0F ? 1.0F : vel.lengthVector() / 5.0F),
						1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
				setDead();
			}
		}

		if (this.arrowShake > 0) {
			this.arrowShake = 0;
		}

		++this.ticksInAir;
		Vec3 var17 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY,
				this.posZ + this.motionZ);
		MovingObjectPosition var4 = this.worldObj.rayTraceBlocks(var17, var3);
		var17 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (var4 != null) {
			var3 = Vec3.createVectorHelper(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
		}

		Entity var5 = null;
		@SuppressWarnings("rawtypes")
		final List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
				this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
		double var7 = 0.0D;
		int var9;
		float var11;

		for (var9 = 0; var9 < var6.size(); ++var9) {
			final Entity var10 = (Entity) var6.get(var9);

			if (var10.canBeCollidedWith() && (var10 != this.shootingEntity || this.ticksInAir >= 5)) {
				var11 = 0.3F;
				final AxisAlignedBB var12 = var10.boundingBox.expand(var11, var11, var11);
				final MovingObjectPosition var13 = var12.calculateIntercept(var17, var3);

				if (var13 != null) {
					final double var14 = var17.distanceTo(var13.hitVec);

					if (var14 < var7 || var7 == 0.0D) {
						var5 = var10;
						var7 = var14;
					}
				}
			}
		}

		if (var5 != null) {
			var4 = new MovingObjectPosition(var5);
		}

		float var20;
		float var26;
		boolean shouldKill = true;

		if (var4 != null) {
			if (var4.entityHit != null) {
				Entity target = var4.entityHit;
				Item equip = null;
				boolean swordInUse = false;
				if (target instanceof EntityLivingBase && ((EntityLivingBase) target).getEquipmentInSlot(0) != null)
					equip = ((EntityLivingBase) target).getEquipmentInSlot(0).getItem();
				if (target instanceof EntityPlayer && IaSEntityHelper.isInFrontOf((target), this))
					swordInUse = ((EntityPlayer) target).isUsingItem();
				if (equip == NyxItems.frostSword && (swordInUse || target instanceof EntityMob)) {
					final int itemDamage = (IaSWorldHelper.getDifficulty(this.worldObj) + (int) this.damage)
							* (this.getIsCritical() ? 2 : 1);
					((EntityLivingBase) target).getEquipmentInSlot(0).damageItem(itemDamage, (EntityLivingBase) target);
					if (!this.getIsCritical()) {
						this.motionX = -this.motionX;
						this.motionY = -this.motionY;
						this.motionZ = -this.motionZ;
						this.worldObj.playSoundAtEntity(this, "random.bow",
								(float) (var4.hitVec.lengthVector() / 5.0F > 1.0 ? 1.0F
										: var4.hitVec.lengthVector() / 5.0F),
								1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
						shouldKill = false;
					} else
						this.setIsCritical(false);
				}
				/* Damage block */ {
					var20 = MathHelper.sqrt_double(
							this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					int var23 = MathHelper.ceiling_double_int(var20 * this.damage);

					if (getIsCritical())
						var23 += this.rand.nextInt(var23 / 2 + 1) + var23;
					else
						var23 += 2;

					if (var4.entityHit instanceof EntityBlaze || var4.entityHit instanceof EntityMagmaCube)
						var23 += 3;

					DamageSource dmgSrc = null;

					if (this.shootingEntity == null) {
						dmgSrc = DamageSource.causeThrownDamage(this, this);
					} else {
						dmgSrc = DamageSource.causeThrownDamage(this, this.shootingEntity);
					}

					// Slow enemies it hits.
					if (var4.entityHit instanceof EntityLivingBase && this.freezeTime > 0)
						((EntityLivingBase) var4.entityHit).addPotionEffect(
								new PotionEffect(Potion.moveSlowdown.id, this.freezeTime, this.freezeLevel));

					if (var4.entityHit.attackEntityFrom(dmgSrc, var23)) {
						if (var4.entityHit instanceof EntityLiving) {
							if (this.knockbackStrength > 0) {
								var26 = MathHelper
										.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

								if (var26 > 0.0F) {
									var4.entityHit.addVelocity(
											this.motionX * this.knockbackStrength * 0.6000000238418579D / var26,
											0.1D * this.knockbackStrength,
											this.motionZ * this.knockbackStrength * 0.6000000238418579D / var26);
								}
							}

							if (this.shootingEntity != null && var4.entityHit != this.shootingEntity
									&& var4.entityHit instanceof EntityPlayer
									&& this.shootingEntity instanceof EntityPlayerMP) {
								((EntityPlayerMP) this.shootingEntity).playerNetServerHandler
										.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
							}
						}
						// Kill the arrow IF it's non-critical and didn't hit an
						// enderman.
						if (shouldKill && !(var4.entityHit instanceof EntityEnderman) && !getIsCritical())
							setDead();
						if (!this.isEntityAlive())
							this.worldObj.playSoundAtEntity(this, "dig.glass",
									(float) (var4.hitVec.lengthVector() / 5.0F > 1.0 ? 1.0F
											: var4.hitVec.lengthVector() / 5.0F),
									1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					}
				}
			} else {
				this.xTile = var4.blockX;
				this.yTile = var4.blockY;
				this.zTile = var4.blockZ;
				this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
				this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

				// Bounce code from normal arrows.
				/*
				 * this.motionX = (double) ((float) (var4.hitVec.xCoord -
				 * this.posX)); this.motionY = (double) ((float)
				 * (var4.hitVec.yCoord - this.posY)); this.motionZ = (double)
				 * ((float) (var4.hitVec.zCoord - this.posZ)); var20 =
				 * MathHelper.sqrt_double(this.motionX * this.motionX +
				 * this.motionY * this.motionY + this.motionZ this.motionZ);
				 * this.posX -= this.motionX / (double) var20
				 * 0.05000000074505806D; this.posY -= this.motionY / (double)
				 * var20 0.05000000074505806D; this.posZ -= this.motionZ /
				 * (double) var20 0.05000000074505806D;
				 */

				if (this.inTile != null) {
					this.inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);

					boolean die = this.inTile.isCollidable();
					if (this.inTile.getMaterial() == Material.glass) {
						die &= !(getIsCritical()
								|| !(this.inTile.renderAsNormalBlock() || (this.inTile instanceof BlockGlass)));
						if (!this.worldObj.isRemote)
							this.worldObj.func_147480_a(this.xTile, this.yTile, this.zTile, false);
					} else if (this.inTile.getMaterial() == Material.ice) {
						die &= !getIsCritical();
						setIsCritical(false);
						if (!this.worldObj.isRemote) {
							this.worldObj.func_147480_a(this.xTile, this.yTile, this.zTile, true);
						}
					} else if (this.inTile.getMaterial() == Material.cloth)
						die = false;
					else if (this.inTile.getMaterial() == Material.leaves)
						die &= !(getIsCritical() || this.rand.nextInt(4) == 0);
					else if (this.inTile.getMaterial() == Material.sand) {
						die &= !getIsCritical();
						setIsCritical(false);
					}

					if (die) {
						this.worldObj.playSoundAtEntity(this, "dig.glass",
								(float) (var4.hitVec.lengthVector() / 5.0F > 1.0 ? 1.0F
										: var4.hitVec.lengthVector() / 5.0F),
								1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
						for (int i = 0; i < 8; ++i)
							this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D,
									0.0D);
						setDead();
					}
				}
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for (this.rotationPitch = (float) (Math.atan2(this.motionY, var20) * 180.0D / Math.PI); this.rotationPitch
				- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)

		{
			;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float var22 = 0.99F;
		var11 = 0.05F;

		if (

		isInWater()) {
			for (int var25 = 0; var25 < (getIsCritical() ? 6 : 4); ++var25) {
				var26 = 0.25F;
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var26,
						this.posY - this.motionY * var26, this.posZ - this.motionZ * var26, this.motionX, this.motionY,
						this.motionZ);
			}

			var22 = 0.8F;
		} else {
			for (var9 = 0; var9 < (getIsCritical() ? 5 : 3); ++var9) {
				IaSFxManager.spawnParticle(this.worldObj, getIsCritical() ? "cloud" : "cloudSmall",
						this.posX + this.motionX * var9 / 4.0D, this.posY + this.motionY * var9 / 4.0D,
						this.posZ + this.motionZ * var9 / 4.0D, 0.0D, 0.05D, 0.0D, true, !getIsCritical());
			}
		}

		this.motionX *= var22;
		this.motionY *= var22;
		this.motionZ *= var22;
		this.motionY -= var11;
		setPosition(this.posX, this.posY, this.posZ);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		this.xTile = par1NBTTagCompound.getShort("xTile");
		this.yTile = par1NBTTagCompound.getShort("yTile");
		this.zTile = par1NBTTagCompound.getShort("zTile");
		this.inTile = Block.getBlockById(par1NBTTagCompound.getInteger("inTile"));
		this.inData = par1NBTTagCompound.getByte("inData") & 255;
		this.arrowShake = par1NBTTagCompound.getByte("shake") & 255;

		if (par1NBTTagCompound.hasKey("damage")) {
			this.damage = par1NBTTagCompound.getDouble("damage");
		}
	}

	public void setDamage(double par1) {
		this.damage = par1;
	}

	@Override
	public void setFire(int par1) {
		setDead();
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind
	 * it.
	 */
	public void setIsCritical(boolean par1) {
		final byte var2 = this.dataWatcher.getWatchableObjectByte(16);

		if (par1) {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 1)));
		} else {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -2)));
		}
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(int par1) {
		this.knockbackStrength = par1;
	}

	@Override
	protected void setOnFireFromLava() {
		setDead();
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Sets the position and rotation. Only difference from the other one is no
	 * bounding on the rotation. Args: posX, posY, posZ, yaw, pitch
	 */
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
		setPosition(par1, par3, par5);
		setRotation(par7, par8);
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	@Override
	public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8) {
		final float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
		par3 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
		par5 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		final float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity(double par1, double par3, double par5) {
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			final float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound.setShort("xTile", (short) this.xTile);
		par1NBTTagCompound.setShort("yTile", (short) this.yTile);
		par1NBTTagCompound.setShort("zTile", (short) this.zTile);
		par1NBTTagCompound.setInteger("inTile", Block.getIdFromBlock(this.inTile));
		par1NBTTagCompound.setByte("inData", (byte) this.inData);
		par1NBTTagCompound.setByte("shake", (byte) this.arrowShake);
		par1NBTTagCompound.setDouble("damage", this.damage);
	}
}
