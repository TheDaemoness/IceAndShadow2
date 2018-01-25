package iceandshadow2.nyx.entities.projectile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.ias.util.IaSEntityHelper;
import iceandshadow2.ias.util.IaSWorldHelper;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
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
		origin = new ItemStack(IaSTools.knife);
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
		yOffset = 0.0F;
	}

	public EntityThrowingKnife(World w, double x, double y, double z, ItemStack is) {
		this(w, is);
		setPosition(x, y, z);
	}

	public EntityThrowingKnife(World w, EntityLivingBase shooter, EntityLivingBase target, float vel, float acc,
			ItemStack is) {
		this(w, is);
		shootingEntity = shooter;
		posY = shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D;
		final double var6 = target.posX - shooter.posX;
		final double var8 = target.posY + target.getEyeHeight() - 0.699999988079071D - posY;
		final double var10 = target.posZ - shooter.posZ;
		final double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);

		if (var12 >= 1.0E-7D) {
			final float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
			final float var15 = (float) -(Math.atan2(var8, var12) * 180.0D / Math.PI);
			final double var16 = var6 / var12;
			final double var18 = var10 / var12;
			setLocationAndAngles(shooter.posX + var16, posY, shooter.posZ + var18, var14, var15);
			final float var20 = (float) var12 * 0.2F;
			setThrowableHeading(var6, var8 + var20, var10, vel, acc);
		}
	}

	public EntityThrowingKnife(World w, EntityLivingBase shooter, float vel, ItemStack is) {
		this(w, is);
		shootingEntity = shooter;
		setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw,
				shooter.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
		motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);
		setThrowableHeading(motionX, motionY, motionZ, vel * 1.5F, 1.0F);
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
		if (!worldObj.isRemote) {
			final EntityItem drop = new EntityItem(worldObj, posX, posY, posZ, mat.getKnifeDrop(shootingEntity, this));
			worldObj.spawnEntityInWorld(drop);
		}
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(16, IaSRegistry.getDefaultMaterial().getMaterialName());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender(float par1) {
		return 15728880;
	}

	@Override
	public ItemStack getItemStack() {
		final ItemStack nis = origin.copy();
		nis.stackSize = 1;
		return nis;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getShadowSize() {
		return 0.5F;
	}

	public ResourceLocation getTexture() {
		return IaSRegistry.getToolMaterial(dataWatcher.getWatchableObjectString(16)).getKnifeTexture(this);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			final float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, var1) * 180.0D / Math.PI);
		}

		final Block var16 = worldObj.getBlock(xTile, yTile, zTile);

		if (var16 != null) {
			var16.setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
			final AxisAlignedBB var2 = var16.getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);

			if (var2 != null && var2.isVecInside(Vec3.createVectorHelper(posX, posY, posZ))) {

				final Vec3 vel = Vec3.createVectorHelper(motionX, motionY, motionZ);

				final IaSToolMaterial mat = IaSRegistry.getToolMaterial(dataWatcher.getWatchableObjectString(16));
				worldObj.playSoundAtEntity(this, mat.getKnifeMissSound(),
						(float) (vel.lengthVector() / 5.0F > 1.0F ? 1.0F : vel.lengthVector() / 5.0F),
						1.6F / (rand.nextFloat() * 0.2F + 0.4F));
				if (mat.onKnifeHit(shootingEntity, this, new ChunkCoordinates(xTile, yTile, zTile))) {
					doDrop(mat);
				}
				setDead();
			}
		}

		if (arrowShake > 0) {
			arrowShake = 0;
		}

		++ticksInAir;
		Vec3 var17 = Vec3.createVectorHelper(posX, posY, posZ);
		Vec3 var3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
		MovingObjectPosition var4 = worldObj.rayTraceBlocks(var17, var3);
		var17 = Vec3.createVectorHelper(posX, posY, posZ);
		var3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

		if (var4 != null) {
			var3 = Vec3.createVectorHelper(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
		}

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

		if (var5 != null) {
			var4 = new MovingObjectPosition(var5);
		}

		float var20;
		float var26;

		boolean shouldKill = true;
		final IaSToolMaterial mat = IaSRegistry.getToolMaterial(dataWatcher.getWatchableObjectString(16));
		if (var4 != null)
			if (var4.entityHit != null && var4.entityHit != shootingEntity) {
				worldObj.playSoundAtEntity(this, "game.hostile.hurt.fall.small",
						(float) (var4.hitVec.lengthVector() / 5.0F > 1.0 ? 1.0F : var4.hitVec.lengthVector() / 5.0F),
						1.2F / (rand.nextFloat() * 0.2F + 0.9F));

				var20 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);

				float basedmg = mat.getKnifeDamage(this, shootingEntity, var4.entityHit);

				final Entity target = var4.entityHit;
				Item equip = null;
				boolean swordInUse = false;
				if (target instanceof EntityLivingBase && ((EntityLivingBase) target).getEquipmentInSlot(0) != null) {
					equip = ((EntityLivingBase) target).getEquipmentInSlot(0).getItem();
				}
				if (target instanceof EntityPlayer && IaSEntityHelper.isInFrontOf((target), this)) {
					swordInUse = ((EntityPlayer) target).isUsingItem();
				}
				if (equip == NyxItems.frostSword && (swordInUse || target instanceof EntityMob)) {
					final int itemDamage = (IaSWorldHelper.getDifficulty(worldObj) + (int) basedmg);
					((EntityLivingBase) target).getEquipmentInSlot(0).damageItem(itemDamage, (EntityLivingBase) target);
					motionX = -motionX;
					motionY = -motionY;
					motionZ = -motionZ;
					worldObj.playSoundAtEntity(this, "random.bow", (float) (var4.hitVec.lengthVector() / 5.0F > 1.0
							? 1.0F : var4.hitVec.lengthVector() / 5.0F), 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					shootingEntity = null;
					shouldKill = false;
				}

				if (origin != null) {
					int enchant = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, origin);
					basedmg += 1.25 * enchant;
					if (var4.entityHit instanceof EntityLivingBase) {
						final EntityLivingBase elb = (EntityLivingBase) var4.entityHit;
						final EnumCreatureAttribute ca = elb.getCreatureAttribute();
						if (ca == EnumCreatureAttribute.ARTHROPOD) {
							enchant = EnchantmentHelper.getEnchantmentLevel(Enchantment.baneOfArthropods.effectId,
									origin);
							basedmg += 2.5 * enchant;
						} else if (ca == EnumCreatureAttribute.UNDEAD) {
							enchant = EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, origin);
							basedmg += 2.5 * enchant;
						}
					}
				}

				final int var23 = MathHelper.ceiling_double_int(var20 * basedmg);

				final DamageSource var21 = mat.getKnifeDamageSource(this, shootingEntity);
				final boolean drop = shouldKill ? mat.onKnifeHit(shootingEntity, this, var4.entityHit) : false;

				if (var4.entityHit.attackEntityFrom(var21, var23)) {
					if (origin != null) {
						final int enchant = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId,
								origin);
						if (enchant >= 1) {
							var4.entityHit.setFire(enchant * 4 - 1);
						}
					}
					if (var4.entityHit instanceof EntityLiving) {
						int knockbackStrength = 0;
						if (origin != null) {
							knockbackStrength += EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId,
									origin);
						}

						var26 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);

						if (var26 > 0.0F) {
							var4.entityHit.addVelocity(motionX * knockbackStrength * 0.6000000238418579D / var26,
									0.1D * knockbackStrength,
									motionZ * knockbackStrength * 0.6000000238418579D / var26);
						}

						if (shootingEntity != null && var4.entityHit != shootingEntity
								&& var4.entityHit instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP) {
							((EntityPlayerMP) shootingEntity).playerNetServerHandler
									.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
						}
					}

					if (shouldKill && !(var4.entityHit instanceof EntityEnderman)) {
						if (drop) {
							doDrop(mat);
						}
						setDead();
					}

				}
			} else {
				xTile = var4.blockX;
				yTile = var4.blockY;
				zTile = var4.blockZ;
				inTile = worldObj.getBlock(xTile, yTile, zTile);
				inData = worldObj.getBlockMetadata(xTile, yTile, zTile);

				motionX = (float) (var4.hitVec.xCoord - posX);
				motionY = (float) (var4.hitVec.yCoord - posY);
				motionZ = (float) (var4.hitVec.zCoord - posZ);
				var20 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
				posX -= motionX / var20 * 0.05000000074505806D;
				posY -= motionY / var20 * 0.05000000074505806D;
				posZ -= motionZ / var20 * 0.05000000074505806D;

				if (inTile != null) {
					inTile.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);

					worldObj.playSoundAtEntity(this, mat.getKnifeMissSound(),
							(float) (var4.hitVec.lengthVector() / 5.0F > 1.0 ? 1.0F
									: var4.hitVec.lengthVector() / 5.0F),
							1.6F / (rand.nextFloat() * 0.2F + 0.4F));

					if (mat.onKnifeHit(shootingEntity, this, new ChunkCoordinates(xTile, yTile, zTile))) {
						doDrop(mat);
					}

					setDead();
				}
			}

		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		var20 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

		for (rotationPitch = (float) (Math.atan2(motionY, var20) * 180.0D / Math.PI); rotationPitch
				- prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {
			;
		}

		while (rotationPitch - prevRotationPitch >= 180.0F) {
			prevRotationPitch += 360.0F;
		}

		while (rotationYaw - prevRotationYaw < -180.0F) {
			prevRotationYaw -= 360.0F;
		}

		while (rotationYaw - prevRotationYaw >= 180.0F) {
			prevRotationYaw += 360.0F;
		}

		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		float var22 = 0.99F;
		var11 = 0.05F;

		if (isInWater()) {
			for (int var25 = 0; var25 < 6; ++var25) {
				var26 = 0.25F;
				worldObj.spawnParticle("bubble", posX - motionX * var26, posY - motionY * var26, posZ - motionZ * var26,
						motionX, motionY, motionZ);
			}

			var22 = 0.65F;
		}

		motionX *= var22;
		motionY *= var22;
		motionZ *= var22;
		motionY -= var11;
		setPosition(posX, posY, posZ);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		xTile = nbt.getShort("xTile");
		yTile = nbt.getShort("yTile");
		zTile = nbt.getShort("zTile");
		inData = nbt.getByte("inData") & 255;
		arrowShake = nbt.getByte("shake") & 255;
		if (nbt.hasKey("iasSourceStack")) {
			origin.readFromNBT(nbt.getCompoundTag("iasSourceStack"));
		}
	}

	/**
	 * Sets the position and rotation. Only difference from the other one is no
	 * bounding on the rotation. Args: posX, posY, posZ, yaw, pitch
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
		setPosition(par1, par3, par5);
		setRotation(par7, par8);
	}

	@Override
	public void setSource(ItemStack is) {
		dataWatcher.updateObject(16, IaSToolMaterial.extractMaterial(is).getMaterialName());
		origin = is.copy();
	}

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

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@Override
	@SideOnly(Side.CLIENT)
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

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setShort("xTile", (short) xTile);
		nbt.setShort("yTile", (short) yTile);
		nbt.setShort("zTile", (short) zTile);
		nbt.setByte("inData", (byte) inData);
		nbt.setByte("shake", (byte) arrowShake);
		final NBTTagCompound n = nbt.getCompoundTag("iasSourceStack");
		origin.writeToNBT(n);
		nbt.setTag("iasSourceStack", n);
	}

}
