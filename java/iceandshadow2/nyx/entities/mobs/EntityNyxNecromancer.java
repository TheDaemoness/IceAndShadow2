package iceandshadow2.nyx.entities.mobs;

import java.util.List;

import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.items.tools.IaSItemThrowingKnife;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseMovement;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseTouch;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseVision;
import iceandshadow2.nyx.entities.cosmetic.EntityCosmeticShadowRiser;
import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton.EnumNyxSkeletonType;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import iceandshadow2.nyx.entities.projectile.EntityThrowingKnife;
import iceandshadow2.nyx.items.tools.NyxItemBow;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.util.IaSWorldHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNyxNecromancer extends EntityNyxSkeleton {

	public EntityNyxNecromancer(World par1World) {
		super(par1World);
		this.setSkeletonType(1);

		senses.clear();
		senses.add(new IaSSenseMovement(this, 12.0));
		senses.add(new IaSSenseTouch(this));
		senses.add(new IaSSenseVision(this, 16.0F));

		this.typpe = EnumNyxSkeletonType.RANDOM;
		this.experienceValue = 20;

		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityNyxNecromancer.class, 24, false));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
		.setBaseValue(getScaledMaxHealth());
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
		.setBaseValue(0.5);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
		.setBaseValue(EntityNyxSkeleton.moveSpeed-0.2);
		this.getEntityAttribute(SharedMonsterAttributes.followRange)
		.setBaseValue(24.0);
	}


	@Override
	public void setCombatTask() {
		this.tasks.removeTask(rangedAttackDefault);
		this.tasks.removeTask(rangedAttackLong);
		this.tasks.removeTask(meleeAttackPlayer);
		this.tasks.removeTask(meleeAttackPassive);
		this.tasks.removeTask(shadowAttack);
		this.tasks.addTask(4, shadowAttack);
	}

	@Override
	public double getScaledMaxHealth() {
		return 70.0;
	}

	@Override
	protected void addRandomArmor() {
		return;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		if (!par1 || this.worldObj.isRemote)
			return;

		if(this.rand.nextInt(4-(IaSWorldHelper.getDifficulty(this.worldObj)>=3?1:0)) == 0)
			this.dropItem(NyxItems.nifelhiumPowder, 1);
		else if(IaSWorldHelper.getDifficulty(this.worldObj)<3)
			this.dropItem(NyxItems.boneCursed, 1);
		if(IaSWorldHelper.getDifficulty(this.worldObj)>=3)
			this.dropItem(NyxItems.boneCursed, 1);
	}

	@Override
	protected void dropRareDrop(int par1) {
		this.dropItem(NyxItems.bloodstone, 1);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase sucker, float par2) {
		if(this.worldObj.isRemote)
			return;
		EntityCosmeticShadowRiser ecsr = new EntityCosmeticShadowRiser(this.worldObj);
		ecsr.setPosition(posX, posY+this.getEyeHeight(), posZ);
		this.worldObj.spawnEntityInWorld(ecsr);
		final boolean harm_undead = sucker.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(
				sucker.posX-8.0, sucker.posY-16.0, sucker.posZ-8.0,
				sucker.posX+8.0, sucker.posY+16.0, sucker.posZ+8.0);
		if(!harm_undead) {
			List li = worldObj.getEntitiesWithinAABBExcludingEntity(this, bb);
			for(Object obj : li) {
				if(obj instanceof EntityNyxSkeleton) {
					final EntityThrowable entityball = new EntityShadowBall(this.worldObj,
							this, false, true);
					Entity target = (Entity)obj;
					entityball.setPosition(target.posX, target.posY+32+worldObj.rand.nextFloat()*32, target.posZ);
					entityball.setThrowableHeading(0, -1, 0, 0.40F, 0F);
					this.worldObj.spawnEntityInWorld(entityball);
				}
			}
		}
		final EntityThrowable entitysmite = new EntityShadowBall(this.worldObj,
				this, harm_undead, true);
		entitysmite.setPosition(sucker.posX, sucker.posY+32+worldObj.rand.nextFloat()*32, sucker.posZ);
		entitysmite.setThrowableHeading(0, -1, 0, 0.40F, 0F);
		this.worldObj.spawnEntityInWorld(entitysmite);
	}



	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.worldObj.isRemote)
			return;
		if (!this.isPotionActive(Potion.hunger) && this.getAttackTarget() != null) {
			int count = 0;
			Entity sucker = this.getAttackTarget();
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(
					posX-12.0, posY-16.0, posZ-12.0,
					posX+12.0, posY+16.0, posZ+12.0);
			final boolean threat = sucker instanceof EntityPlayer && sucker.getDistanceSqToEntity(this) < 256;
			final boolean alone = worldObj.getEntitiesWithinAABB(EntityNyxSkeleton.class, bb).size() == 1;
			if(threat || alone) {
				List li = worldObj.getEntitiesWithinAABB(EntityPlayer.class, bb);
				li.add(sucker);
				for(; count < IaSWorldHelper.getDifficulty(worldObj) && getHealth() > 6 && !li.isEmpty(); 
						++count) {
					EntityMob summon;
					if(this.rand.nextInt(3) == 0)
						summon = new EntityNyxSkeleton(this.worldObj,EnumNyxSkeletonType.MAGIC_SHADOW);
					else
						summon = new EntityNyxSkeleton(this.worldObj,EnumNyxSkeletonType.RAPIER);
					summon.setPosition(this.posX, this.posY, this.posZ);
					summon.setAttackTarget((EntityLivingBase)li.get(0));
					li.remove(0);
					this.worldObj.playSoundAtEntity(this, "mob.wither.shoot",
						0.5F, 0.33F + 0.33F * this.rand.nextFloat());
					
					if(this.worldObj.spawnEntityInWorld(summon))
						this.worldObj.playSoundAtEntity(summon,
								"mob.silverfish.kill", 0.7F,
								0.33F + 0.33F * this.rand.nextFloat());
					this.heal(-5);
				}
				if(count > 0)
					this.addPotionEffect(new PotionEffect(Potion.hunger.id,85,0));
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData dat) {
		final ItemStack helm = new ItemStack(Items.leather_helmet);
		((ItemArmor) helm.getItem()).func_82813_b(helm, 0x331111);
		this.setCurrentItemOrArmor(4, helm);
		this.equipmentDropChances[4] = 0.0F;
		return dat;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float dmg) {
		this.addPotionEffect(new PotionEffect(Potion.hunger.id, 25, 1));
		if(par1DamageSource.isMagicDamage())
			dmg /= 2;
		return super.attackEntityFrom(par1DamageSource, dmg);
	}


}
