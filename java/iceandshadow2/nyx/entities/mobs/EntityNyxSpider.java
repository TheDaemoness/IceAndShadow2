package iceandshadow2.nyx.entities.mobs;

import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.ias.util.IaSEntityHelper;
import iceandshadow2.ias.util.IaSWorldHelper;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.util.EntityOrbNourishment;
import iceandshadow2.nyx.world.NyxBiomes;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityNyxSpider extends EntitySpider implements IIaSAspect {

	public EntityNyxSpider(World par1World) {
		super(par1World);
		setSize(0.7F, 0.5F);
		experienceValue = 4;
		maxHurtResistantTime /= 2;
		setInvisible(true);
	}

	// To protect against reward hijacking with splash potions.
	@Override
	public void addPotionEffect(PotionEffect eff) {
		if (eff.getPotionID() == Potion.invisibility.id)
			return;
		super.addPotionEffect(eff);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getScaledMaxHealth());
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		setInvisible(false);

		final float dmg = (IaSWorldHelper.getDifficulty(worldObj) >= 3 ? 2 : 1)
				+ IaSWorldHelper.getRegionArmorMod(this);

		final DamageSource dmgsrc = DamageSource.causeMobDamage(this);
		dmgsrc.setMagicDamage();
		final boolean flag = par1Entity.attackEntityFrom(dmgsrc, dmg);

		if (flag) {
			if (par1Entity instanceof EntityLivingBase) {
				final int lvl = IaSWorldHelper.getDifficulty(worldObj) - 1;
				int mod = IaSWorldHelper.getDifficulty(worldObj) >= 3 ? 225 : 275;
				final EntityLivingBase elb = (EntityLivingBase) par1Entity;
				final boolean hometurf = IaSEntityHelper.getBiome(elb) == NyxBiomes.nyxInfested;
				if (!elb.isPotionActive(Potion.poison))
					mod /= 2;
				if (hometurf)
					mod /= 2;
				elb.addPotionEffect(new PotionEffect(Potion.poison.id, mod + 60, hometurf ? 1 : 0));
				elb.addPotionEffect(new PotionEffect(Potion.weakness.id, mod + 90, lvl + (hometurf ? 1 : 0)));
			}
			return true;
		} else
			return false;
	}

	protected void doUncloakSound() {
		worldObj.playSoundAtEntity(this, "IceAndShadow2:mob_nyxwisp_materialize",
				1.0F - IaSWorldHelper.getDifficulty(worldObj) * 0.10F, rand.nextFloat() * 0.2F + 0.9F);
	}

	/**
	 * Drop 0-2 items of this living's type. @param par1 - Whether this entity has
	 * recently been hit by a player. @param par2 - Level of Looting used to kill
	 * this mob.
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2) {
		if (!par1)
			return;

		if (isInvisible())
			dropItem(NyxItems.silkBerries, 1 + rand.nextInt(2 + par2));

		final int diff = IaSWorldHelper.getDifficulty(worldObj);
		final int baite = rand.nextInt(Math.max(1, 8 - diff) + par2) - par2;
		if (baite <= 0)
			dropItem(NyxItems.toughGossamer, 1);

		dropItem(NyxItems.resin, rand.nextInt(5) < par2 - 1 ? 2 : 1);

		worldObj.spawnEntityInWorld(new EntityOrbNourishment(worldObj, posX, posY, posZ, 1));
	}

	@Override
	protected void dropRareDrop(int par1) {
		IaSEntityHelper.dropItem(this, new ItemStack(NyxItems.silkBerries, 1, 1));
		final EntityNyxSpiderBaby bab = new EntityNyxSpiderBaby(worldObj);
		bab.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
		worldObj.spawnEntityInWorld(bab);
	}

	@Override
	protected void fall(float par1) {
	}

	@Override
	protected Entity findPlayerToAttack() {
		final double range = this.isPotionActive(Potion.blindness.id) ? 2.0D : 12.0D;
		final EntityPlayer plai = worldObj.getClosestVulnerablePlayerToEntity(this, range);

		if (plai != null && !plai.isInvisible()) {
			if (isInvisible()) {
				doUncloakSound();
				setInvisible(false);
			}
			return plai;
		} else if (!isInvisible())
			setInvisible(true);
		return null;
	}

	@Override
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
		if (!isInvisible())
			playSound("mob.spider.step", 0.15F, 1.0F);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.INFESTATION;
	}

	public float getAttackStrength(Entity par1Entity) {
		return 5.0F;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		if (getAttackTarget() != null)
			return 1;
		final int lightb = worldObj.getBlockLightValue(i, j, k);
		return lightb > 7 ? 0 : 1;
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
		final int wl = IaSWorldHelper.getRegionLevel(this);
		if (wl < 1)
			return false;
		return posY > 64.0F && super.getCanSpawnHere();
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		if (isInvisible())
			return null;
		return "mob.spider.death";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		if (isInvisible())
			return null;
		return "mob.spider.say";
	}

	@Override
	protected String getLivingSound() {
		return null;
	}

	public double getScaledMaxHealth() {
		final double hp = 12.0 + 4.0 * IaSWorldHelper.getDifficulty(worldObj);
		return hp;
	}

	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
		final Object par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);

		// No spider wisp jokeys.
		if (riddenByEntity != null)
			riddenByEntity.setDead();

		return (IEntityLivingData) par1EntityLivingData1;
	}

	@Override
	public void setFire(int time) {
		return;
	}

	@Override
	public void setRevengeTarget(EntityLivingBase elb) {
		super.setRevengeTarget(elb);
		if (isInvisible() && getAttackTarget() != null) {
			doUncloakSound();
			setInvisible(false);
		}
	}
}
