package iceandshadow2.nyx.entities.ai;

import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.ai.IIaSMobGetters;
import iceandshadow2.nyx.entities.ai.senses.IIaSSensateOld;
import iceandshadow2.util.IaSWorldHelper;

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
	
	@Override
	public void updateTask() {
		final EntityLivingBase elb = this.taskOwner.getAttackTarget();
		if(elb == null)
			return;
		if(!((IIaSSensateOld)this.taskOwner).getSense().canSense(elb)) {
			++lastSeen;
			if(lastSeen > 85) {
				this.taskOwner.setAttackTarget(null);
				this.taskOwner.getNavigator().clearPathEntity();
				this.taskOwner.getNavigator().tryMoveToXYZ(elb.posX, elb.posY, elb.posZ, this.taskOwner.getAIMoveSpeed());
			}
		}
		else
			lastSeen = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		final EntityLivingBase elb = this.taskOwner.getAttackTarget();
		if (elb == null) {
			return false;
		} else if (!elb.isEntityAlive()) {
			return false;
		} else if (elb instanceof EntityPlayer) {
			if (((EntityPlayer) elb).capabilities.isCreativeMode) {
				this.taskOwner.setAttackTarget(null);
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean isSuitableTarget(EntityLivingBase candi, boolean par2) {
		//Discard parameter 2.
		if (!super.isSuitableTarget(candi, false))
			return false;
		if (IaSWorldHelper.getDifficulty(this.taskOwner.worldObj) >= 3 &&
				EnumIaSAspect.getAspect(candi) == EnumIaSAspect.getAspect(this.taskOwner))
			return false;
		return ((IIaSSensateOld) this.taskOwner).getSense().canSense(candi);
	}

	@Override
	public void resetTask() {
		this.taskOwner.setAttackTarget(null);
		super.resetTask();
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (this.taskOwner.isPotionActive(Potion.confusion.id))
			return false;
		final double d0 = ((IIaSSensateOld) this.taskOwner).getSense().getRange();
		final List<Entity> list = this.taskOwner.worldObj.getEntitiesWithinAABBExcludingEntity(this.taskOwner,
				this.taskOwner.boundingBox.expand(d0, d0, d0));

		if (list.isEmpty())
			return false;

		double nearest = Double.MAX_VALUE;
		EntityLivingBase targ = null;
		boolean playerflag = false;
		for (final Entity ent : list) {

			// Basic checks.
			if (!(ent instanceof EntityLivingBase))
				continue;
			if (!isSuitableTarget((EntityLivingBase) ent, false))
				continue;

			// Give priority to players.
			if (ent instanceof EntityPlayer) {
				playerflag = true;
				if (this.taskOwner.getDistanceSqToEntity(ent) < nearest) {
					nearest = this.taskOwner.getDistanceSqToEntity(ent);
					targ = (EntityLivingBase) ent;
				}
			} else if (!playerflag && ent instanceof EntityAgeable) {
				if (this.taskOwner.getDistanceSqToEntity(ent) < nearest) {
					nearest = this.taskOwner.getDistanceSqToEntity(ent);
					targ = (EntityLivingBase) ent;
				}
			}
		}
		if (targ != null) {
			this.lastSeen = 0;
			this.taskOwner.setAttackTarget(targ);
			this.taskOwner.getNavigator().clearPathEntity();
			return true;
		}
		return false;
	}

	@Override
	public void startExecuting() {
        double d0 = this.getTargetDistance();
        List list = this.taskOwner.worldObj.getEntitiesWithinAABB(
        		this.taskOwner.getClass(),
        		AxisAlignedBB.getBoundingBox(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D).expand(d0, 10.0D, d0));
        Iterator iterator = list.iterator();

        while (iterator.hasNext())
        {
            EntityCreature ally = (EntityCreature)iterator.next();

            if (this.taskOwner != ally && ally.getAttackTarget() == null && !ally.isOnSameTeam(this.taskOwner.getAttackTarget()))
            {
                ally.setAttackTarget(this.taskOwner.getAttackTarget());
            }
        }
        super.startExecuting();
	}
}
