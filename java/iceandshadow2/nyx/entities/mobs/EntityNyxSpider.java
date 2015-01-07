package iceandshadow2.nyx.entities.mobs;

import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSWorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityNyxSpider extends EntitySpider {
	
	public EntityNyxSpider(World par1World) {
		super(par1World);
		this.setSize(0.7F, 0.5F);
		this.experienceValue = 4;
		
		this.setInvisible(true);
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public int getBrightnessForRender(float par1) {
        return super.getBrightnessForRender(par1)/2 + Integer.MAX_VALUE/2;
    }

	@Override
	public float getBrightness(float par1) {
		return super.getBrightness(par1) * 0.5F + 0.5F;
	}

	@Override
	public boolean getCanSpawnHere() {
		for (int x = -15; x < 16; ++x) {
			for (int y = -15; y < 16; ++y) {
				for (int z = -15; z < 16; ++z) {
					//TODO: Wisp repellant check.
				}
			}
		}
		return this.posY > 64.0F && super.getCanSpawnHere();
	}

	@Override
	protected void fall(float par1) {
	}
	
	@Override
	protected boolean isValidLightLevel() {
        return true;
    }


    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
	protected String getHurtSound()
    {
    	if(this.isInvisible())
    		return null;
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
	protected String getDeathSound()
    {
    	if(this.isInvisible())
    		return null;
        return "mob.spider.death";
    }

	public double getScaledMaxHealth() {
		if(this.worldObj == null)
			return 20.0;
		return 12.0 + 4.0 * this.worldObj.difficultySetting.getDifficultyId();
	}

	@Override
	protected void dropRareDrop(int par1) {
		if (this.isInvisible())
			this.dropItem(NyxItems.bloodstone,1);
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

		if (this.isInvisible())
			this.dropItem(Items.experience_bottle, 1+this.rand.nextInt(2+par2));

		/*
		int baite = this.rand.nextInt(16)-par2;
		if (baite <= 0)
			this.dropItem(IaSItems.nyxToughGossamer.itemID, 1);

        if(this.rand.nextInt(4-this.worldObj.difficultySetting) == 0)
			this.dropItem(IaSItems.nyxPhantomResin.itemID, 1+this.rand.nextInt(1+par2));
			*/
	}


    @Override
	public boolean attackEntityAsMob(Entity par1Entity)
    {
		this.setInvisible(false);
		
		float dmg = IaSWorldHelper.getDifficulty(this.worldObj) + (IaSWorldHelper.getDifficulty(this.worldObj)>=3?1:0);
		
		DamageSource dmgsrc = DamageSource.causeMobDamage(this);
		dmgsrc.setMagicDamage();
		boolean flag = par1Entity.attackEntityFrom(dmgsrc, dmg);
		
		if (flag) {
            if (par1Entity instanceof EntityLivingBase) {
				int var2 = (this.worldObj.difficultySetting.getDifficultyId()-1);
					((EntityLivingBase) par1Entity)
							.addPotionEffect(new PotionEffect(Potion.poison.id,
									var2 * 80 + 139, 0));
					((EntityLivingBase) par1Entity)
							.addPotionEffect(new PotionEffect(
									Potion.moveSlowdown.id, var2 * 60 + 199,
									var2));
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        Object par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);
        
        //No spider wisp jokeys.
        if(this.riddenByEntity != null) {
        	this.riddenByEntity.setDead();
        }

        return (IEntityLivingData)par1EntityLivingData1;
    }
	
	protected void doUncloakSound() {
		this.playSound("random.breath",
				1.0F - this.worldObj.difficultySetting.getDifficultyId() * 0.10F,
				this.rand.nextFloat() * 0.2F + 0.9F);
	}
	
	@Override
	public void setRevengeTarget(EntityLivingBase elb) {
		super.setRevengeTarget(elb);
		if(this.isInvisible()) {
			doUncloakSound();
			this.setInvisible(false);
		}
	}
	
	//To protect against reward hijacking with splash potions.
	@Override
	public void addPotionEffect(PotionEffect eff) {
		if(eff.getPotionID() == Potion.invisibility.id)
			return;
		super.addPotionEffect(eff);
	}

	@Override
	protected Entity findPlayerToAttack() {

		double range = (this.isPotionActive(Potion.blindness.id)?2.0D:12.0D);
		EntityPlayer plai = this.worldObj.getClosestVulnerablePlayerToEntity(
				this, range);
		
		if(plai != null && !plai.isInvisible()) {
			
			if (this.isInvisible()) {
				doUncloakSound();
				this.setInvisible(false);
			}
			return plai;
		}
		else if (!this.isInvisible())
			this.setInvisible(true);
		return null;
	}

	public float getAttackStrength(Entity par1Entity) {
		return 5.0F;
	}

	@Override
	protected String getLivingSound() {
		return null;
	}

	protected void playStepSound(int par1, int par2, int par3, int par4) {
		return;
	}

	protected void playJumpSound(int par1, int par2, int par3, int par4) {
		if(!this.isInvisible())
			this.playSound("mob.spider.step", 0.15F, 1.0F);
	}
	
    @Override
	public float getBlockPathWeight(int i, int j, int k)
    {
    	int lightb = worldObj.getBlockLightValue(i, j, k);
    	return (lightb>7?0:1);
    }
    
    @Override
	protected void applyEntityAttributes()
    {
            super.applyEntityAttributes();
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getScaledMaxHealth());
            this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
    }
}
