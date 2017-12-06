package iceandshadow2.nyx.entities.mobs;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.IaSFlags;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.ai.EntityAINyxRevenge;
import iceandshadow2.nyx.entities.ai.EntityAINyxTargeter;
import iceandshadow2.nyx.entities.ai.EntityAINyxWatchClosest;
import iceandshadow2.nyx.entities.ai.IIaSBlockPathDesirability;
import iceandshadow2.nyx.entities.ai.senses.*;
import iceandshadow2.nyx.entities.projectile.EntityPoisonBall;
import iceandshadow2.nyx.entities.util.EntityOrbNourishment;
import iceandshadow2.nyx.entities.util.EntityWightTeleport;
import iceandshadow2.nyx.world.biome.NyxBiomeForestDense;
import iceandshadow2.nyx.world.biome.NyxBiomeForestSparse;
import iceandshadow2.nyx.world.biome.NyxBiomeInfested;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.util.IaSEntityHelper;
import iceandshadow2.util.IaSWorldHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityNyxWightToxic extends EntityZombie implements IIaSMobGetters {

	protected static double moveSpeed = 0.4;

	private static double TELEPORT_RANGE = 8.0F;

	private EntityLivingBase searched;
	protected int regenDelay;

	protected IaSSetSenses senses;

	public EntityNyxWightToxic(World par1World) {
		super(par1World);

		this.stepHeight = 0.0f;

		this.experienceValue = 15;
		this.regenDelay = 15;

		this.senses = new IaSSetSenses(this);
		this.senses.add(new IaSSenseMovement(this, 24.0));
		this.senses.add(new IaSSenseActions(this, 16.0));
		this.senses.add(new IaSSenseTouch(this));
		this.senses.add(new IaSSenseAura(this, 5.0F));
		this.senses.add(new IaSSensePoisoned(this, 9.0F));

		this.tasks.taskEntries.clear();
		this.targetTasks.taskEntries.clear();

		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.8D, true));
		this.tasks.addTask(3, new EntityAIFleeSun(this, EntityNyxWightToxic.moveSpeed + 0.5));
		// this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this,
		// 1.0D));
		this.tasks.addTask(5, new EntityAIWander(this, EntityNyxWightToxic.moveSpeed));
		this.tasks.addTask(6, new EntityAINyxWatchClosest(this, EntityPlayer.class, 6.0F, 0.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINyxRevenge(this));
		this.targetTasks.addTask(2, new EntityAINyxTargeter(this));
	}

	// To protect spoidahs.
	@Override
	public void addPotionEffect(PotionEffect eff) {
		if (eff.getPotionID() == Potion.poison.id)
			return;
		super.addPotionEffect(eff);
	}

	@Override
	protected void addRandomArmor() {
		return;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getScaledMaxHealth());
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(EntityNyxWightToxic.moveSpeed);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
		getEntityAttribute(EntityZombie.field_110186_bp).setBaseValue(0.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		int dmg = 2 * IaSWorldHelper.getDifficulty(this.worldObj) - 1;
		if (par1Entity instanceof EntityLivingBase && !this.worldObj.isRemote) {
			final EntityLivingBase tox = (EntityLivingBase) par1Entity;
			final PotionEffect pot = tox.getActivePotionEffect(Potion.poison);
			if (pot != null)
				dmg += 9 + pot.getAmplifier() * dmg;
			if (tox.attackEntityFrom(DamageSource.causeIndirectMagicDamage(tox, this), dmg)) {
				if (pot != null) {
					this.worldObj.playSoundAtEntity(this, "IceAndShadow2:mob_nyxwight_toxic_attack_brutal", 0.5F,
							this.rand.nextFloat() * 0.15F + 0.85F);
					tox.removePotionEffect(Potion.poison.id);
					heal(getMaxHealth());
					if (tox.getHealth() > 0)
						teleportAway(par1Entity);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if (par1DamageSource.isProjectile() && !par1DamageSource.isDamageAbsolute())
			return super.attackEntityFrom(par1DamageSource, 1);
		boolean flag;
		if (par1DamageSource.isMagicDamage() && !par1DamageSource.isDamageAbsolute())
			par2 -= IaSWorldHelper.getRegionArmorMod(this);
		if (par1DamageSource.isDamageAbsolute())
			flag = super.attackEntityFrom(par1DamageSource, par2 * 1.5F);
		else
			flag = super.attackEntityFrom(par1DamageSource, par2);
		return flag;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean couldFlyFasterWithBoots() {
		return true;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		if (!par1)
			return;

		final int diff = IaSWorldHelper.getDifficulty(this.worldObj);
		final int baite = this.rand.nextInt(8 + par2) - par2 - diff;

		if (baite <= 0)
			IaSEntityHelper.dropItem(this, new ItemStack(NyxItems.toxicCore, 1, 1));

		dropItem(NyxItems.resin, (this.rand.nextInt(diff + par2) > 1 ? 2 : 1));

		this.worldObj.spawnEntityInWorld(new EntityOrbNourishment(this.worldObj, this.posX, this.posY, this.posZ, 2));
	}

	@Override
	protected void dropRareDrop(int par1) {
		dropItem(Items.ender_pearl, 2 + this.rand.nextInt(2));
	}

	@Override
	protected void fall(float par1) {
	}

	@Override
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		if (getAttackTarget() != null || getSearchTarget() != null)
			return 1;
		final BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof NyxBiomeInfested)
			return -3;
		else if (biome instanceof NyxBiomeForestDense || biome instanceof NyxBiomeForestSparse)
			return 15;
		float mod = 0F;
		final Block bl = this.worldObj.getBlock(i, j, k);
		if (bl instanceof IIaSBlockPathDesirability)
			mod = ((IIaSBlockPathDesirability) bl).getBlockPathWeight(this.worldObj, i, j, k);
		return mod + super.getBlockPathWeight(i, j, k);
	}

	@Override
	public float getBrightness(float par1) {
		return super.getBrightness(par1) * 0.5F + 0.5F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender(float par1) {
		return super.getBrightnessForRender(par1) / 2 + Integer.MAX_VALUE / 2;
	}

	@Override
	public boolean getCanSpawnHere() {
		for (int x = -16; x <= 16; ++x) {
			for (int y = -4; y <= 4; ++y) {
				for (int z = -16; z <= 16; ++z) {
					if (IaSEntityHelper.getBlock(this, x, y, z) == NyxBlocks.poisonLeaves) {
				        List list = this.worldObj.getEntitiesWithinAABB(
				        		this.getClass(),
				        		AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(16, 12, 16));
						return list.size() < (1+IaSWorldHelper.getRegionLevel(this)/2) && this.posY > 64.0F && super.getCanSpawnHere();
					}
				}
			}
		}
		return false;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	protected String getHurtSound() {
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
		return EntityNyxWightToxic.moveSpeed;
	}

	@Override
	public double getScaledMaxHealth() {
		if (this.worldObj == null)
			return 35.0D;
		return 15.0D + IaSWorldHelper.getDifficulty(this.worldObj) * 10;
	}

	@Override
	public EntityLivingBase getSearchTarget() {
		return this.searched;
	}

	@Override
	public IaSSense getSense() {
		return this.senses;
	}

	@Override
	public int getTotalArmorValue() {
		return super.getTotalArmorValue() + IaSWorldHelper.getRegionArmorMod(this);
	}

	@Override
	public boolean isOnLadder() {
		return this.isCollidedHorizontally;
	}

	@Override
	protected void jump() {
		return;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.motionY = Math.max(-0.3, this.motionY);
		Random rand = this.worldObj.rand;
		if (this.worldObj.isRemote) {
			IaSFxManager.spawnParticle(this.worldObj, rand.nextBoolean() ? "poisonSmoke" : "shadowSmokeSmall",
					this.posX - (0.5 - rand.nextDouble()) / 4, this.posY + rand.nextDouble(),
					this.posZ - (0.5 - rand.nextDouble()) / 4, 0.0, 0.0, 0.0, false, true);
		}
		final boolean attacking = getAttackTarget() != null;
		if (--this.regenDelay <= 0) {
			if (IaSWorldHelper.getDifficulty(this.worldObj) <= 1) {
				if (!this.worldObj.isRemote)
					setDead();
				return;
			}
			if (attacking) {
				final double range = EntityNyxWightToxic.TELEPORT_RANGE * EntityNyxWightToxic.TELEPORT_RANGE;
				final boolean poisoned = getAttackTarget().isPotionActive(Potion.poison);
				if (getDistanceSqToEntity(getAttackTarget()) > range / (poisoned ? 2 : 1)) {
					teleportAt(getAttackTarget());
				} else if (!this.worldObj.isRemote) {
					final List ents = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
							AxisAlignedBB.getBoundingBox(this.posX - EntityNyxWightToxic.TELEPORT_RANGE,
									this.posY - EntityNyxWightToxic.TELEPORT_RANGE*2,
									this.posZ - EntityNyxWightToxic.TELEPORT_RANGE,
									this.posX + EntityNyxWightToxic.TELEPORT_RANGE,
									this.posY + EntityNyxWightToxic.TELEPORT_RANGE,
									this.posZ + EntityNyxWightToxic.TELEPORT_RANGE));
					for (final Object ent : ents) {
						if (ent instanceof EntityAgeable || ent instanceof EntityPlayer
								|| EnumIaSAspect.getAspect(ent) == EnumIaSAspect.INFESTATION) {
							final EntityLivingBase elb = (EntityLivingBase) ent;
							if ((elb == this.getAttackTarget() && this.getHealth() >= this.getMaxHealth())
									|| elb.isPotionActive(Potion.poison))
								continue;
							final EntityPoisonBall pb = new EntityPoisonBall(this.worldObj, this);
							pb.setThrowableHeading(elb.posX - this.posX,
									elb.posY - this.posY + elb.getEyeHeight() - getEyeHeight(), elb.posZ - this.posZ,
									1.0F, 1.0F);
							this.worldObj.spawnEntityInWorld(pb);
						}
					}
				}
			}
			heal(1);
			this.regenDelay = (IaSWorldHelper.getDifficulty(worldObj) >= 3 ? 20 : 35);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData dat) {
		this.equipmentDropChances[0] = 0.0F;

		IaSWorldHelper.getDifficulty(this.worldObj);
		return dat;
	}

	@Override
	public void setAttackTarget(EntityLivingBase ent) {
		// NOTE: Leaving this seperate as future checks may assume that ent is
		// non-null.
		if (ent == null || ent == this.getAttackTarget()) {
			super.setAttackTarget(ent);
			return;
		}
		boolean op = false;
		if (ent instanceof EntityNyxSpider)
			op = true;
		else if (this.getHealth() < this.getMaxHealth())
			op = true;
		for (int x = -8; !op && x <= 8; ++x) {
			for (int y = -8; !op && y <= 8; ++y) {
				for (int z = -8; !op && z <= 8; ++z) {
					if (IaSEntityHelper.getBlock(ent, x, y, z) == NyxBlocks.poisonLog) {
						op = true;
					}
				}
			}
		}
		if (op)
			super.setAttackTarget(ent);
	}

	@Override
	public void setFire(int time) {
		if (this.dimension != IaSFlags.dim_nyx_id)
			super.setFire(time);
	}

	@Override
	public void setInWeb() {
		// Nope.
	}

	@Override
	public void setSearchTarget(EntityLivingBase ent) {
		this.searched = ent;
	}

	public void teleportAt(Entity target) {
		target.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mob.endermen.portal", 0.7F,
				0.7F + target.worldObj.rand.nextFloat() * 0.1F);
		if (this.worldObj.isRemote)
			return;
		EntityWightTeleport wt;
		if (target instanceof EntityLivingBase)
			wt = new EntityWightTeleport(this.worldObj, this, (EntityLivingBase) target);
		else
			wt = new EntityWightTeleport(this.worldObj, this);
		final double d0 = target.posX + target.motionX - this.posX;
		final double d1 = target.posY + target.getEyeHeight() - getEyeHeight() - this.posY;
		final double d2 = target.posZ + target.motionZ - this.posZ;

		wt.setThrowableHeading(d0, d1, d2, 1, 2.0F);
		this.worldObj.spawnEntityInWorld(wt);
		setDead();
	}

	public void teleportAway(Entity target) {
		target.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mob.endermen.portal", 0.7F,
				0.7F + target.worldObj.rand.nextFloat() * 0.1F);
		if (this.worldObj.isRemote)
			return;
		EntityWightTeleport wt;
		if (target instanceof EntityLivingBase)
			wt = new EntityWightTeleport(this.worldObj, this, (EntityLivingBase) target);
		else
			wt = new EntityWightTeleport(this.worldObj, this);
		final double d0 = target.posX + target.motionX - this.posX;
		final double d1 = target.posY + target.getEyeHeight() - getEyeHeight() - this.posY;
		final double d2 = target.posZ + target.motionZ - this.posZ;

		wt.setThrowableHeading(-d0, -d1, -d2, 0.5F, 20.0F);
		this.worldObj.spawnEntityInWorld(wt);
		setDead();
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.POISONWOOD;
	}

	@Override
	public boolean hates(EnumIaSAspect aspect) {
		return aspect == null || aspect == EnumIaSAspect.INFESTATION || aspect == EnumIaSAspect.ANCIENT;
	}
}
