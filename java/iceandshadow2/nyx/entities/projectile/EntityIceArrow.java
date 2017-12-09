package iceandshadow2.nyx.entities.projectile;

import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSAspect;
import iceandshadow2.render.fx.IaSFxManager;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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

public class EntityIceArrow extends Entity implements IProjectile, IIaSAspect {
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
		freezeLevel = 0;
		freezeTime = 1;
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
	}

	public EntityIceArrow(World par1World, double par2, double par4, double par6, int freezeLevel, int freezeTime) {
		super(par1World);
		this.freezeLevel = freezeLevel;
		this.freezeTime = freezeTime;
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
		setPosition(par2, par4, par6);
		yOffset = 0.0F;
	}

	public EntityIceArrow(World par1World, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving,
			float par4, float par5, int freezeLevel, int freezeTime) {
		super(par1World);
		this.freezeLevel = freezeLevel;
		this.freezeTime = freezeTime;
		renderDistanceWeight = 10.0D;
		shootingEntity = par2EntityLiving;
		posY = par2EntityLiving.posY + par2EntityLiving.getEyeHeight() - 0.10000000149011612D;
		final double var6 = par3EntityLiving.posX - par2EntityLiving.posX;
		final double var8 = par3EntityLiving.posY + par3EntityLiving.getEyeHeight() - 0.699999988079071D - posY;
		final double var10 = par3EntityLiving.posZ - par2EntityLiving.posZ;
		final double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);

		if (var12 >= 1.0E-7D) {
			final float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
			final float var15 = (float) -(Math.atan2(var8, var12) * 180.0D / Math.PI);
			final double var16 = var6 / var12;
			final double var18 = var10 / var12;
			setLocationAndAngles(par2EntityLiving.posX + var16, posY, par2EntityLiving.posZ + var18, var14, var15);
			yOffset = 0.0F;
			final float var20 = (float) var12 * 0.2F;
			setThrowableHeading(var6, var8 + var20, var10, par4, par5);
		}
	}

	public EntityIceArrow(World par1World, EntityLivingBase par2EntityLiving, float par3, int freezeLevel,
			int freezeTime) {
		super(par1World);
		this.freezeLevel = freezeLevel;
		this.freezeTime = freezeTime;
		renderDistanceWeight = 10.0D;
		shootingEntity = par2EntityLiving;

		setSize(0.5F, 0.5F);
		setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(),
				par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
		motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);
		setThrowableHeading(motionX, motionY, motionZ, par3 * 1.5F, 1.0F);
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
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NYX;
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
		return damage;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind
	 * it.
	 */
	public boolean getIsCritical() {
		final byte var1 = dataWatcher.getWatchableObjectByte(16);
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

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			final float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D
					/ Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, var1) * 180.0D / Math.PI);
		}

		final Block blockIn = worldObj.getBlock(xTile, yTile, zTile);

		if (blockIn != null) {
			blockIn.setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
			final AxisAlignedBB blockBB = blockIn.getCollisionBoundingBoxFromPool(worldObj, xTile, yTile,
					zTile);

			if (blockBB != null && blockBB.isVecInside(Vec3.createVectorHelper(posX, posY, posZ))) {

				final Vec3 vel = Vec3.createVectorHelper(motionX, motionY, motionZ);

				worldObj.playSoundAtEntity(this, "dig.glass",
						(float) (vel.lengthVector() / 5.0F > 1.0F ? 1.0F : vel.lengthVector() / 5.0F),
						1.2F / (rand.nextFloat() * 0.2F + 0.9F));
				setDead();
			}
		}

		if (arrowShake > 0)
			arrowShake = 0;

		++ticksInAir;
		Vec3 var17 = Vec3.createVectorHelper(posX, posY, posZ);
		Vec3 var3 = Vec3.createVectorHelper(posX + motionX, posY + motionY,
				posZ + motionZ);
		MovingObjectPosition var4 = worldObj.rayTraceBlocks(var17, var3);
		var17 = Vec3.createVectorHelper(posX, posY, posZ);
		var3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

		if (var4 != null)
			var3 = Vec3.createVectorHelper(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);

		Entity var5 = null;
		@SuppressWarnings("rawtypes")
		final List var6 = worldObj.getEntitiesWithinAABBExcludingEntity(this,
				boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
		double var7 = 0.0D;
		int var9;
		float var11;

		for (var9 = 0; var9 < var6.size(); ++var9) {
			final Entity var10 = (Entity) var6.get(var9);

			if (var10.canBeCollidedWith() && (var10 != shootingEntity || ticksInAir >= 5)) {
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

		if (var5 != null)
			var4 = new MovingObjectPosition(var5);

		float var20;
		float var26;
		final boolean shouldKill = true;

		if (var4 != null)
			if (var4.entityHit != null) {
				var20 = MathHelper.sqrt_double(
						motionX * motionX + motionY * motionY + motionZ * motionZ);
				int var23 = MathHelper.ceiling_double_int(var20 * damage);

				if (getIsCritical())
					var23 += rand.nextInt(var23 / 2 + 1) + var23;
				else
					var23 += 2;

				if (var4.entityHit instanceof EntityBlaze || var4.entityHit instanceof EntityMagmaCube)
					var23 += 3;

				DamageSource dmgSrc = null;

				if (shootingEntity == null)
					dmgSrc = DamageSource.causeThrownDamage(this, this);
				else
					dmgSrc = DamageSource.causeThrownDamage(this, shootingEntity);

				// Slow enemies it hits.
				if (var4.entityHit instanceof EntityLivingBase && freezeTime > 0)
					((EntityLivingBase) var4.entityHit).addPotionEffect(
							new PotionEffect(Potion.moveSlowdown.id, freezeTime, freezeLevel));

				if (var4.entityHit.attackEntityFrom(dmgSrc, var23)) {
					if (var4.entityHit instanceof EntityLiving) {
						if (knockbackStrength > 0) {
							var26 = MathHelper
									.sqrt_double(motionX * motionX + motionZ * motionZ);

							if (var26 > 0.0F)
								var4.entityHit.addVelocity(
										motionX * knockbackStrength * 0.6000000238418579D / var26,
										0.1D * knockbackStrength,
										motionZ * knockbackStrength * 0.6000000238418579D / var26);
						}

						if (shootingEntity != null && var4.entityHit != shootingEntity
								&& var4.entityHit instanceof EntityPlayer
								&& shootingEntity instanceof EntityPlayerMP)
							((EntityPlayerMP) shootingEntity).playerNetServerHandler
									.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
					}
					// Kill the arrow IF it's non-critical and didn't hit an
					// enderman.
					if (shouldKill && !(var4.entityHit instanceof EntityEnderman) && !getIsCritical())
						setDead();
					if (!isEntityAlive())
						worldObj.playSoundAtEntity(this, "dig.glass",
								(float) (var4.hitVec.lengthVector() / 5.0F > 1.0 ? 1.0F
										: var4.hitVec.lengthVector() / 5.0F),
								1.2F / (rand.nextFloat() * 0.2F + 0.9F));
				}
			} else {
				xTile = var4.blockX;
				yTile = var4.blockY;
				zTile = var4.blockZ;
				inTile = worldObj.getBlock(xTile, yTile, zTile);
				inData = worldObj.getBlockMetadata(xTile, yTile, zTile);

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

				if (inTile != null) {
					inTile.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);

					boolean die = inTile.isCollidable();
					if (inTile.getMaterial() == Material.glass) {
						die &= !(getIsCritical()
								|| !(inTile.renderAsNormalBlock() || (inTile instanceof BlockGlass)));
						if (!worldObj.isRemote)
							worldObj.func_147480_a(xTile, yTile, zTile, false);
					} else if (inTile.getMaterial() == Material.ice) {
						die &= !getIsCritical();
						setIsCritical(false);
						if (!worldObj.isRemote)
							worldObj.func_147480_a(xTile, yTile, zTile, true);
					} else if (inTile.getMaterial() == Material.cloth)
						die = false;
					else if (inTile.getMaterial() == Material.leaves)
						die &= !(getIsCritical() || rand.nextInt(4) == 0);
					else if (inTile.getMaterial() == Material.sand) {
						die &= !getIsCritical();
						setIsCritical(false);
					}

					if (die) {
						worldObj.playSoundAtEntity(this, "dig.glass",
								(float) (var4.hitVec.lengthVector() / 5.0F > 1.0 ? 1.0F
										: var4.hitVec.lengthVector() / 5.0F),
								1.2F / (rand.nextFloat() * 0.2F + 0.9F));
						for (int i = 0; i < 8; ++i)
							worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D,
									0.0D);
						setDead();
					}
				}
			}

		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		var20 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

		for (rotationPitch = (float) (Math.atan2(motionY, var20) * 180.0D / Math.PI); rotationPitch
				- prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
			;

		while (rotationPitch - prevRotationPitch >= 180.0F)
			prevRotationPitch += 360.0F;

		while (rotationYaw - prevRotationYaw < -180.0F)
			prevRotationYaw -= 360.0F;

		while (rotationYaw - prevRotationYaw >= 180.0F)
			prevRotationYaw += 360.0F;

		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw);
		float var22 = 0.99F;
		var11 = 0.05F;

		if (

		isInWater()) {
			for (int var25 = 0; var25 < (getIsCritical() ? 6 : 4); ++var25) {
				var26 = 0.25F;
				worldObj.spawnParticle("bubble", posX - motionX * var26,
						posY - motionY * var26, posZ - motionZ * var26, motionX, motionY,
						motionZ);
			}

			var22 = 0.8F;
		} else
			for (var9 = 0; var9 < (getIsCritical() ? 5 : 3); ++var9)
				IaSFxManager.spawnParticle(worldObj, getIsCritical() ? "cloud" : "cloudSmall",
						posX + motionX * var9 / 4.0D, posY + motionY * var9 / 4.0D,
						posZ + motionZ * var9 / 4.0D, 0.0D, 0.05D, 0.0D, true, !getIsCritical());

		motionX *= var22;
		motionY *= var22;
		motionZ *= var22;
		motionY -= var11;
		setPosition(posX, posY, posZ);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		xTile = par1NBTTagCompound.getShort("xTile");
		yTile = par1NBTTagCompound.getShort("yTile");
		zTile = par1NBTTagCompound.getShort("zTile");
		inTile = Block.getBlockById(par1NBTTagCompound.getInteger("inTile"));
		inData = par1NBTTagCompound.getByte("inData") & 255;
		arrowShake = par1NBTTagCompound.getByte("shake") & 255;

		if (par1NBTTagCompound.hasKey("damage"))
			damage = par1NBTTagCompound.getDouble("damage");
	}

	public void setDamage(double par1) {
		damage = par1;
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
		final byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (par1)
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 1)));
		else
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -2)));
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(int par1) {
		knockbackStrength = par1;
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
		par1 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par3 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par5 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		motionX = par1;
		motionY = par3;
		motionZ = par5;
		final float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity(double par1, double par3, double par5) {
		motionX = par1;
		motionY = par3;
		motionZ = par5;

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			final float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch;
			prevRotationYaw = rotationYaw;
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound.setShort("xTile", (short) xTile);
		par1NBTTagCompound.setShort("yTile", (short) yTile);
		par1NBTTagCompound.setShort("zTile", (short) zTile);
		par1NBTTagCompound.setInteger("inTile", Block.getIdFromBlock(inTile));
		par1NBTTagCompound.setByte("inData", (byte) inData);
		par1NBTTagCompound.setByte("shake", (byte) arrowShake);
		par1NBTTagCompound.setDouble("damage", damage);
	}
}
