package iceandshadow2.nyx.entities.mobs;

import java.util.Random;

import iceandshadow2.IaSFlags;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.items.tools.IaSItemThrowingKnife;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.ai.EntityAINyxRangedAttack;
import iceandshadow2.nyx.entities.ai.EntityAINyxSearch;
import iceandshadow2.nyx.entities.ai.EntityAINyxSkeletonWeaponSwitch;
import iceandshadow2.nyx.entities.ai.EntityAINyxTargeter;
import iceandshadow2.nyx.entities.ai.senses.*;
import iceandshadow2.nyx.entities.projectile.EntityIceArrow;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import iceandshadow2.nyx.entities.projectile.EntityThrowingKnife;
import iceandshadow2.nyx.items.tools.NyxItemBow;
import iceandshadow2.nyx.items.tools.NyxItemBowFrostLong;
import iceandshadow2.util.IaSWorldHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNyxSkeleton extends EntitySkeleton implements IIaSSensate,
IIaSMobGetters {

	public enum EnumNyxSkeletonType {
		RANDOM(-1), BOW_FROST_SHORT(0), KNIFE(1), MAGIC_SHADOW(2), BOW_FROST_LONG(3), RAPIER(4);

		public static EnumNyxSkeletonType fromId(int id) {
			for (final EnumNyxSkeletonType t : values()) {
				if (t.id == (byte) id)
					return t;
			}
			return null;
		}

		public byte id;

		EnumNyxSkeletonType(int aidee) {
			id = (byte) aidee;
		}
	}

	protected EntityAINyxRangedAttack rangedAttackDefault = new EntityAINyxRangedAttack(
			this, this.moveSpeed, 25, 35, 24.0F);
	protected EntityAINyxRangedAttack rangedAttackLong = new EntityAINyxRangedAttack(
			this, this.moveSpeed, 45, 55, 32.0F);
	protected EntityAIAttackOnCollide meleeAttackPlayer = new EntityAIAttackOnCollide(
			this, EntityPlayer.class, this.moveSpeed + 0.3, false);
	protected EntityAIAttackOnCollide meleeAttackPassive = new EntityAIAttackOnCollide(
			this, EntityAgeable.class, this.moveSpeed + 0.3, true);
	protected EntityAINyxRangedAttack shadowAttack = new EntityAINyxRangedAttack(
			this, this.moveSpeed + 0.2, 35, 45, 12.0F);

	protected IaSSetSenses senses;
	private EntityLivingBase searched;

	private int regenDelay;

	/** Probability to get armor */
	protected static float[] nyxSkeletonArmorProbability = new float[] { 0.0F,
		0.01F, 0.03F, 0.09F };

	protected EnumNyxSkeletonType typpe;
	protected boolean altWeaponFlag;
	protected ItemStack reserveWeapon;
	protected int throwDelay;

	protected static double moveSpeed = 0.5;

	public EntityNyxSkeleton(World par1World) {
		this(par1World, EnumNyxSkeletonType.RANDOM);
	}
	
	public EntityNyxSkeleton(World par1World, EnumNyxSkeletonType type) {
		super(par1World);

		senses = new IaSSetSenses(this);
		senses.add(new IaSSenseMovement(this, 8.0));
		senses.add(new IaSSenseTouch(this));
		senses.add(new IaSSenseVision(this, 32.0F));

		this.setSkeletonType(0);
		this.experienceValue = 7;
		this.regenDelay = 0;
		this.throwDelay = 0;

		this.tasks.taskEntries.clear();
		this.targetTasks.taskEntries.clear();

		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAINyxSkeletonWeaponSwitch(this));
		this.tasks.addTask(3, new EntityAIFleeSun(this,
				EntityNyxSkeleton.moveSpeed + 0.5));
		this.tasks.addTask(4, new EntityAINyxSearch(this));
		this.tasks.addTask(5, new EntityAIWander(this,
				EntityNyxSkeleton.moveSpeed));
		this.tasks.addTask(6, new EntityAIWatchClosest(this,
				EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINyxTargeter(this));

		if (par1World != null && !par1World.isRemote)
			this.setCombatTask();

		if(type != EnumNyxSkeletonType.RANDOM) 
			setNyxSkeletonCombatType(type);
		else
			this.typpe = type;
	}

	@Override
	protected void addRandomArmor() {
		if (this.rand.nextFloat() < nyxSkeletonArmorProbability[IaSWorldHelper
		                                                        .getDifficulty(this.worldObj)]) {
			int i = this.rand.nextInt(2 + (IaSWorldHelper
					.getDifficulty(this.worldObj) == 3 ? 1 : 0));
			final float f = IaSWorldHelper.getDifficulty(this.worldObj) == 3 ? 0.1F
					: 0.25F;

			for (int val = 0; val < 3; ++val) {
				if (this.rand.nextFloat() < 0.095F)
					++i;
			}

			for (int j = 3; j >= 1; --j) {
				if (j < 3 && this.rand.nextFloat() < f)
					break;

				if (this.getEquipmentInSlot(j) == null) {
					final ItemStack arm = IaSTools.getArmorForSlot(j, i);

					if (arm != null)
						this.setCurrentItemOrArmor(j, arm);
				}
			}
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
		.setBaseValue(getScaledMaxHealth());
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
		.setBaseValue(0.33D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
		.setBaseValue(EntityNyxSkeleton.moveSpeed);
		this.getEntityAttribute(SharedMonsterAttributes.followRange)
		.setBaseValue(24.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		float f = this.getAttackStrength(par1Entity);
		int i = 0;

		if (par1Entity instanceof EntityLivingBase) {
			f += EnchantmentHelper.getEnchantmentModifierLiving(this,
					(EntityLivingBase) par1Entity);
			i += EnchantmentHelper.getKnockbackModifier(this,
					(EntityLivingBase) par1Entity);
		}

		final boolean flag = par1Entity.attackEntityFrom(
				DamageSource.causeMobDamage(this), f * 2 + 4);

		if (flag) {
			if (i > 0) {
				par1Entity.addVelocity(
						-MathHelper.sin(this.rotationYaw * (float) Math.PI
								/ 180.0F)
								* i * 0.5F,
								0.1D,
								MathHelper.cos(this.rotationYaw * (float) Math.PI
										/ 180.0F)
										* i * 0.5F);
				this.motionX *= 0.6D;
				this.motionZ *= 0.6D;
			}

			final int j = EnchantmentHelper.getFireAspectModifier(this);

			if (j > 0)
				par1Entity.setFire(j * 4);

			if (par1Entity instanceof EntityLivingBase) {
				if(this.getEquipmentInSlot(0) == null)
					return flag;
				if(this.getEquipmentInSlot(0).getItem() == NyxItems.frostSword)
					((EntityLivingBase) par1Entity)
						.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,
						5+5*IaSWorldHelper.getDifficulty(this.worldObj), 4));
			}
		}

		return flag;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float dmg) {
		if (this.isEntityInvulnerable()
				|| par1DamageSource == DamageSource.drown)
			return false;
		if (this.getEquipmentInSlot(2) != null
				&& !par1DamageSource.isUnblockable()
				&& this.getEquipmentInSlot(2).getItem() == IaSTools.armorNavistra[2])
			return false;
		this.addPotionEffect(new PotionEffect(Potion.hunger.id, 179, 0));
		if (par1DamageSource.isFireDamage())
			return super.attackEntityFrom(par1DamageSource, dmg * 3);
		if(par1DamageSource.isMagicDamage() && !par1DamageSource.isDamageAbsolute())
			return super.attackEntityFrom(par1DamageSource, Math.max(1,dmg-IaSWorldHelper.getRegionArmorMod(this)));
		return super.attackEntityFrom(par1DamageSource, dmg);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLiving,
			float par2) {
		final ItemStack wielding = this.getHeldItem();
		if (wielding != null && wielding.getItem() instanceof NyxItemBow) {
			doBowAttack(par1EntityLiving, par2,
					wielding.getItem() instanceof NyxItemBowFrostLong);
		} else {
			doShadowAttack(par1EntityLiving, par2);
			if (typpe != EnumNyxSkeletonType.MAGIC_SHADOW)
				this.attackEntityFrom(DamageSource.magic, 6.0F);
		}
	}

	public void doBowAttack(EntityLivingBase par1EntityLiving, float par2,
			boolean longe) {
		final int slowtime = IaSWorldHelper.getDifficulty(this.worldObj)
				* (longe ? 70 : 15);
		final int slowstr = IaSWorldHelper.getDifficulty(this.worldObj)
				+ (longe ? 1 : -1);
		final int dif = IaSWorldHelper.getDifficulty(worldObj);
		EntityIceArrow var2;
		if (longe) {
			var2 = new EntityIceArrow(this.worldObj, this, 2.4F, slowstr,
					slowtime);
			final double ydelta = par1EntityLiving.posY - this.posY + par1EntityLiving.getEyeHeight() - this.getEyeHeight();
			var2.setThrowableHeading(par1EntityLiving.posX - this.posX, ydelta,
					par1EntityLiving.posZ - this.posZ, 3.2F, 2.0F);
		} else
			var2 = new EntityIceArrow(this.worldObj, this, par1EntityLiving,
					1.8F, 5.0F, slowstr, slowtime);
		var2.setIsCritical(longe);
		int var3 = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.power.effectId, this.getHeldItem());
		final int var4 = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.punch.effectId, this.getHeldItem());
		var3 += dif == 3 ? 1 : 0;

		var2.setDamage(getAttackStrength(par1EntityLiving));

		if (var3 > 0) {
			var2.setDamage(var2.getDamage() + var3 * 0.5D + 0.5D);
		}

		if (var4 > 0) {
			var2.setKnockbackStrength(var4);
		}

		this.playSound("random.bow", 1.0F,
				1.0F / (this.getRNG().nextFloat() * 0.3F + 0.6F));
		this.worldObj.spawnEntityInWorld(var2);
	}

	public void doShadowAttack(EntityLivingBase par1EntityLiving, float par2) {
		final boolean harm_undead = par1EntityLiving.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
		final EntityThrowable entityball = new EntityShadowBall(this.worldObj,
				this, harm_undead, IaSWorldHelper.getRegionLevel(par1EntityLiving)>=6);

		final double d0 = par1EntityLiving.posX + par1EntityLiving.motionX
				- this.posX;
		final double d1 = par1EntityLiving.posY
				+ par1EntityLiving.getEyeHeight() - this.getEyeHeight()
				- this.posY;
		final double d2 = par1EntityLiving.posZ + par1EntityLiving.motionZ
				- this.posZ;
		final float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

		if (f1 <= 2.0)
			entityball.setThrowableHeading(d0, d1, d2, 0.40F, 8.0F);
		else
			entityball.rotationPitch += 20.0F;
		entityball.setThrowableHeading(d0, d1 + f1 * 0.2F, d2, 0.80F, 8.0F);
		this.worldObj.spawnEntityInWorld(entityball);
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
		if (this.rand.nextBoolean())
			this.dropItem(NyxItems.icicle, 1);

		/*
		 * Calendar var1 = this.worldObj.getCurrentDate(); if (var1.get(2) + 1
		 * == 10 && var1.get(5) == 31) this.dropItem(new
		 * ItemStack(IaSItems.nyxCandy,1,1)); if (var1.get(2) + 1 == 12 &&
		 * (var1.get(5) == 25 || var1.get(5) == 24)) this.dropItem(new
		 * ItemStack(IaSItems.nyxCandy,1,2));
		 */
	}

	public EntityItem dropItem(ItemStack par1ItemStack) {
		if (par1ItemStack.stackSize == 0) {
			return null;
		} else {
			final EntityItem entityitem = new EntityItem(this.worldObj,
					this.posX, this.posY, this.posZ, par1ItemStack);
			entityitem.delayBeforeCanPickup = 10;
			if (captureDrops) {
				capturedDrops.add(entityitem);
			} else {
				this.worldObj.spawnEntityInWorld(entityitem);
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

	public int getAttackStrength(Entity par1Entity) {
		final ItemStack var2 = this.getHeldItem();
		int var3;
		if (this.worldObj != null)
			var3 = IaSWorldHelper.getDifficulty(this.worldObj) >= 3 ? 7 : 8;
			else
				var3 = 8;

		if (var2 != null) {
			if (var2.getItem() instanceof IIaSTool)
				var3 += MathHelper.ceiling_float_int(IaSToolMaterial
						.extractMaterial(var2).getToolDamage(var2, this,
								par1Entity));
		}
		return var3;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		if (this.getAttackTarget() != null || this.getSearchTarget() != null)
			return 1;
		final int lightb = worldObj.getBlockLightValue(i, j, k);
		return lightb > 7 ? lightb / 2 : 0;
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.posY > 64.0F && super.getCanSpawnHere();
	}

	public ItemStack getDefaultAlternateWeapon(EnumNyxSkeletonType taipe) {
		if (taipe == EnumNyxSkeletonType.MAGIC_SHADOW)
			return new ItemStack(Items.bone);
		else
			return new ItemStack(NyxItems.frostSword,1,475+this.rand.nextInt(25));
	}

	public ItemStack getDefaultWeapon(EnumNyxSkeletonType taipe) {
		if (taipe == EnumNyxSkeletonType.KNIFE) {
			final ItemStack ait = IaSTools.setToolMaterial(IaSTools.knife,
					"Devora");
			ait.stackSize = new Random().nextInt(8) + 4;

			final int lvl = IaSWorldHelper.getRegionLevel(this);
			if (lvl >= 6) {
				ait.addEnchantment(Enchantment.knockback, 1);
				ait.addEnchantment(Enchantment.fireAspect, 1);
			}
			return ait;
		}
		if (taipe == EnumNyxSkeletonType.BOW_FROST_SHORT)
			return new ItemStack(NyxItems.frostBowShort, 1,
					383 - this.rand.nextInt(16));
		if (taipe == EnumNyxSkeletonType.MAGIC_SHADOW)
			return new ItemStack(NyxItems.boneCursed);
		if (taipe == EnumNyxSkeletonType.BOW_FROST_LONG)
			return new ItemStack(NyxItems.frostBowLong, 1,
					254 - this.rand.nextInt(16));
		if (taipe == EnumNyxSkeletonType.RAPIER)
			return new ItemStack(NyxItems.frostSword, 1, 45+this.rand.nextInt(50));
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
		return moveSpeed;
	}

	public EnumNyxSkeletonType getNyxSkeletonCombatType() {
		return typpe;
	}

	public ItemStack getReserveWeapon() {
		return this.reserveWeapon;
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
	public IaSSense getSense() {
		return senses;
	}

	public boolean isUsingAlternateWeapon() {
		return altWeaponFlag;
	}

	@Override
	public int getTotalArmorValue() {
		return super.getTotalArmorValue()+IaSWorldHelper.getRegionArmorMod(this);
	}

	@Override
	protected void jump() {
		super.jump();
		this.motionY = 1.2D * 0.41999998688697815D;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.worldObj.isRemote)
			return;
		if (!this.isPotionActive(Potion.hunger)) {
			if (--regenDelay <= 0) {
				this.heal(1);
				this.regenDelay = 30;
			}
		}
		if (this.getEquipmentInSlot(0) != null && !this.worldObj.isRemote) {
			final ItemStack is = this.getEquipmentInSlot(0);
			if (is.getItem() instanceof IaSItemThrowingKnife
					&& this.getAttackTarget() != null) {
				if (throwDelay <= 0) {
					final double dist = this.getDistanceSqToEntity(this
							.getAttackTarget());
					if (dist < 4 || dist > 100)
						return;
					if (!this.getEntitySenses().canSee(this.getAttackTarget()))
						return;
					final ItemStack projkni = is.copy();
					projkni.addEnchantment(Enchantment.sharpness, 1+this.worldObj.difficultySetting.getDifficultyId());
					final EntityThrowingKnife etn = new EntityThrowingKnife(
							this.worldObj, this, this.getAttackTarget(), 1.1F,
							2.0F, projkni);
					this.worldObj.playSoundAtEntity(this, "random.bow", 0.5F,
							0.75F);
					final IaSToolMaterial mat = IaSToolMaterial.extractMaterial(is);
					mat.onKnifeThrow(is, this, etn);
					this.worldObj.spawnEntityInWorld(etn);
					this.throwDelay = mat.getKnifeCooldown(is, this.worldObj,
							this) * 2;
				} else
					--throwDelay;
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData dat) {
		this.equipmentDropChances[0] = 0.0F;
		this.altWeaponFlag = false;
		
		if(this.getSkeletonType() == 1 || this.typpe != EnumNyxSkeletonType.RANDOM)
			return dat;
		
		final int dif = IaSWorldHelper.getDifficulty(worldObj);
		final int reg = IaSWorldHelper.getRegionLevel(this);
		
		// Knife skeleton.
		if (dif >= 2 && reg >= 3 && this.rand.nextInt(4) == 0) {
			setNyxSkeletonCombatType(EnumNyxSkeletonType.KNIFE);
			this.equipmentDropChances[0] = 0.33F;
		}

		// Special skeleton.
		else if (dif >= 2 && reg >= 1 && this.rand.nextInt(dif == 3 ? 8 : 12) == 0) {
			final ItemStack helm = new ItemStack(Items.leather_helmet);
			((ItemArmor) helm.getItem()).func_82813_b(helm, 0x773333);
			this.setCurrentItemOrArmor(4, helm);
			this.equipmentDropChances[4] = 0.0F;
			if (reg >= 5
					&& rand.nextBoolean()) {
				setNyxSkeletonCombatType(EnumNyxSkeletonType.BOW_FROST_LONG);
				this.equipmentDropChances[0] = 0.33F;
			} else {
				setNyxSkeletonCombatType(EnumNyxSkeletonType.MAGIC_SHADOW);
			}
		}

		// Bow skeleton.
		else {
			this.setCurrentItemOrArmor(0,
					this.getDefaultWeapon(EnumNyxSkeletonType.BOW_FROST_SHORT));
			setNyxSkeletonCombatType(EnumNyxSkeletonType.BOW_FROST_SHORT);
			this.equipmentDropChances[0] = 0.05F;
		}

		this.addRandomArmor();
		return dat;
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		if (par1NBTTagCompound.hasKey("NyxSkeletonCombatStyle"))
			this.setNyxSkeletonCombatType(par1NBTTagCompound
					.getByte("NyxSkeletonCombatStyle"));

		super.readEntityFromNBT(par1NBTTagCompound);

		this.setCombatTask();
	}

	@Override
	public void setCombatTask() {
		this.tasks.removeTask(rangedAttackDefault);
		this.tasks.removeTask(rangedAttackLong);
		this.tasks.removeTask(meleeAttackPlayer);
		this.tasks.removeTask(meleeAttackPassive);
		this.tasks.removeTask(shadowAttack);
		final ItemStack var1 = this.getHeldItem();

		if (var1 != null && var1.getItem() instanceof NyxItemBow) {
			if (this.typpe == EnumNyxSkeletonType.BOW_FROST_LONG) {
				this.tasks.addTask(4, rangedAttackLong);
			} else {
				this.tasks.addTask(4, rangedAttackDefault);
			}
		} else if (this.typpe == EnumNyxSkeletonType.MAGIC_SHADOW) {
			this.tasks.addTask(4, shadowAttack);
		} else {
			this.tasks.addTask(4, meleeAttackPlayer);
			this.tasks.addTask(4, meleeAttackPassive);
		}
	}

	@Override
	public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
		super.setCurrentItemOrArmor(par1, par2ItemStack);
		if (!this.worldObj.isRemote && par1 == 0)
			this.setCombatTask();
	}

	@Override
	public void setFire(int time) {
		if (this.dimension != IaSFlags.dim_nyx_id)
			super.setFire(time);
	}

	public void setNyxSkeletonCombatType(EnumNyxSkeletonType taipe) {
		typpe = taipe;
		this.setCurrentItemOrArmor(0, getDefaultWeapon(taipe));
		this.reserveWeapon = this.getDefaultAlternateWeapon(taipe);
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
		final ItemStack switche = this.getEquipmentInSlot(0);
		this.setCurrentItemOrArmor(0, reserveWeapon);
		reserveWeapon = switche;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByte("NyxSkeletonCombatStyle", this.typpe.id);
	}

}
