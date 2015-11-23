package iceandshadow2.nyx.entities.mobs;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.IaSFlags;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.ai.EntityAINyxTargeter;
import iceandshadow2.nyx.entities.ai.EntityAINyxWatchClosest;
import iceandshadow2.nyx.entities.ai.senses.IIaSSensate;
import iceandshadow2.nyx.entities.ai.senses.IaSSense;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseEnemy;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseMovement;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseTouch;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseVision;
import iceandshadow2.nyx.entities.ai.senses.IaSSetSenses;
import iceandshadow2.nyx.entities.projectile.EntityPoisonBall;
import iceandshadow2.nyx.entities.util.EntityWightTeleport;
import iceandshadow2.nyx.world.biome.NyxBiomeForestDense;
import iceandshadow2.nyx.world.biome.NyxBiomeForestSparse;
import iceandshadow2.nyx.world.biome.NyxBiomeInfested;
import iceandshadow2.util.IaSEntityHelper;
import iceandshadow2.util.IaSWorldHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityNyxWightToxic extends EntityZombie implements IIaSMobGetters, IIaSSensate {

	private EntityLivingBase searched;

	protected static double moveSpeed = 0.4;
	
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
		senses.add(new IaSSenseEnemy(this, 6.0F));
		senses.add(new IaSSenseVision(this, 3.0F));

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
		this.tasks.addTask(8, new EntityAINyxWatchClosest(this,
				EntityPlayer.class, 6.0F, 0.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityNyxSpider.class, 6, true));
        this.targetTasks.addTask(3, new EntityAINyxTargeter(this));
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
		.setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
		.setBaseValue(this.moveSpeed);
		this.getEntityAttribute(SharedMonsterAttributes.followRange)
		.setBaseValue(16.0);
		this.getEntityAttribute(field_110186_bp)
		.setBaseValue(0.0);
	}
	
	// To protect spoidahs.
	@Override
	public void addPotionEffect(PotionEffect eff) {
		if (eff.getPotionID() == Potion.poison.id)
			return;
		super.addPotionEffect(eff);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		final boolean flag = par1Entity.attackEntityFrom(
				DamageSource.causeThornsDamage(this), IaSWorldHelper.getDifficulty(worldObj));

		if (flag) {
			if (par1Entity instanceof EntityLivingBase && !this.worldObj.isRemote) {
				EntityLivingBase tox = (EntityLivingBase)par1Entity;
				tox.addPotionEffect(new PotionEffect(Potion.poison.id,
					75+IaSWorldHelper.getDifficulty(worldObj)*15,1));
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
				|| (par1DamageSource.isProjectile() && !par1DamageSource.isDamageAbsolute())
				|| par1DamageSource == DamageSource.drown)
			return false;
		boolean flag;
		if(par1DamageSource.isMagicDamage() && !par1DamageSource.isDamageAbsolute())
			par2 -= IaSWorldHelper.getRegionArmorMod(this);
		if(par1DamageSource.isDamageAbsolute())
			flag = super.attackEntityFrom(par1DamageSource, par2);
		else
			flag = super.attackEntityFrom(par1DamageSource, par2/2);
		return flag;
	}
	
	@Override
	protected void dropFewItems(boolean par1, int par2) {
		if (!par1)
			return;

		final int baite = this.rand.nextInt(6 + par2) - par2;
		if (baite <= 0)
			this.dropItem(NyxItems.toxicCore, 1);

		this.dropItem(NyxItems.resin,this.rand.nextInt(3)<par2-1?2:1);
	}

	@Override
	protected void dropRareDrop(int par1) {
		this.dropItem(Items.ender_pearl,2+this.rand.nextInt(2));
	}

	@Override
	protected void fall(float par1) {}

	@Override
	public boolean getCanSpawnHere() {
		return this.posY > 48.0F && super.getCanSpawnHere();
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return null;
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
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
		return;
	}
	
	@Override
    public boolean isOnLadder()
    {
        return this.isCollidedHorizontally;
    }

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.motionY = Math.max(-0.3, this.motionY);
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
				else if(!this.worldObj.isRemote) {
					List ents = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
							AxisAlignedBB.getBoundingBox(
									posX-TELEPORT_RANGE, posY-TELEPORT_RANGE, posZ-TELEPORT_RANGE,
									posX+TELEPORT_RANGE, posY+TELEPORT_RANGE, posZ+TELEPORT_RANGE));
					for(Object ent : ents) {
						if(ent instanceof EntityAgeable || ent instanceof EntityPlayer || ent instanceof EntityNyxSpider) {
							EntityPoisonBall pb = new EntityPoisonBall(worldObj,this);
							EntityLivingBase elb = (EntityLivingBase)ent;
							pb.setThrowableHeading(
									elb.posX-posX,
									elb.posY-posY+elb.getEyeHeight()-getEyeHeight(), 
									elb.posZ-posZ,
									1.0F, 1.0F);
							this.worldObj.spawnEntityInWorld(pb);
						}
					}
				}
			}
			this.heal(1);
			this.regenDelay = 35;
		}
	}
	
	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		if (this.getAttackTarget() != null || this.getSearchTarget() != null)
			return 1;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof NyxBiomeInfested)
			return 1;
		else if (biome instanceof NyxBiomeForestDense || biome instanceof NyxBiomeForestSparse)
			return 15;
		return 3;
	}
	
	public void teleportAt(Entity target) {
		target.worldObj.playSoundEffect(posX, posY, posZ, "mob.endermen.portal", 0.7F,
				0.7F + target.worldObj.rand.nextFloat() * 0.1F);
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
