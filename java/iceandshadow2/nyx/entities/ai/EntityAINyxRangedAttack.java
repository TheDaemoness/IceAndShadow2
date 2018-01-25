package iceandshadow2.nyx.entities.ai;

import iceandshadow2.nyx.entities.ai.senses.IIaSSensateOld;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

public class EntityAINyxRangedAttack extends EntityAIBase {
	/** The entity the AI instance has been applied to */
	private final EntityLiving entityHost;
	private boolean reflexDelay;

	/**
	 * The entity (as a RangedAttackMob) the AI instance has been applied to.
	 */
	private final IRangedAttackMob rangedAttackEntityHost;
	private EntityLivingBase attackTarget;

	/**
	 * A decrementing tick that spawns a ranged attack once this value reaches
	 * 0. It is then set back to the maxRangedAttackTime.
	 */
	private int rangedAttackTime;
	private double entityMoveSpeed;
	private int stopMovingDelay;
	private int minRangedAttackTime;

	/**
	 * The maximum time the AI has to wait before performing another ranged
	 * attack.
	 */
	private int maxRangedAttackTime;
	private float range;
	private float rangeSq;
	private boolean reqLOS;

	public EntityAINyxRangedAttack(IRangedAttackMob par1IRangedAttackMob, double par2, int par4, float par5) {
		this(par1IRangedAttackMob, par2, par4, par4, par5);
	}

	public EntityAINyxRangedAttack(IRangedAttackMob par1IRangedAttackMob, double par2, int par4, int par5, float par6) {
		this(par1IRangedAttackMob, par2, par4, par5, par6, true);
	}

	public EntityAINyxRangedAttack(IRangedAttackMob par1IRangedAttackMob, double par2, int par4, int par5, float par6,
			boolean los) {
		rangedAttackTime = -1;

		if (!(par1IRangedAttackMob instanceof EntityLivingBase))
			throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
		else {
			rangedAttackEntityHost = par1IRangedAttackMob;
			entityHost = (EntityLiving) par1IRangedAttackMob;
			entityMoveSpeed = par2;
			minRangedAttackTime = par4;
			maxRangedAttackTime = par5;
			range = par6;
			rangeSq = par6 * par6;
			reqLOS = los;
			setMutexBits(3);
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return shouldExecute() || !entityHost.getNavigator().noPath();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		attackTarget = null;
		stopMovingDelay = 0;
		rangedAttackTime = -1;
		reflexDelay = false;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		final EntityLivingBase entitylivingbase = entityHost.getAttackTarget();

		if (entitylivingbase == null || entitylivingbase.isDead)
			return false;
		else {
			attackTarget = entitylivingbase;
			return true;
		}
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		final double d0 = entityHost.getDistanceSq(attackTarget.posX, attackTarget.boundingBox.minY, attackTarget.posZ);
		boolean flag;
		if (entityHost instanceof IIaSSensateOld) {
			flag = ((IIaSSensateOld) entityHost).getSense().canSense(attackTarget);
		} else {
			flag = true;
		}
		flag &= !reqLOS || entityHost.getEntitySenses().canSee(attackTarget);

		if (flag) {
			if (stopMovingDelay < 10) {
				++stopMovingDelay;
			}
		} else {
			stopMovingDelay = 0;
		}

		if (d0 > rangeSq || stopMovingDelay < 10) {
			entityHost.getNavigator().tryMoveToEntityLiving(attackTarget, entityMoveSpeed);
		} else {
			entityHost.getNavigator().clearPathEntity();
		}

		float f;
		entityHost.getLookHelper().setLookPositionWithEntity(attackTarget, 30.0F, 30.0F);

		if (--rangedAttackTime <= 0) {
			if (d0 > rangeSq || !flag)
				return;

			f = MathHelper.sqrt_double(d0) / range;
			float f1 = f;

			if (f < 0.05F) {
				f1 = 0.05F;
			}

			if (f1 > 1.1F) {
				f1 = 1.1F;
			}

			rangedAttackTime = MathHelper
					.floor_float(f * (maxRangedAttackTime - minRangedAttackTime) + minRangedAttackTime);
			if (reflexDelay) {
				rangedAttackEntityHost.attackEntityWithRangedAttack(attackTarget, f1);
			} else {
				reflexDelay = true;
				rangedAttackTime /= 2;
			}

		}
	}
}
