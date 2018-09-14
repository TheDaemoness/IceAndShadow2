package iceandshadow2.nyx.entities.projectile;

import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityPoisonBall extends EntityThrowable implements IIaSAspect {

	public EntityPoisonBall(World par1World) {
		super(par1World);
	}

	@SideOnly(Side.CLIENT)
	public EntityPoisonBall(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	public EntityPoisonBall(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
	}

	@Override
	protected float func_70182_d() {
		return 0.5F;
	}

	@Override
	protected float func_70183_g() {
		return 0.2F;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.POISONWOOD;
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity() {
		return 0.005F;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		IaSFxManager.spawnParticle(worldObj, "poisonSmoke", posX, posY, posZ, false, false);
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {

		if (par1MovingObjectPosition.typeOfHit == MovingObjectType.ENTITY) {
			final EntityLivingBase victim = (EntityLivingBase) (par1MovingObjectPosition.entityHit);
			if (worldObj.isRemote) {
				setDead();
				return;
			}
			if (EnumIaSAspect.getAspect(victim) == EnumIaSAspect.POISONWOOD) {
			} // NO-OP.
			else if (par1MovingObjectPosition.entityHit instanceof EntityLivingBase) {
				if (EnumIaSAspect.getAspect(victim) == EnumIaSAspect.INFESTATION) {
					victim.attackEntityFrom(DamageSource.wither, 16);
					victim.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 165, 3));
					victim.addPotionEffect(new PotionEffect(Potion.wither.id, 165, 1));
				} else {
					if (getThrower() != null)
						victim.attackEntityFrom(DamageSource.causeIndirectMagicDamage(victim, getThrower()), 2);
					else
						victim.attackEntityFrom(DamageSource.magic, 1);
					if (victim.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
						victim.addPotionEffect(new PotionEffect(Potion.wither.id, 165, 1));
					else if (getThrower() instanceof EntityMob) {
						final PotionEffect pot = victim.getActivePotionEffect(Potion.poison);
						if (pot != null)
							victim.addPotionEffect(new PotionEffect(Potion.poison.id, 125, 1 + pot.getAmplifier()));
						else
							victim.addPotionEffect(new PotionEffect(Potion.poison.id, 125, 0));
					} else
						victim.addPotionEffect(new PotionEffect(Potion.poison.id, 165, 0));
				}
				victim.addPotionEffect(new PotionEffect(Potion.confusion.id, 85, 0));
			}

		}
		setDead();
	}
}
