package iceandshadow2.nyx.entities.projectile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.items.tools.IaSTools;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityThrowingKnife extends IaSEntityKnifeBase {

	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private Block inTile;
	private int inData = 0;

	/** Seems to be some sort of timer for animating an arrow. */
	public int arrowShake = 0;

	/** The owner of this arrow. */
	private int ticksInAir = 0;
	private EntityLivingBase shootingEntity;

	private ItemStack origin;

	public EntityThrowingKnife(World w) {
		super(w);
		this.origin = new ItemStack(IaSTools.knife);
		this.renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
		this.yOffset = 0.0F;
	}

	public EntityThrowingKnife(World w, double x, double y, double z,
			ItemStack is) {
		this(w, is);
		setPosition(x, y, z);
	}

	public EntityThrowingKnife(World w, EntityLivingBase shooter,
			EntityLivingBase target, float vel, float acc, ItemStack is) {
		this(w, is);
		this.shootingEntity = shooter;
		this.posY = shooter.posY + shooter.getEyeHeight()
				- 0.10000000149011612D;
		final double var6 = target.posX - shooter.posX;
		final double var8 = target.posY + target.getEyeHeight()
				- 0.699999988079071D - this.posY;
		final double var10 = target.posZ - shooter.posZ;
		final double var12 = MathHelper
				.sqrt_double(var6 * var6 + var10 * var10);

		if (var12 >= 1.0E-7D) {
			final float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
			final float var15 = (float) -(Math.atan2(var8, var12) * 180.0D / Math.PI);
			final double var16 = var6 / var12;
			final double var18 = var10 / var12;
			setLocationAndAngles(shooter.posX + var16, this.posY,
					shooter.posZ + var18, var14, var15);
			final float var20 = (float) var12 * 0.2F;
			setThrowableHeading(var6, var8 + var20, var10, vel, acc);
		}
	}

	public EntityThrowingKnife(World w, EntityLivingBase shooter, float vel,
			ItemStack is) {
		this(w, is);
		this.shootingEntity = shooter;
		setLocationAndAngles(shooter.posX,
				shooter.posY + shooter.getEyeHeight(), shooter.posZ,
				shooter.rotationYaw, shooter.rotationPitch);
		this.posX -= MathHelper
				.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper
				.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(this.posX, this.posY, this.posZ);
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F
				* (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F
				* (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F
				* (float) Math.PI);
		setThrowableHeading(this.motionX, this.motionY, this.motionZ,
				vel * 1.5F, 1.0F);
	}

	public EntityThrowingKnife(World w, ItemStack is) {
		this(w);
		setSource(is);
	}

	@Override
	public boolean canAttackWithItem() {
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void doDrop(IaSToolMaterial mat) {
		if (!this.worldObj.isRemote) {
			final EntityItem drop = new EntityItem(this.worldObj, this.posX,
					this.posY, this.posZ, mat.getKnifeDrop(this.shootingEntity,
							this));
			this.worldObj.spawnEntityInWorld(drop);
		}
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, IaSRegistry.getDefaultMaterial()
				.getMaterialName());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender(float par1) {
		return 15728880;
	}

	@Override
	public ItemStack getItemStack() {
		final ItemStack nis = this.origin.copy();
		nis.stackSize = 1;
		return nis;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getShadowSize() {
		return 0.5F;
	}

	public ResourceLocation getTexture() {
		return IaSRegistry.getToolMaterial(
				this.dataWatcher.getWatchableObjectString(16)).getKnifeTexture(
						this);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			final float var1 = MathHelper.sqrt_double(this.motionX
					* this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(
					this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(
					this.motionY, var1) * 180.0D / Math.PI);
		}

		final Block var16 = this.worldObj.getBlock(this.xTile, this.yTile,
				this.zTile);

		if (var16 != null) {
			var16.setBlockBoundsBasedOnState(this.worldObj, this.xTile,
					this.yTile, this.zTile);
			final AxisAlignedBB var2 = var16.getCollisionBoundingBoxFromPool(
					this.worldObj, this.xTile, this.yTile, this.zTile);

			if (var2 != null
					&& var2.isVecInside(Vec3.createVectorHelper(this.posX,
							this.posY, this.posZ))) {

				final Vec3 vel = Vec3.createVectorHelper(this.motionX,
						this.motionY, this.motionZ);

				final IaSToolMaterial mat = IaSRegistry
						.getToolMaterial(this.dataWatcher
								.getWatchableObjectString(16));
				this.worldObj.playSoundAtEntity(
						this,
						mat.getKnifeMissSound(),
						(float) (vel.lengthVector() / 5.0F > 1.0F ? 1.0F : vel
								.lengthVector() / 5.0F), 1.6F / (this.rand
										.nextFloat() * 0.2F + 0.4F));
				if (mat.onKnifeHit(
						this.shootingEntity,
						this,
						new ChunkCoordinates(this.xTile, this.yTile, this.zTile)))
					doDrop(mat);
				setDead();
			}
		}

		if (this.arrowShake > 0) {
			this.arrowShake = 0;
		}

		++this.ticksInAir;
		Vec3 var17 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY
				+ this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition var4 = this.worldObj.rayTraceBlocks(var17, var3);
		var17 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY
				+ this.motionY, this.posZ + this.motionZ);

		if (var4 != null) {
			var3 = Vec3.createVectorHelper(var4.hitVec.xCoord,
					var4.hitVec.yCoord, var4.hitVec.zCoord);
		}

		Entity var5 = null;
		@SuppressWarnings("rawtypes")
		final List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(
				this,
				this.boundingBox.addCoord(this.motionX, this.motionY,
						this.motionZ).expand(1.0D, 1.0D, 1.0D));
		double var7 = 0.0D;
		int var9;
		float var11;

		for (var9 = 0; var9 < var6.size(); ++var9) {
			final Entity var10 = (Entity) var6.get(var9);

			if (var10.canBeCollidedWith()
					&& (var10 != this.shootingEntity || this.ticksInAir >= 5)) {
				var11 = 0.3F;
				final AxisAlignedBB var12 = var10.boundingBox.expand(var11,
						var11, var11);
				final MovingObjectPosition var13 = var12.calculateIntercept(
						var17, var3);

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

		if (var4 != null) {
			
			final IaSToolMaterial mat = IaSRegistry
					.getToolMaterial(this.dataWatcher
							.getWatchableObjectString(16));
			
			if (var4.entityHit != null && var4.entityHit != this.shootingEntity) {
				this.worldObj.playSoundAtEntity(this,
						"game.hostile.hurt.fall.small", (float) (var4.hitVec
								.lengthVector() / 5.0F > 1.0 ? 1.0F
										: var4.hitVec.lengthVector() / 5.0F),
										1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

				var20 = MathHelper.sqrt_double(this.motionX * this.motionX
						+ this.motionY * this.motionY + this.motionZ
						* this.motionZ);

				float basedmg = mat.getKnifeDamage(this, this.shootingEntity,
						var4.entityHit);
				if (this.origin != null) {
					int enchant = EnchantmentHelper.getEnchantmentLevel(
							Enchantment.sharpness.effectId, this.origin);
					basedmg += 1.25 * enchant;
					if (var4.entityHit instanceof EntityLivingBase) {
						final EntityLivingBase elb = (EntityLivingBase) var4.entityHit;
						final EnumCreatureAttribute ca = elb
								.getCreatureAttribute();
						if (ca == EnumCreatureAttribute.ARTHROPOD) {
							enchant = EnchantmentHelper.getEnchantmentLevel(
									Enchantment.baneOfArthropods.effectId,
									this.origin);
							basedmg += 2.5 * enchant;
						} else if (ca == EnumCreatureAttribute.UNDEAD) {
							enchant = EnchantmentHelper.getEnchantmentLevel(
									Enchantment.smite.effectId, this.origin);
							basedmg += 2.5 * enchant;
						}
					}
				}

				final int var23 = MathHelper
						.ceiling_double_int(var20 * basedmg);

				final DamageSource var21 = mat.getKnifeDamageSource(this,
						this.shootingEntity);
				final boolean drop = mat.onKnifeHit(this.shootingEntity, this,
						var4.entityHit);

				if (var4.entityHit.attackEntityFrom(var21, var23)) {
					if (this.origin != null) {
						final int enchant = EnchantmentHelper
								.getEnchantmentLevel(
										Enchantment.fireAspect.effectId,
										this.origin);
						if (enchant >= 1)
							var4.entityHit.setFire(enchant * 4 - 1);
					}
					if (var4.entityHit instanceof EntityLiving) {
						int knockbackStrength = 0;
						if (this.origin != null)
							knockbackStrength += EnchantmentHelper
							.getEnchantmentLevel(
									Enchantment.knockback.effectId,
									this.origin);

						var26 = MathHelper.sqrt_double(this.motionX
								* this.motionX + this.motionZ * this.motionZ);

						if (var26 > 0.0F) {
							var4.entityHit.addVelocity(this.motionX
									* knockbackStrength * 0.6000000238418579D
									/ var26, 0.1D * knockbackStrength,
									this.motionZ * knockbackStrength
									* 0.6000000238418579D / var26);
						}

						if (this.shootingEntity != null
								&& var4.entityHit != this.shootingEntity
								&& var4.entityHit instanceof EntityPlayer
								&& this.shootingEntity instanceof EntityPlayerMP) {
							((EntityPlayerMP) this.shootingEntity).playerNetServerHandler
							.sendPacket(new S2BPacketChangeGameState(6,
									0.0F));
						}
					}

					if (!(var4.entityHit instanceof EntityEnderman)) {
						if (drop)
							doDrop(mat);
						setDead();
					}

				}
			} else {
				this.xTile = var4.blockX;
				this.yTile = var4.blockY;
				this.zTile = var4.blockZ;
				this.inTile = this.worldObj.getBlock(this.xTile, this.yTile,
						this.zTile);
				this.inData = this.worldObj.getBlockMetadata(this.xTile,
						this.yTile, this.zTile);

				this.motionX = (float) (var4.hitVec.xCoord - this.posX);
				this.motionY = (float) (var4.hitVec.yCoord - this.posY);
				this.motionZ = (float) (var4.hitVec.zCoord - this.posZ);
				var20 = MathHelper.sqrt_double(this.motionX * this.motionX
						+ this.motionY * this.motionY + this.motionZ
						* this.motionZ);
				this.posX -= this.motionX / var20 * 0.05000000074505806D;
				this.posY -= this.motionY / var20 * 0.05000000074505806D;
				this.posZ -= this.motionZ / var20 * 0.05000000074505806D;

				if (this.inTile != null) {
					this.inTile.onEntityCollidedWithBlock(this.worldObj,
							this.xTile, this.yTile, this.zTile, this);

					this.worldObj
					.playSoundAtEntity(
							this,
							mat.getKnifeMissSound(),
							(float) (var4.hitVec.lengthVector() / 5.0F > 1.0 ? 1.0F
									: var4.hitVec.lengthVector() / 5.0F),
									1.6F / (this.rand.nextFloat() * 0.2F + 0.4F));

					if (mat.onKnifeHit(this.shootingEntity, this,
							new ChunkCoordinates(this.xTile, this.yTile,
									this.zTile)))
						doDrop(mat);

					setDead();
				}
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		var20 = MathHelper.sqrt_double(this.motionX * this.motionX
				+ this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for (this.rotationPitch = (float) (Math.atan2(this.motionY, var20) * 180.0D / Math.PI); this.rotationPitch
				- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
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

		this.rotationPitch = this.prevRotationPitch
				+ (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw
				+ (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float var22 = 0.99F;
		var11 = 0.05F;

		if (isInWater()) {
			for (int var25 = 0; var25 < 6; ++var25) {
				var26 = 0.25F;
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX
						* var26, this.posY - this.motionY * var26, this.posZ
						- this.motionZ * var26, this.motionX, this.motionY,
						this.motionZ);
			}

			var22 = 0.65F;
		}

		this.motionX *= var22;
		this.motionY *= var22;
		this.motionZ *= var22;
		this.motionY -= var11;
		setPosition(this.posX, this.posY, this.posZ);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.xTile = nbt.getShort("xTile");
		this.yTile = nbt.getShort("yTile");
		this.zTile = nbt.getShort("zTile");
		this.inData = nbt.getByte("inData") & 255;
		this.arrowShake = nbt.getByte("shake") & 255;
		if (nbt.hasKey("iasSourceStack"))
			this.origin.readFromNBT(nbt.getCompoundTag("iasSourceStack"));
	}

	/**
	 * Sets the position and rotation. Only difference from the other one is no
	 * bounding on the rotation. Args: posX, posY, posZ, yaw, pitch
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double par1, double par3, double par5,
			float par7, float par8, int par9) {
		setPosition(par1, par3, par5);
		setRotation(par7, par8);
	}

	@Override
	public void setSource(ItemStack is) {
		this.dataWatcher.updateObject(16, IaSToolMaterial.extractMaterial(is)
				.getMaterialName());
		this.origin = is.copy();
	}

	@Override
	public void setThrowableHeading(double par1, double par3, double par5,
			float par7, float par8) {
		final float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3
				+ par5 * par5);
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
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1,
				par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3,
				var10) * 180.0D / Math.PI);
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double par1, double par3, double par5) {
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			final float var7 = MathHelper
					.sqrt_double(par1 * par1 + par5 * par5);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1,
					par5) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(
					par3, var7) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			setLocationAndAngles(this.posX, this.posY, this.posZ,
					this.rotationYaw, this.rotationPitch);
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setShort("xTile", (short) this.xTile);
		nbt.setShort("yTile", (short) this.yTile);
		nbt.setShort("zTile", (short) this.zTile);
		nbt.setByte("inData", (byte) this.inData);
		nbt.setByte("shake", (byte) this.arrowShake);
		final NBTTagCompound n = nbt.getCompoundTag("iasSourceStack");
		this.origin.writeToNBT(n);
		nbt.setTag("iasSourceStack", n);
	}

}
