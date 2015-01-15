package iceandshadow2.nyx.entities.ai;

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

	public EntityAINyxRangedAttack(IRangedAttackMob par1IRangedAttackMob,
			double par2, int par4, float par5) {
		this(par1IRangedAttackMob, par2, par4, par4, par5);
	}

	public EntityAINyxRangedAttack(IRangedAttackMob par1IRangedAttackMob,
			double par2, int par4, int par5, float par6) {
		this(par1IRangedAttackMob, par2, par4, par5, par6, true);
	}

	public EntityAINyxRangedAttack(IRangedAttackMob par1IRangedAttackMob,
			double par2, int par4, int par5, float par6, boolean los) {
		this.rangedAttackTime = -1;

		if (!(par1IRangedAttackMob instanceof EntityLivingBase)) {
			throw new IllegalArgumentException(
					"ArrowAttackGoal requires Mob implements RangedAttackMob");
		} else {
			this.rangedAttackEntityHost = par1IRangedAttackMob;
			this.entityHost = (EntityLiving) par1IRangedAttackMob;
			this.entityMoveSpeed = par2;
			this.minRangedAttackTime = par4;
			this.maxRangedAttackTime = par5;
			this.range = par6;
			this.rangeSq = par6 * par6;
			this.reqLOS = los;
			this.setMutexBits(3);
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		this.attackTarget = null;
		this.stopMovingDelay = 0;
		this.rangedAttackTime = -1;
		this.reflexDelay = false;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		final EntityLivingBase entitylivingbase = this.entityHost
				.getAttackTarget();

		if (entitylivingbase == null) {
			return false;
		} else {
			this.attackTarget = entitylivingbase;
			return true;
		}
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		final double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX,
				this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
		final boolean flag = !reqLOS
				|| this.entityHost.getEntitySenses().canSee(this.attackTarget);

		if (flag) {
			if (this.stopMovingDelay < 10)
				++this.stopMovingDelay;
		} else
			this.stopMovingDelay = 0;

		if (d0 > this.rangeSq || this.stopMovingDelay < 10) {
			this.entityHost.getNavigator().tryMoveToEntityLiving(
					this.attackTarget, this.entityMoveSpeed);
		} else {
			this.entityHost.getNavigator().clearPathEntity();
		}

		float f;
		this.entityHost.getLookHelper().setLookPositionWithEntity(
				this.attackTarget, 30.0F, 30.0F);

		if (--this.rangedAttackTime <= 0) {
			if (d0 > this.rangeSq || !flag) {
				return;
			}

			f = MathHelper.sqrt_double(d0) / this.range;
			float f1 = f;

			if (f < 0.05F) {
				f1 = 0.05F;
			}

			if (f1 > 1.1F) {
				f1 = 1.1F;
			}

			this.rangedAttackTime = MathHelper.floor_float(f
					* (this.maxRangedAttackTime - this.minRangedAttackTime)
					+ this.minRangedAttackTime);
			if (reflexDelay)
				this.rangedAttackEntityHost.attackEntityWithRangedAttack(
						this.attackTarget, f1);
			else {
				reflexDelay = true;
				this.rangedAttackTime /= 2;
			}

		}
	}
}
