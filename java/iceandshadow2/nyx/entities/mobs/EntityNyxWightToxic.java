package iceandshadow2.nyx.entities.mobs;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.IaSFlags;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.ai.EntityAINyxTargeter;
import iceandshadow2.nyx.entities.ai.senses.IIaSSensate;
import iceandshadow2.nyx.entities.ai.senses.IaSSense;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseMovement;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseTouch;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseVision;
import iceandshadow2.nyx.entities.ai.senses.IaSSetSenses;
import iceandshadow2.nyx.entities.util.EntityWightTeleport;
import iceandshadow2.util.IaSEntityHelper;
import iceandshadow2.util.IaSWorldHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
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
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNyxWightToxic extends EntityZombie implements IIaSMobGetters, IIaSSensate {

	private EntityLivingBase searched;

	protected static double moveSpeed = 0.25;
	
	protected int regenDelay;
	protected IaSSetSenses senses;
	
	private static double TELEPORT_RANGE = 8.0F;

	public EntityNyxWightToxic(World par1World) {
		super(par1World);

		this.experienceValue = 25;
		this.regenDelay = 0;

		senses = new IaSSetSenses(this);
		senses.add(new IaSSenseMovement(this, 24.0));
		senses.add(new IaSSenseTouch(this));
		senses.add(new IaSSenseVision(this, 4.0F));

		this.tasks.taskEntries.clear();
		this.targetTasks.taskEntries.clear();

		this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityAnimal.class, 1.2D, true));
		this.tasks.addTask(5, new EntityAIFleeSun(this,
				EntityNyxWightToxic.moveSpeed + 0.5));
        //this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this,
				EntityNyxWightToxic.moveSpeed));
		this.tasks.addTask(8, new EntityAIWatchClosest(this,
				EntityPlayer.class, 6.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINyxTargeter(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityAnimal.class, 0, false));
	}

	@Override
	protected void addRandomArmor() {
		return;
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
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
		.setBaseValue(getScaledMaxHealth());
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
		.setBaseValue(0.75D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
		.setBaseValue(EntityNyxWightToxic.moveSpeed);
		this.getEntityAttribute(SharedMonsterAttributes.followRange)
		.setBaseValue(16.0);
		this.getEntityAttribute(field_110186_bp)
		.setBaseValue(0.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		float f = this.getAttackStrength(par1Entity);
		int i = 0;

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

			if (par1Entity instanceof EntityLivingBase && !this.worldObj.isRemote) {
				EntityLivingBase tox = (EntityLivingBase)par1Entity;
				tox.addPotionEffect(new PotionEffect(Potion.poison.id,
					75+IaSWorldHelper.getDifficulty(worldObj)*30,0));
			}
		}

		return flag;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if (this.isEntityInvulnerable()
				|| !par1DamageSource.isUnblockable()
				|| par1DamageSource == DamageSource.drown)
			return false;
		boolean flag;
		if(par1DamageSource.isMagicDamage() && !par1DamageSource.isDamageAbsolute())
			par2 -= IaSWorldHelper.getRegionArmorMod(this);
		if(par1DamageSource.isDamageAbsolute() && !this.isInvisible())
			flag = super.attackEntityFrom(par1DamageSource, par2);
		else
			flag = super.attackEntityFrom(par1DamageSource, par2/2);
		if(par1DamageSource instanceof EntityDamageSource && !par1DamageSource.isProjectile()
				&& !par1DamageSource.isMagicDamage()
				&& !par1DamageSource.isExplosion()
				&& !par1DamageSource.isFireDamage()) {
			Entity ent = ((EntityDamageSource)par1DamageSource).getEntity();
			if(ent instanceof EntityPlayer)
				((EntityPlayer)ent).dropOneItem(false);
		}
		return flag;
	}
	
	@Override
	protected void dropFewItems(boolean par1, int par2) {
		if (!par1)
			return;

		final int baite = this.rand.nextInt(6 + par2) - par2;
		if (baite <= 0)
			this.dropItem(Items.ender_pearl, 1);

		 this.dropItem(NyxItems.resin,this.rand.nextInt(3)<par2-1?2:1);
	}

	@Override
	protected void dropRareDrop(int par1) {
		this.dropItem(NyxItems.toxicCore,1);
	}

	@Override
	protected void fall(float par1) {}

	public int getAttackStrength(Entity par1Entity) {
		final ItemStack var2 = this.getHeldItem();
		int var3;
		if (this.worldObj != null)
			var3 = IaSWorldHelper.getDifficulty(this.worldObj) >= 3 ? 8 : 9;
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
	public boolean getCanSpawnHere() {
		return this.posY > 48.0F && super.getCanSpawnHere();
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		if(this.getAttackTarget() != null)
			return "IceAndShadow2:mob_nyxghoul_idle";
		return null;
	}

	@Override
	protected String getHurtSound() {
		return "IceAndShadow2:mob_nyxghoul_hurt";
	}

	@Override
	protected String getDeathSound() {
		return "IceAndShadow2:mob_nyxghoul_death";
	}
	
	@Override
	protected void func_145780_a(int p_145780_1_, int p_145780_2_,
			int p_145780_3_, Block p_145780_4_) {
	}

	@Override
	public void onKillEntity(EntityLivingBase misnomer) {
		this.heal(misnomer.getMaxHealth()/2);
		super.onKillEntity(misnomer);
	}

	@Override
	public double getMoveSpeed() {
		return moveSpeed;
	}

	@Override
	public double getScaledMaxHealth() {
		if(this.worldObj == null)
			return 25.0D;
		return 5.0D+IaSWorldHelper.getDifficulty(this.worldObj)*10;
	}

	@Override
	public EntityLivingBase getSearchTarget() {
		return searched;
	}
	
	@Override
	protected void jump() {
		super.jump();
		this.motionY = 1.2D * 0.41999998688697815D;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.motionY = Math.max(-0.1, this.motionY);
		boolean attacking = this.getAttackTarget() != null;
		if (--regenDelay <= 0) {
			if(IaSWorldHelper.getDifficulty(this.worldObj) <= 1) {
				if (!this.worldObj.isRemote)
					this.setDead();
				return;
			}
			if(attacking) {
				if(this.getDistanceSqToEntity(this.getAttackTarget()) > TELEPORT_RANGE*TELEPORT_RANGE)
					teleportAt(this.getAttackTarget());
			}
			this.heal(1);
			this.regenDelay = 15;
		}
	}
	
	public void teleportAt(Entity target) {
		if (this.worldObj.isRemote)
			return;
		EntityWightTeleport wt;
		if(target instanceof EntityLivingBase)
			wt = new EntityWightTeleport(this.worldObj,this,(EntityLivingBase)target);
		else
			wt = new EntityWightTeleport(this.worldObj,this);
		final double d0 = target.posX + target.motionX
				- this.posX;
		final double d1 = target.posY
				+ target.getEyeHeight() - this.getEyeHeight()
				- this.posY;
		final double d2 = target.posZ + target.motionZ
				- this.posZ;

		wt.setThrowableHeading(d0, d1, d2, 1, 2.0F);
		worldObj.spawnEntityInWorld(wt);
		this.setDead();
	}

	@Override
	public int getTotalArmorValue() {
		return super.getTotalArmorValue()+IaSWorldHelper.getRegionArmorMod(this);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData dat) {
		this.equipmentDropChances[0] = 0.0F;
		
		final int dif = IaSWorldHelper.getDifficulty(worldObj);
		return dat;
	}

	@Override
	public void setFire(int time) {
		if (this.dimension != IaSFlags.dim_nyx_id)
			super.setFire(time);
	}

	@Override
	public void setInWeb() {
		//Nope.
	}

	@Override
	public void setSearchTarget(EntityLivingBase ent) {
		searched = ent;
	}

	@Override
	public IaSSense getSense() {
		return senses;
	}

}
