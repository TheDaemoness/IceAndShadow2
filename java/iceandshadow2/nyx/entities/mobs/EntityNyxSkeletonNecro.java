package iceandshadow2.nyx.entities.mobs;

import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseMovement;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseTouch;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseVision;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import iceandshadow2.util.IaSWorldHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNyxSkeletonNecro extends EntityNyxSkeleton {

	public EntityNyxSkeletonNecro(World par1World) {
		super(par1World);
		this.setSkeletonType(1);
		
		senses.clear();
		senses.add(new IaSSenseMovement(this, 12.0));
		senses.add(new IaSSenseTouch(this));
		senses.add(new IaSSenseVision(this, 16.0F));

		this.typpe = EnumNyxSkeletonType.MAGIC_SHADOW;
		this.experienceValue = 20;

        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityNyxSkeletonNecro.class, 24, false));
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
	public void doShadowAttack(EntityLivingBase par1EntityLiving, float par2) {
		final boolean harm_undead = par1EntityLiving.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
		final EntityThrowable entityball = new EntityShadowBall(this.worldObj,
				this, harm_undead, true);

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

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData dat) {
		final ItemStack helm = new ItemStack(Items.leather_helmet);
		((ItemArmor) helm.getItem()).func_82813_b(helm, 0x773333);
		this.setCurrentItemOrArmor(4, helm);
		this.equipmentDropChances[4] = 0.0F;
		return dat;
	}
}
