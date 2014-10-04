package iceandshadow2.nyx.entities.mobs;

import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.items.tools.IaSItemArmor;
import iceandshadow2.ias.items.tools.IaSItemTool;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.ai.EntityAINyxRangedAttack;
import iceandshadow2.nyx.entities.ai.EntityAINyxSkeletonWeaponSwitch;
import iceandshadow2.nyx.entities.ai.EntityAINyxTargeter;
import iceandshadow2.nyx.entities.ai.senses.IIaSSensate;
import iceandshadow2.nyx.entities.ai.senses.IaSSense;
import iceandshadow2.nyx.entities.ai.senses.IaSSenseVision;
import iceandshadow2.nyx.entities.ai.senses.IaSSetSenses;
import iceandshadow2.nyx.entities.projectile.EntityIceArrow;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import iceandshadow2.util.IaSWorldHelper;

import com.ibm.icu.util.Calendar;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityNyxSkeleton extends EntitySkeleton implements IIaSSensate {
	
	public enum EnumNyxSkeletonType {
		BOW_FROST_SHORT(0),
		MELEE(1),
		MAGIC_SHADOW(2),
		BOW_FROST_LONG(3),
		KNIFE_DEVORA(4);
		
		public byte id;
		EnumNyxSkeletonType(int aidee) {
			id = (byte)aidee;
		}
		public static EnumNyxSkeletonType fromId(int id) {
			for(EnumNyxSkeletonType t : values()) {
				if(t.id == (byte)id)
					return t;
			}
			return null;
		}
	}

    protected EntityAINyxRangedAttack rangedAttackShort = new EntityAINyxRangedAttack(this, this.moveSpeed+0.25, 15, 25, 24.0F);
    protected EntityAINyxRangedAttack rangedAttackLong = new EntityAINyxRangedAttack(this, this.moveSpeed, 25, 35, 32.0F);
    protected EntityAIAttackOnCollide meleeAttackPlayer = new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed+0.5, false);
    protected EntityAIAttackOnCollide meleeAttackPassive = new EntityAIAttackOnCollide(this, EntityAgeable.class, this.moveSpeed+0.5, true);
	protected EntityAINyxRangedAttack shadowAttack = new EntityAINyxRangedAttack(this, this.moveSpeed+0.25, 35, 45, 12.0F);
	protected EntityAINyxRangedAttack knifeAttack = new EntityAINyxRangedAttack(this, this.moveSpeed+0.25, 10, 15, 12.0F);

	protected IaSSetSenses senses;
	
    /** Probability to get armor */
    protected static float[] nyxSkeletonArmorProbability = new float[] {0.0F, 0.01F, 0.03F, 0.09F};
    
    protected EnumNyxSkeletonType typpe;
    protected boolean altWeaponFlag;
    protected ItemStack reserveWeapon;

	protected static double moveSpeed = 0.5;
	
	public EntityNyxSkeleton(World par1World) {    
		super(par1World);
		
		senses = new IaSSetSenses(this);
		senses.add(new IaSSenseVision(this,24.0));
		
        this.setSkeletonType(0);
        this.experienceValue = 7;
        
        this.tasks.taskEntries.clear();
        this.targetTasks.taskEntries.clear();
        
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAINyxSkeletonWeaponSwitch(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, this.moveSpeed+0.5));
        this.tasks.addTask(5, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINyxTargeter(this, 24.0));
        
        if (par1World != null && !par1World.isRemote)
            this.setCombatTask();
        
        this.typpe = EnumNyxSkeletonType.BOW_FROST_SHORT;
	}
    
	@Override
    protected void dropRareDrop(int par1) {
		//this.dropItem(NyxItems.nyxEscapeBomb.itemID,1);
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
	@Override
    protected void dropFewItems(boolean par1, int par2)
    {
    	if(!par1)
    		return;
    	
    	this.dropItem(Items.bone, 1);
    
    	/*
    	Calendar var1 = this.worldObj.getCurrentDate();
        if (var1.get(2) + 1 == 10 && var1.get(5) == 31)
        	this.dropItem(new ItemStack(IaSItems.nyxCandy,1,1));
        if (var1.get(2) + 1 == 12 && (var1.get(5) == 25 || var1.get(5) == 24))
        	this.dropItem(new ItemStack(IaSItems.nyxCandy,1,2));
    	*/
    }
    
    public EntityItem dropItem(ItemStack par1ItemStack)
    {
        if (par1ItemStack.stackSize == 0)
        {
            return null;
        }
        else
        {
            EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, par1ItemStack);
            entityitem.delayBeforeCanPickup = 10;
            if (captureDrops)
            {
                capturedDrops.add(entityitem);
            }
            else
            {
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            return entityitem;
        }
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k)
    {
    	int lightb = worldObj.getBlockLightValue(i, j, k);
    	return (lightb>7?(lightb/2):0);
    }
    
    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound()
    {
        return "";
    }

    @Override
    protected void fall(float par1) {
    	super.fall(par1/3.0F);
	}

    @Override
    public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
    {
        super.setCurrentItemOrArmor(par1, par2ItemStack);
        if (!this.worldObj.isRemote && par1 == 0)
            this.setCombatTask();
    }
    
    @Override
    public void setCombatTask()
    {
        this.tasks.removeTask(rangedAttackShort);
        this.tasks.removeTask(rangedAttackLong);
        this.tasks.removeTask(meleeAttackPlayer);
        this.tasks.removeTask(meleeAttackPassive);
        this.tasks.removeTask(shadowAttack);
        ItemStack var1 = this.getHeldItem();

        if (var1 != null && var1.getItem() instanceof ItemBow) {
        	if(this.typpe == EnumNyxSkeletonType.BOW_FROST_LONG)
        		this.tasks.addTask(4, rangedAttackLong);
        	else
                this.tasks.addTask(4, rangedAttackShort);
        }
        else if (this.typpe == EnumNyxSkeletonType.MAGIC_SHADOW) {
            this.tasks.addTask(4, shadowAttack);
        }
        else {
            this.tasks.addTask(4, meleeAttackPlayer);
            this.tasks.addTask(4, meleeAttackPassive);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        float f = (float)this.getAttackStrength(par1Entity);
        int i = 0;

        if (par1Entity instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)par1Entity);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)par1Entity);
        }

        boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), f*2 + 4);
        
        if (flag)
        {
            if (i > 0)
            {
                par1Entity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
                par1Entity.setFire(j * 4);

            if (par1Entity instanceof EntityLivingBase)
            {
                int weakentime = (this.worldObj.difficultySetting.getDifficultyId())*7;
                ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.weakness.id, weakentime * 20, IaSWorldHelper.getDifficulty(this.worldObj)-1));
            }
        }

        return flag;
    }
    
    public int getAttackStrength(Entity par1Entity)
    {
    	ItemStack var2 = this.getHeldItem();
    	int var3;
    	if(this.worldObj != null)
    		var3 = (IaSWorldHelper.getDifficulty(this.worldObj) > 2?7:8);
    	else
    		var3 = 8;

	    if (var2 != null) {
	    	if(var2.getItem() instanceof IaSItemTool)
	    		var3 += MathHelper.ceiling_float_int(IaSToolMaterial.extractMaterial(var2).getToolDamage(var2, this, par1Entity));
	    }
        return var3;
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (this.isEntityInvulnerable() || par1DamageSource == DamageSource.drown)
            return false;
        if (par1DamageSource.isFireDamage())
        	return super.attackEntityFrom(par1DamageSource, par2*3);
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLiving, float par2) {
    	ItemStack wielding = this.getHeldItem();
    	if(wielding.getItem() instanceof ItemBow)
    		doBowAttack(par1EntityLiving, par2);
    	else {
    		doShadowAttack(par1EntityLiving, par2);
    		if(typpe != EnumNyxSkeletonType.MAGIC_SHADOW)
    			this.attackEntityFrom(DamageSource.magic, 6.0F);
    	}
    }
    
    public void doShadowAttack(EntityLivingBase par1EntityLiving, float par2) {
		boolean harm_undead = par1EntityLiving.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
		EntityThrowable entityball = new EntityShadowBall(this.worldObj, this, harm_undead, false);

		double d0 = par1EntityLiving.posX + par1EntityLiving.motionX
				- this.posX;
		double d1 = par1EntityLiving.posY
				+ (double) par1EntityLiving.getEyeHeight() - this.getEyeHeight()
				- this.posY;
		double d2 = par1EntityLiving.posZ + par1EntityLiving.motionZ
				- this.posZ;
		float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
		
		if(f1 <= 2.0)
			entityball.setThrowableHeading(d0, d1, d2,
					0.40F, 8.0F);
		else
			entityball.rotationPitch += 20.0F;
			entityball.setThrowableHeading(d0, d1 + (double) (f1 * 0.2F), d2,
				0.80F, 8.0F);
		this.worldObj.spawnEntityInWorld(entityball);
	}
    
    public void doBowAttack(EntityLivingBase par1EntityLiving, float par2)
    {
    	int slowtime = IaSWorldHelper.getDifficulty(this.worldObj) * 70;
        EntityIceArrow var2 = new EntityIceArrow(this.worldObj, this, par1EntityLiving, 1.70F, 5.0F, IaSWorldHelper.getDifficulty(this.worldObj)-1, slowtime);
        int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        var2.setIsCritical(false);
        
        var2.setDamage(getAttackStrength(par1EntityLiving));

        if (var3 > 0)
        {
            var2.setDamage(var2.getDamage() + (double)var3 * 0.5D + 0.5D);
        }

        if (var4 > 0)
        {
            var2.setKnockbackStrength(var4);
        }

        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.3F + 0.6F));
        this.worldObj.spawnEntityInWorld(var2);
    }
	
    public boolean canPickupLoot() {
    	return this.typpe == EnumNyxSkeletonType.MELEE || this.isUsingAlternateWeapon();
    }
    
    @Override
    protected void addRandomArmor()
    {
    	if (this.rand.nextFloat() < nyxSkeletonArmorProbability[IaSWorldHelper.getDifficulty(this.worldObj)])
        {
            int i = this.rand.nextInt(2 + (IaSWorldHelper.getDifficulty(this.worldObj) == 3?1:0));
            float f = IaSWorldHelper.getDifficulty(this.worldObj) == 3 ? 0.1F : 0.25F;

            for(int val = 0; val < 3; ++val) {
            	if (this.rand.nextFloat() < 0.095F)
            		++i;
            }

            for (int j = 3; j >= 1; --j)
            {
                if (j < 3 && this.rand.nextFloat() < f)
                    break;

                if (this.getEquipmentInSlot(j) == null)
                {
                    ItemStack arm = IaSTools.getArmorForSlot(j, i);

                    if (arm != null)
                        this.setCurrentItemOrArmor(j, arm);
                }
            }
        }
    }
    
    public EnumNyxSkeletonType getNyxSkeletonCombatType() {
    	return typpe;
    }
    
    public ItemStack getDefaultWeapon(EnumNyxSkeletonType taipe) {
    	if(taipe == EnumNyxSkeletonType.MELEE) {
    		ItemStack ait = new ItemStack(Items.stone_sword);

    		if(IaSWorldHelper.getDifficulty(this.worldObj) >= 2)
    			ait.addEnchantment(Enchantment.sharpness, 0);
    		if(IaSWorldHelper.getDifficulty(this.worldObj) == 3)
    			ait.addEnchantment(Enchantment.knockback, 0);
    		return ait;
    	}
    	if(taipe == EnumNyxSkeletonType.BOW_FROST_SHORT)
    		return new ItemStack(NyxItems.frostBowShort,1,385-this.rand.nextInt(16));
    	if(taipe == EnumNyxSkeletonType.MAGIC_SHADOW)
    		return new ItemStack(Items.bone);
    	if(taipe == EnumNyxSkeletonType.BOW_FROST_LONG)
    		return new ItemStack(NyxItems.frostBowLong,1,255-this.rand.nextInt(16));
    	if(taipe == EnumNyxSkeletonType.KNIFE_DEVORA)
    		return new ItemStack(NyxItems.devora);
    	return null;
    }
    
    public ItemStack getDefaultAlternateWeapon(EnumNyxSkeletonType taipe) {
    	if(taipe == EnumNyxSkeletonType.MELEE)
    		return new ItemStack(Items.wooden_sword);
    	else
    		return new ItemStack(Items.stone_sword);
    }
    
    public ItemStack getReserveWeapon() {
    	return this.reserveWeapon;
    }
    
    public void useAlternateWeapon(boolean use) {
    	if(altWeaponFlag == use)
    		return;
    	
    	altWeaponFlag = use;
    	ItemStack switche = this.getEquipmentInSlot(0);
    	this.setCurrentItemOrArmor(0, reserveWeapon);
    	reserveWeapon = switche;
    }
    
    public boolean isUsingAlternateWeapon() {
    	return altWeaponFlag;
    }
    
    public void setNyxSkeletonCombatType(int comb_id) {
    	setNyxSkeletonCombatType(EnumNyxSkeletonType.fromId(comb_id));
    }
    
    public void setNyxSkeletonCombatType(EnumNyxSkeletonType taipe) {
    	typpe = taipe;
        this.setCurrentItemOrArmor(0, getDefaultWeapon(taipe));
    	this.reserveWeapon = this.getDefaultAlternateWeapon(taipe);
    }
    
    protected void jump()
    {
    	super.jump();
        this.motionY = 1.2D*0.41999998688697815D;
    }
    
    @Override
    public void entityInit() {
    	this.altWeaponFlag = false;
    	
    	//Sword skeleton.
    	if (this.rand.nextInt(4) == 0) {
    		setNyxSkeletonCombatType(EnumNyxSkeletonType.MELEE);
    	}
    	
    	//Shadow skeleton.
    	else if (this.rand.nextInt(12) == 0) {
    		ItemStack helm = new ItemStack(Items.leather_helmet);
    		((ItemArmor)helm.getItem()).func_82813_b(helm, 0x773333);
            this.setCurrentItemOrArmor(4, helm);
            this.equipmentDropChances[4] = 0.0F;
            
    		setNyxSkeletonCombatType(EnumNyxSkeletonType.MAGIC_SHADOW);
    	}
    	
    	//Shadow skeleton.
    	else if (this.rand.nextInt(8) == 0) {
    		ItemStack helm = new ItemStack(Items.leather_helmet);
    		((ItemArmor)helm.getItem()).func_82813_b(helm, 0x333377);
            this.setCurrentItemOrArmor(4, helm);
            this.equipmentDropChances[4] = 0.0F;

            this.setCurrentItemOrArmor(0, this.getDefaultWeapon(EnumNyxSkeletonType.MAGIC_SHADOW));
    		setNyxSkeletonCombatType(EnumNyxSkeletonType.MAGIC_SHADOW);
    	}
    	
    	//Bow skeleton.
        else {
            this.setCurrentItemOrArmor(0, this.getDefaultWeapon(EnumNyxSkeletonType.BOW_FROST_SHORT));
    		setNyxSkeletonCombatType(EnumNyxSkeletonType.BOW_FROST_SHORT);
        }
    
    	this.addRandomArmor();
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        if(par1NBTTagCompound.hasKey("NyxSkeletonCombatStyle"))
        	this.setNyxSkeletonCombatType(par1NBTTagCompound.getByte("NyxSkeletonCombatStyle"));

        super.readEntityFromNBT(par1NBTTagCompound);
        
        this.setCombatTask();
    }
    
    public double getScaledMaxHealth() {
    	return 30.0D;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("NyxSkeletonCombatStyle", (byte)this.typpe.id);
    }
    
    @Override
	protected void applyEntityAttributes()
    {
            super.applyEntityAttributes();
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getScaledMaxHealth());
            this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.33D);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.moveSpeed);
            this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }

	@Override
	public IaSSense getSense() {
		return senses;
	}

}
