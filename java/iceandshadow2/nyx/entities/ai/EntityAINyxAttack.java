package iceandshadow2.nyx.entities.ai;

import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.util.IaSWorldHelper;
import iceandshadow2.nyx.entities.ai.senses.IIaSSensateOld;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;

public class EntityAINyxAttack extends EntityAITarget {

	protected int lastSeen;

	public EntityAINyxAttack(EntityMob par1EntityCreature) {
		super(par1EntityCreature, false, false);
		lastSeen = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		final EntityLivingBase elb = taskOwner.getAttackTarget();
		if (elb == null)
			return false;
		else if (!elb.isEntityAlive())
			return false;
		else if (elb instanceof EntityPlayer)
			if (((EntityPlayer) elb).capabilities.isCreativeMode) {
				taskOwner.setAttackTarget(null);
				return false;
			}
		return true;
	}

	@Override
	protected boolean isSuitableTarget(EntityLivingBase candi, boolean par2) {
		// Discard parameter 2.
		if (!super.isSuitableTarget(candi, false))
			return false;
		if (IaSWorldHelper.getDifficulty(taskOwner.worldObj) >= 3
				&& EnumIaSAspect.getAspect(candi) == EnumIaSAspect.getAspect(taskOwner))
			return false;
		return ((IIaSSensateOld) taskOwner).getSense().canSense(candi);
	}

	@Override
	public void resetTask() {
		taskOwner.setAttackTarget(null);
		super.resetTask();
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (taskOwner.isPotionActive(Potion.confusion.id))
			return false;
		final double d0 = ((IIaSSensateOld) taskOwner).getSense().getRange();
		final List<Entity> list = taskOwner.worldObj.getEntitiesWithinAABBExcludingEntity(taskOwner,
				taskOwner.boundingBox.expand(d0, d0, d0));

		if (list.isEmpty())
			return false;

		double nearest = Double.MAX_VALUE;
		EntityLivingBase targ = null;
		boolean playerflag = false;
		for (final Entity ent : list) {

			// Basic checks.
			if (!(ent instanceof EntityLivingBase)) {
				continue;
			}
			if (!isSuitableTarget((EntityLivingBase) ent, false)) {
				continue;
			}

			// Give priority to players.
			if (ent instanceof EntityPlayer) {
				playerflag = true;
				if (taskOwner.getDistanceSqToEntity(ent) < nearest) {
					nearest = taskOwner.getDistanceSqToEntity(ent);
					targ = (EntityLivingBase) ent;
				}
			} else if (!playerflag && ent instanceof EntityAgeable)
				if (taskOwner.getDistanceSqToEntity(ent) < nearest) {
					nearest = taskOwner.getDistanceSqToEntity(ent);
					targ = (EntityLivingBase) ent;
				}
		}
		if (targ != null) {
			lastSeen = 0;
			taskOwner.setAttackTarget(targ);
			taskOwner.getNavigator().clearPathEntity();
			return true;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		final double d0 = getTargetDistance();
		final List list = taskOwner.worldObj
				.getEntitiesWithinAABB(taskOwner.getClass(),
						AxisAlignedBB.getBoundingBox(taskOwner.posX, taskOwner.posY, taskOwner.posZ,
								taskOwner.posX + 1.0D, taskOwner.posY + 1.0D, taskOwner.posZ + 1.0D)
								.expand(d0, 10.0D, d0));
		final Iterator iterator = list.iterator();

		while (iterator.hasNext()) {
			final EntityCreature ally = (EntityCreature) iterator.next();

			final boolean isFriends = taskOwner.getAttackTarget() != null && ally.isOnSameTeam(taskOwner.getAttackTarget());
			if (taskOwner != ally && ally.getAttackTarget() == null && !isFriends) {
				ally.setAttackTarget(taskOwner.getAttackTarget());
			}
		}
		super.startExecuting();
	}

	@Override
	public void updateTask() {
		final EntityLivingBase elb = taskOwner.getAttackTarget();
		if (elb == null)
			return;
		if (!((IIaSSensateOld) taskOwner).getSense().canSense(elb)) {
			++lastSeen;
			if (lastSeen > 85) {
				taskOwner.setAttackTarget(null);
				taskOwner.getNavigator().clearPathEntity();
				taskOwner.getNavigator().tryMoveToXYZ(elb.posX, elb.posY, elb.posZ, taskOwner.getAIMoveSpeed());
			}
		} else {
			lastSeen = 0;
		}
	}
}
