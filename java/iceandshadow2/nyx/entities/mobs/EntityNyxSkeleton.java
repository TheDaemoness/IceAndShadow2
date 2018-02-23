package iceandshadow2.nyx.entities.mobs;

import java.util.List;
import java.util.Random;

import iceandshadow2.ias.ai.IIaSMobGetters;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IaSToolMaterial;
import iceandshadow2.ias.items.tools.IaSItemThrowingKnife;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.ias.util.IaSWorldHelper;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.ai.EntityAINyxRangedAttack;
import iceandshadow2.nyx.entities.ai.EntityAINyxRevenge;
import iceandshadow2.nyx.entities.ai.EntityAINyxSearch;
import iceandshadow2.nyx.entities.ai.EntityAINyxSkeletonWeaponSwitch;
import iceandshadow2.nyx.entities.ai.EntityAINyxTargeter;
import iceandshadow2.nyx.entities.ai.EntityAINyxWatchClosest;
import iceandshadow2.nyx.entities.ai.IIaSBlockPathDesirability;
import iceandshadow2.nyx.entities.ai.senses.*;
import iceandshadow2.nyx.entities.projectile.EntityIceArrow;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import iceandshadow2.nyx.entities.projectile.EntityThrowingKnife;
import iceandshadow2.nyx.entities.util.EntityOrbNourishment;
import iceandshadow2.nyx.items.tools.NyxBaseItemBow;
import iceandshadow2.nyx.items.tools.NyxItemBowFrostLong;
import iceandshadow2.nyx.items.tools.NyxItemSwordFrost;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNyxSkeleton extends EntitySkeleton implements IIaSMobGetters {

	public enum EnumNyxSkeletonType {
		RANDOM(-1), BOW_FROST_SHORT(0), KNIFE(1), MAGIC_SHADOW(2), BOW_FROST_LONG(3), RAPIER(4);

		public static EnumNyxSkeletonType fromId(int id) {
			for (final EnumNyxSkeletonType t : EnumNyxSkeletonType.values())
				if (t.id == (byte) id)
					return t;
			return null;
		}

		public byte id;

		EnumNyxSkeletonType(int aidee) {
			id = (byte) aidee;
		}
	}

	/** Probability to get armor */
	protected static float[] nyxSkeletonArmorProbability = new float[] { 0.0F, 0.01F, 0.03F, 0.09F };
	protected static double moveSpeed = 0.5;
	protected EntityAINyxRangedAttack rangedAttackDefault = new EntityAINyxRangedAttack(this,
			EntityNyxSkeleton.moveSpeed, 25, 35, 24.0F);
	protected EntityAINyxRangedAttack rangedAttackLong = new EntityAINyxRangedAttack(this, EntityNyxSkeleton.moveSpeed,
			45, 55, 32.0F);
	protected EntityAIAttackOnCollide meleeAttack = new EntityAIAttackOnCollide(this, EntityLivingBase.class,
			EntityNyxSkeleton.moveSpeed + 0.3, false);

	// protected EntityAIAttackOnCollide meleeAttackPassive = new
	// EntityAIAttackOnCollide(this, EntityAgeable.class,
	// EntityNyxSkeleton.moveSpeed + 0.3, true);
	protected EntityAINyxRangedAttack shadowAttack = new EntityAINyxRangedAttack(this,
			EntityNyxSkeleton.moveSpeed + 0.2, 35, 45, 12.0F);

	protected IaSSetSenses senses;

	private EntityLivingBase searched;

	private int regenDelay;
	protected EnumNyxSkeletonType typpe;
	protected boolean altWeaponFlag;
	protected ItemStack reserveWeapon;

	protected int throwDelay;

	public EntityNyxSkeleton(World par1World) {
		this(par1World, EnumNyxSkeletonType.RANDOM);
	}

	public EntityNyxSkeleton(World par1World, EnumNyxSkeletonType type) {
		super(par1World);
		maxHurtResistantTime /= 2;

		senses = new IaSSetSenses(this);
		senses.add(new IaSSenseMovement(this, 8.0));
		senses.add(new IaSSenseTouch(this));
		senses.add(new IaSSenseVision(this, 32.0F));

		setSkeletonType(0);
		experienceValue = 7;
		regenDelay = 0;
		throwDelay = 0;

		tasks.taskEntries.clear();
		targetTasks.taskEntries.clear();

		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAINyxSkeletonWeaponSwitch(this));
		tasks.addTask(3, new EntityAIFleeSun(this, EntityNyxSkeleton.moveSpeed + 0.5));
		tasks.addTask(3, new EntityAIAvoidEntity(this, EntityNyxWightToxic.class, 5.0F, EntityNyxSkeleton.moveSpeed,
				EntityNyxSkeleton.moveSpeed + 0.5));
		tasks.addTask(4, new EntityAINyxSearch(this));
		tasks.addTask(5, new EntityAIWander(this, EntityNyxSkeleton.moveSpeed));
		tasks.addTask(6, new EntityAINyxWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAINyxRevenge(this));
		targetTasks.addTask(2, new EntityAINyxTargeter(this));

		if (par1World != null && !par1World.isRemote) {
			setCombatTask();
		}

		if (type != EnumNyxSkeletonType.RANDOM) {
			setNyxSkeletonCombatType(type);
		} else {
			typpe = type;
		}
	}

	@Override
	protected void addRandomArmor() {
		if (IaSWorldHelper.getRegionLevel(this) < 2)
			return;
		if (rand.nextFloat() < EntityNyxSkeleton.nyxSkeletonArmorProbability[IaSWorldHelper.getDifficulty(worldObj)]) {
			int i = rand.nextInt(2 + (IaSWorldHelper.getDifficulty(worldObj) == 3 ? 1 : 0));
			final float f = IaSWorldHelper.getDifficulty(worldObj) == 3 ? 0.1F : 0.25F;

			for (int val = 0; val < 3; ++val)
				if (rand.nextFloat() < 0.095F) {
					++i;
				}

			for (int j = 3; j >= 1; --j) {
				if (j < 3 && rand.nextFloat() < f) {
					break;
				}

				if (getEquipmentInSlot(j) == null) {
					final ItemStack arm = IaSTools.getArmorForSlot(j, i);
					arm.setItemDamage(3 * arm.getMaxDamage() / 4 + rand.nextInt(arm.getMaxDamage() / 4) - 1);

					if (arm != null) {
						setCurrentItemOrArmor(j, arm);
					}
				}
			}
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getScaledMaxHealth());
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.33D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(EntityNyxSkeleton.moveSpeed);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		float f = getAttackStrength(par1Entity);
		int i = 0;

		if (par1Entity instanceof EntityLivingBase) {
			f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) par1Entity);
			i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) par1Entity);
		}

		final boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), f * 2 + 4);

		if (flag) {
			if (i > 0) {
				par1Entity.addVelocity(-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * i * 0.5F, 0.1D,
						MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * i * 0.5F);
				motionX *= 0.6D;
				motionZ *= 0.6D;
			}

			final int j = EnchantmentHelper.getFireAspectModifier(this);

			if (j > 0) {
				par1Entity.setFire(j * 4);
			}

			if (par1Entity instanceof EntityLivingBase) {
				if (getEquipmentInSlot(0) == null)
					return flag;
				((EntityLivingBase) par1Entity).addPotionEffect(
						new PotionEffect(Potion.moveSlowdown.id, 5 + 5 * IaSWorldHelper.getDifficulty(worldObj), 4));
			}
		}

		return flag;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float dmg) {
		if (par1DamageSource.getEntity() != null) {
			removePotionEffect(Potion.confusion.id);
		}
		if (isEntityInvulnerable() || par1DamageSource == DamageSource.drown)
			return false;
		if (getEquipmentInSlot(2) != null && !par1DamageSource.isUnblockable()
				&& getEquipmentInSlot(2).getItem() == IaSTools.armorNavistra[2])
			return false;
		addPotionEffect(new PotionEffect(Potion.hunger.id, 179, 0));
		if (par1DamageSource.isFireDamage())
			return super.attackEntityFrom(par1DamageSource, dmg * 3);
		if (par1DamageSource.isMagicDamage() && !par1DamageSource.isDamageAbsolute())
			return super.attackEntityFrom(par1DamageSource, Math.max(1, dmg - IaSWorldHelper.getRegionArmorMod(this)));
		if (par1DamageSource.isProjectile())
			return super.attackEntityFrom(par1DamageSource, dmg);
		if (getEquipmentInSlot(0) == null)
			return super.attackEntityFrom(par1DamageSource, dmg);
		if (getEquipmentInSlot(0).getItem() == NyxItems.frostSword) {
			final Entity attacker = par1DamageSource.getEntity();
			if (attacker != null) {
				attacker.hurtResistantTime = 0;
				if (attacker instanceof EntityLivingBase) {
					final int ulevel = ((NyxItemSwordFrost) NyxItems.frostSword).getUpgradeLevel(getEquipmentInSlot(0));
					((EntityLivingBase) attacker)
							.addPotionEffect(new PotionEffect(Potion.resistance.id, 15, -(ulevel + 1)));
				}
				attackEntityAsMob(attacker);
				return super.attackEntityFrom(par1DamageSource, dmg / 2);
			}
		}
		return super.attackEntityFrom(par1DamageSource, dmg);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLiving, float par2) {
		final ItemStack wielding = getHeldItem();
		if (wielding != null && wielding.getItem() instanceof NyxBaseItemBow) {
			doBowAttack(par1EntityLiving, par2, wielding.getItem() instanceof NyxItemBowFrostLong);
		} else {
			doShadowAttack(par1EntityLiving, par2);
			if (typpe != EnumNyxSkeletonType.MAGIC_SHADOW) {
				attackEntityFrom(DamageSource.magic, 6.0F);
			}
		}
	}

	@Override
	public boolean couldFlyFasterWithBoots() {
		return false;
	}

	public void doBowAttack(EntityLivingBase par1EntityLiving, float par2, boolean longe) {
		final int slowtime = IaSWorldHelper.getDifficulty(worldObj) * (longe ? 70 : 15);
		final int slowstr = IaSWorldHelper.getDifficulty(worldObj) + (longe ? 1 : -1);
		final int dif = IaSWorldHelper.getDifficulty(worldObj);
		EntityIceArrow var2;
		final double xdelta = par1EntityLiving.posX - posX;
		final double zdelta = par1EntityLiving.posZ - posZ;
		final double range = Math.sqrt(xdelta * xdelta + zdelta * zdelta) / 20;
		final float velocity = longe ? 3.2F : 1.6F;
		final double xbias = 0;
		final double zbias = 0;
		final double ydelta = par1EntityLiving.posY - posY + (par1EntityLiving.getEyeHeight() - getEyeHeight());
		final double ybias = range * range * 5.5 / velocity;
		var2 = new EntityIceArrow(worldObj, this, 0F, slowstr, slowtime);
		var2.setThrowableHeading(xdelta + xbias, ydelta + ybias, zdelta + zbias, velocity, 5F / velocity);
		var2.setIsCritical(longe);
		int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
		final int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
		var3 += dif == 3 ? 1 : 0;

		var2.setDamage(getAttackStrength(par1EntityLiving));

		if (var3 > 0) {
			var2.setDamage(var2.getDamage() + var3 * 0.5D + 0.5D);
		}

		if (var4 > 0) {
			var2.setKnockbackStrength(var4);
		}

		playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.3F + 0.6F));
		worldObj.spawnEntityInWorld(var2);
	}

	public void doShadowAttack(EntityLivingBase par1EntityLiving, float par2) {
		final boolean harm_undead = par1EntityLiving.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
		final EntityThrowable entityball = new EntityShadowBall(worldObj, this, harm_undead,
				IaSWorldHelper.getRegionLevel(par1EntityLiving) >= 6);

		final double d0 = par1EntityLiving.posX + par1EntityLiving.motionX - posX;
		final double d1 = par1EntityLiving.posY + par1EntityLiving.getEyeHeight() - getEyeHeight() - posY;
		final double d2 = par1EntityLiving.posZ + par1EntityLiving.motionZ - posZ;
		final float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

		if (f1 <= 2.0) {
			entityball.setThrowableHeading(d0, d1, d2, 0.40F, 8.0F);
		} else {
			entityball.rotationPitch += 20.0F;
		}
		entityball.setThrowableHeading(d0, d1 + f1 * 0.2F, d2, 0.80F, 8.0F);
		worldObj.spawnEntityInWorld(entityball);
	}

	/**
	 * Drop 0-2 items of this living's type. @param par1 - Whether this entity
	 * has recently been hit by a player. @param par2 - Level of Looting used to
	 * kill this mob.
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2) {
		if (!par1)
			return;

		this.dropItem(Items.bone, 1);
		this.dropItem(NyxItems.icicle, worldObj.rand.nextInt(2));

		worldObj.spawnEntityInWorld(new EntityOrbNourishment(worldObj, posX, posY, posZ, 2));

		/*
		 * Calendar var1 = this.worldObj.getCurrentDate(); if (var1.get(2) + 1
		 * == 10 && var1.get(5) == 31) this.dropItem(new
		 * ItemStack(IaSItems.nyxCandy,1,1)); if (var1.get(2) + 1 == 12 &&
		 * (var1.get(5) == 25 || var1.get(5) == 24)) this.dropItem(new
		 * ItemStack(IaSItems.nyxCandy,1,2));
		 */
	}

	public EntityItem dropItem(ItemStack par1ItemStack) {
		if (par1ItemStack.stackSize == 0)
			return null;
		else {
			final EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, par1ItemStack);
			entityitem.delayBeforeCanPickup = 10;
			if (captureDrops) {
				capturedDrops.add(entityitem);
			} else {
				worldObj.spawnEntityInWorld(entityitem);
			}
			return entityitem;
		}
	}

	@Override
	protected void dropRareDrop(int par1) {
		this.dropItem(NyxItems.boneCursed, 1);
	}

	@Override
	protected void fall(float par1) {
		super.fall(par1 / 3.0F);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NYX;
	}

	@Override
	public float getAttackStrength(Entity par1Entity) {
		getHeldItem();
		int var3;
		if (worldObj != null) {
			var3 = IaSWorldHelper.getDifficulty(worldObj) >= 3 ? 7 : 8;
		} else {
			var3 = 8;
		}

		/*
		 * if (var2 != null) { if (var2.getItem() instanceof IIaSTool) var3 +=
		 * MathHelper .ceiling_float_int(IaSToolMaterial.extractMaterial(var2).
		 * getToolDamage(var2, this, par1Entity))/2; }
		 */
		return var3;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		if (getAttackTarget() != null || getSearchTarget() != null)
			return 1;
		final int lightb = worldObj.getBlockLightValue(i, j, k);
		float mod = 0F;
		final Block bl = worldObj.getBlock(i, j, k);
		if (bl instanceof IIaSBlockPathDesirability) {
			mod = ((IIaSBlockPathDesirability) bl).getBlockPathWeight(worldObj, i, j, k);
		}
		return mod + (lightb >= 7 ? lightb * 2 : 0);
	}

	@Override
	public boolean getCanSpawnHere() {
		final int SAFE_RADIUS = 48;
		if (posX * posX + posZ * posZ < SAFE_RADIUS * SAFE_RADIUS)
			return false;
		final List list = worldObj.getEntitiesWithinAABB(this.getClass(), AxisAlignedBB
				.getBoundingBox(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16, 12, 16));
		return list.size() <= (1 + IaSWorldHelper.getRegionLevel(this) / 2) && posY > 64.0F && super.getCanSpawnHere();
	}

	public ItemStack getDefaultAlternateWeapon(EnumNyxSkeletonType taipe) {
		if (taipe == EnumNyxSkeletonType.MAGIC_SHADOW)
			return new ItemStack(Items.bone);
		else
			return IaSTools.setToolMaterial(IaSTools.sword, "Icicle");
	}

	public ItemStack getDefaultWeapon(EnumNyxSkeletonType taipe) {
		if (taipe == EnumNyxSkeletonType.KNIFE) {
			final ItemStack ait = IaSTools.setToolMaterial(IaSTools.knife, "Icicle");
			ait.stackSize = new Random().nextInt(8) + 4;

			final int lvl = IaSWorldHelper.getRegionLevel(this);
			if (lvl >= 6) {
				ait.addEnchantment(Enchantment.knockback, 1);
				ait.addEnchantment(Enchantment.fireAspect, 1);
			}
			return ait;
		}
		if (taipe == EnumNyxSkeletonType.BOW_FROST_SHORT)
			return new ItemStack(NyxItems.frostBowShort, 1, 383 - rand.nextInt(16));
		if (taipe == EnumNyxSkeletonType.MAGIC_SHADOW)
			return new ItemStack(NyxItems.boneCursed);
		if (taipe == EnumNyxSkeletonType.BOW_FROST_LONG)
			return new ItemStack(NyxItems.frostBowLong, 1, 254 - rand.nextInt(16));
		if (taipe == EnumNyxSkeletonType.RAPIER)
			return new ItemStack(NyxItems.frostSword, 1, 45 + rand.nextInt(50));
		return null;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return null;
	}

	@Override
	public double getMoveSpeed() {
		return EntityNyxSkeleton.moveSpeed;
	}

	public EnumNyxSkeletonType getNyxSkeletonCombatType() {
		return typpe;
	}

	public ItemStack getReserveWeapon() {
		return reserveWeapon;
	}

	@Override
	public double getScaledMaxHealth() {
		return 30.0;
	}

	@Override
	public EntityLivingBase getSearchTarget() {
		return searched;
	}

	@Override
	public IaSSenseOld getSense() {
		return senses;
	}

	@Override
	public int getTotalArmorValue() {
		return super.getTotalArmorValue() + IaSWorldHelper.getRegionArmorMod(this);
	}

	@Override
	public boolean hates(EnumIaSAspect aspect) {
		return aspect == EnumIaSAspect.ALIEN || aspect == EnumIaSAspect.ANCIENT;
	}

	public boolean isUsingAlternateWeapon() {
		return altWeaponFlag;
	}

	@Override
	protected void jump() {
		super.jump();
		motionY = 1.2D * 0.41999998688697815D;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (worldObj.isRemote)
			return;
		if (!this.isPotionActive(Potion.hunger))
			if (--regenDelay <= 0) {
				heal(1);
				regenDelay = 30;
			}
		if (getEquipmentInSlot(0) != null && !worldObj.isRemote) {
			final ItemStack is = getEquipmentInSlot(0);
			if (is.getItem() instanceof IaSItemThrowingKnife && getAttackTarget() != null)
				if (throwDelay <= 0) {
					final double dist = getDistanceSqToEntity(getAttackTarget());
					if (dist < 4 || dist > 100)
						return;
					if (!getEntitySenses().canSee(getAttackTarget()))
						return;
					final ItemStack projkni = is.copy();
					projkni.addEnchantment(Enchantment.sharpness, 1 + worldObj.difficultySetting.getDifficultyId());
					final EntityThrowingKnife etn = new EntityThrowingKnife(worldObj, this, getAttackTarget(), 1.1F,
							2.0F, projkni);
					worldObj.playSoundAtEntity(this, "random.bow", 0.5F, 0.75F);
					final IaSToolMaterial mat = IaSToolMaterial.extractMaterial(is);
					mat.onKnifeThrow(is, this, etn);
					worldObj.spawnEntityInWorld(etn);
					throwDelay = mat.getKnifeCooldown(is, worldObj, this) * 2;
				} else {
					--throwDelay;
				}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData dat) {
		equipmentDropChances[0] = 0.0F;
		altWeaponFlag = false;

		if (getSkeletonType() == 1 || typpe != EnumNyxSkeletonType.RANDOM)
			return dat;

		final int dif = IaSWorldHelper.getDifficulty(worldObj);
		final int reg = IaSWorldHelper.getRegionLevel(this);

		// Knife skeleton.
		if (dif >= 2 && reg >= 3 && rand.nextInt(4) == 0) {
			setNyxSkeletonCombatType(EnumNyxSkeletonType.KNIFE);
			equipmentDropChances[0] = 0.33F;
		}

		// Special skeleton.
		else if (dif >= 2 && reg >= 1 && rand.nextInt(dif == 3 ? 8 : 12) == 0) {
			final ItemStack helm = new ItemStack(Items.leather_helmet);
			((ItemArmor) helm.getItem()).func_82813_b(helm, 0x773333);
			setCurrentItemOrArmor(4, helm);
			equipmentDropChances[4] = 0.0F;
			if (reg >= 5 && rand.nextBoolean()) {
				setNyxSkeletonCombatType(EnumNyxSkeletonType.BOW_FROST_LONG);
				equipmentDropChances[0] = 0.1F;
			} else if (reg >= 3 && rand.nextBoolean()) {
				setNyxSkeletonCombatType(EnumNyxSkeletonType.RAPIER);
				equipmentDropChances[0] = 0.1F;
			} else {
				setNyxSkeletonCombatType(EnumNyxSkeletonType.MAGIC_SHADOW);
			}
		}

		// Bow skeleton.
		else {
			setCurrentItemOrArmor(0, getDefaultWeapon(EnumNyxSkeletonType.BOW_FROST_SHORT));
			setNyxSkeletonCombatType(EnumNyxSkeletonType.BOW_FROST_SHORT);
			equipmentDropChances[0] = 0.02F;
		}

		addRandomArmor();
		return dat;
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		if (par1NBTTagCompound.hasKey("NyxSkeletonCombatStyle")) {
			this.setNyxSkeletonCombatType(par1NBTTagCompound.getByte("NyxSkeletonCombatStyle"));
		}

		super.readEntityFromNBT(par1NBTTagCompound);

		setCombatTask();
	}

	@Override
	public void setAttackTarget(EntityLivingBase p_70624_1_) {
		if (this.isPotionActive(Potion.confusion))
			return;
		super.setAttackTarget(p_70624_1_);
	}

	@Override
	public void setCombatTask() {
		tasks.removeTask(rangedAttackDefault);
		tasks.removeTask(rangedAttackLong);
		tasks.removeTask(meleeAttack);
		// this.tasks.removeTask(this.meleeAttackPassive);
		tasks.removeTask(shadowAttack);
		final ItemStack var1 = getHeldItem();

		if (var1 != null && var1.getItem() instanceof NyxBaseItemBow) {
			if (typpe == EnumNyxSkeletonType.BOW_FROST_LONG) {
				tasks.addTask(4, rangedAttackLong);
			} else {
				tasks.addTask(4, rangedAttackDefault);
			}
		} else if (typpe == EnumNyxSkeletonType.MAGIC_SHADOW) {
			tasks.addTask(4, shadowAttack);
		}
		else {
			tasks.addTask(4, meleeAttack);
		// this.tasks.addTask(4, this.meleeAttackPassive);
		}
	}

	@Override
	public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
		super.setCurrentItemOrArmor(par1, par2ItemStack);
		if (!worldObj.isRemote && par1 == 0) {
			setCombatTask();
		}
	}

	public void setNyxSkeletonCombatType(EnumNyxSkeletonType taipe) {
		typpe = taipe;
		setCurrentItemOrArmor(0, getDefaultWeapon(taipe));
		reserveWeapon = getDefaultAlternateWeapon(taipe);
	}

	public void setNyxSkeletonCombatType(int comb_id) {
		setNyxSkeletonCombatType(EnumNyxSkeletonType.fromId(comb_id));
	}

	@Override
	public void setSearchTarget(EntityLivingBase ent) {
		searched = ent;
	}

	public void useAlternateWeapon(boolean use) {
		if (altWeaponFlag == use)
			return;

		altWeaponFlag = use;
		final ItemStack switche = getEquipmentInSlot(0);
		setCurrentItemOrArmor(0, reserveWeapon);
		reserveWeapon = switche;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByte("NyxSkeletonCombatStyle", typpe.id);
	}
}
