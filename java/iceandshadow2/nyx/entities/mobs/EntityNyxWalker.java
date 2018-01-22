package iceandshadow2.nyx.entities.mobs;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.IaSFlags;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.ai.IIaSMobGetters;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.ias.util.IaSEntityHelper;
import iceandshadow2.ias.util.IaSWorldHelper;
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
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityNyxWalker extends EntityZombie implements IIaSMobGetters {

	protected static double moveSpeed = 0.4;

	private EntityLivingBase searched;
	protected int regenDelay;

	protected IaSSetSenses senses;

	public EntityNyxWalker(World par1World) {
		super(par1World);
		this.setSize(0.6f, 0.8f);

		stepHeight = 0.0f;

		experienceValue = 15;
		regenDelay = 15;

		senses = new IaSSetSenses(this);
		senses.add(new IaSSenseMovement(this, 16.0));
		senses.add(new IaSSenseActions(this, 24.0));
		senses.add(new IaSSenseTouch(this));
		senses.add(new IaSSenseVision(this, 8.0F));

		tasks.taskEntries.clear();
		targetTasks.taskEntries.clear();

		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.8D, true));
		tasks.addTask(3, new EntityAIFleeSun(this, EntityNyxWalker.moveSpeed + 0.5));
		// this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this,
		// 1.0D));
		tasks.addTask(5, new EntityAIWander(this, EntityNyxWalker.moveSpeed));
		tasks.addTask(6, new EntityAINyxWatchClosest(this, EntityPlayer.class, 6.0F, 0.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAINyxRevenge(this));
		targetTasks.addTask(2, new EntityAINyxTargeter(this));
	}

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
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(EntityNyxWalker.moveSpeed);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
		getEntityAttribute(EntityZombie.field_110186_bp).setBaseValue(0.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity victim) {
		final ItemStack is = this.getHeldItem();
		if(is != null && is.getItem() instanceof IIaSTool) {
			IaSToolMaterial mat = IaSToolMaterial.extractMaterial(is);
			if(victim instanceof EntityLivingBase) {
				EntityLivingBase elb = (EntityLivingBase)victim;
				final float basedmg = mat.getToolDamage(is, this, elb) + this.getAttackStrength(elb);
				int headshotresist = 0;
				if(elb.getEquipmentInSlot(4) != null && elb.getEquipmentInSlot(4).getItem() instanceof ItemArmor) {
					headshotresist = Math.max(0, ((ItemArmor)elb.getEquipmentInSlot(4).getItem()).damageReduceAmount);
					elb.getEquipmentInSlot(4).damageItem((int)(1+basedmg)*2, elb);
				}
				if(headshotresist<=2)
					victim.attackEntityFrom(IaSDamageSources.dmgHeadshot,
							1 + basedmg + IaSWorldHelper.getDifficulty(worldObj)*elb.getMaxHealth()/(4+headshotresist));
			}
			mat.onAttack(is, this, victim);
		}
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public boolean couldFlyFasterWithBoots() {
		return false;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		if (!par1)
			return;

		final int diff = IaSWorldHelper.getDifficulty(worldObj);
		final int baite = rand.nextInt(8 + par2) - par2 - diff;

		if (baite <= 0)
			IaSEntityHelper.dropItem(this, new ItemStack(NyxItems.salt, 1, 1));

		IaSEntityHelper.dropItem(this, new ItemStack(NyxItems.toughGossamer));

		worldObj.spawnEntityInWorld(new EntityOrbNourishment(worldObj, posX, posY, posZ, 3));
		
		final int spiders = rand.nextInt(3+IaSWorldHelper.getDifficulty(worldObj)*2)/2;
		for(int i = 0; i < spiders; ++i) {
			worldObj.spawnEntityInWorld(new EntityOrbNourishment(worldObj, posX, posY, posZ, 1));
			EntityNyxSpiderBaby kiddo = new EntityNyxSpiderBaby(worldObj);
			kiddo.setPosition(this.posX-0.25+rand.nextDouble()/2, this.posY+rand.nextDouble()/2, this.posZ-0.25+rand.nextDouble()/2);
			worldObj.spawnEntityInWorld(kiddo);
		}
	}

	@Override
	protected void dropRareDrop(int par1) {
		dropItem(NyxItems.boneSanctified, 1);
	}

	@Override
	protected void fall(float par1) {
	}

	@Override
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.INFESTATION;
	}

	@Override
	public float getBrightness(float par1) {
		return 4*super.getBrightness(par1)/5 + 3f;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender(float par1) {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posZ);

        if (this.worldObj.blockExists(i, 0, j))
        {
            double d0 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66D;
            int k = MathHelper.floor_double(this.posY - (double)this.yOffset + d0);
            return this.worldObj.getLightBrightnessForSkyBlocks(i, k, j, 3);
        }
        else
        {
            return 3;
        }
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
		return EntityNyxWalker.moveSpeed;
	}

	@Override
	public double getScaledMaxHealth() {
		return 30.0D + IaSWorldHelper.getDifficulty(worldObj) * 15;
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
		return aspect == null || aspect == EnumIaSAspect.ANCIENT || aspect == EnumIaSAspect.NYX;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.rotationYawHead+=40*(rand.nextFloat()-0.5f);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData dat) {
		equipmentDropChances[0] = 0.05F;
		String material;
		switch(rand.nextInt(2+2*IaSWorldHelper.getDifficulty(worldObj))) {
		case 0:
		case 1:
		case 2:
			material = "Echir";
			break;
		case 3:
		case 4:
			material = "Devora";
			break;
		default:
			material = "Exousium";
		}
		final ItemStack is = IaSTools.setToolMaterial(IaSTools.pickaxe, material);
		this.setCurrentItemOrArmor(0, is);
		return dat;
	}

	@Override
	public void setInWeb() {
		// Nope.
	}

	@Override
	public void setSearchTarget(EntityLivingBase ent) {
		searched = ent;
	}

	@Override
	public float getAttackStrength(Entity victim) {
		return 3 + IaSWorldHelper.getDifficulty(worldObj)*2;
	}
}
