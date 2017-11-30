package iceandshadow2.nyx.entities.ai;

import iceandshadow2.nyx.entities.ai.senses.IIaSSensate;
import iceandshadow2.nyx.entities.mobs.IIaSMobGetters;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class EntityAINyxTargeter extends EntityAITarget {

	protected int lastSeen;
	protected EntityLivingBase targetEntity;

	public EntityAINyxTargeter(EntityMob par1EntityCreature) {
		super(par1EntityCreature, false, false);
		this.lastSeen = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		final EntityLivingBase elb = this.taskOwner.getAttackTarget();

		if (elb == null) {
			return false;
		} else if (this.taskOwner.isPotionActive(Potion.confusion.id)) {
			return false;
		} else if (!elb.isEntityAlive()) {
			return false;
		} else if (!((IIaSSensate) this.taskOwner).getSense().canSense(elb)) {
			++this.lastSeen;
			if (this.lastSeen > 30) {
				((IIaSMobGetters) this.taskOwner).setSearchTarget(elb);
				return false;
			}
			return true;
		} else if (elb instanceof EntityPlayer) {
			if (((EntityPlayer) elb).capabilities.isCreativeMode)
				return false;
		}
		this.lastSeen = 0;
		return true;
	}

	@Override
	protected boolean isSuitableTarget(EntityLivingBase candi, boolean par2) {
		if (!super.isSuitableTarget(candi, par2))
			return false;
		return ((IIaSSensate) this.taskOwner).getSense().canSense(candi);
	}

	@Override
	public void resetTask() {
		super.resetTask();
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if(this.taskOwner.isPotionActive(Potion.confusion.id))
			return false;
		final double d0 = ((IIaSSensate) this.taskOwner).getSense().getRange();
		final List<Entity> list = this.taskOwner.worldObj
				.getEntitiesWithinAABBExcludingEntity(this.taskOwner,
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
			this.targetEntity = targ;
			return true;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		this.lastSeen = 0;
		this.taskOwner.setAttackTarget(this.targetEntity);
		this.targetEntity = null;
		this.taskOwner.getNavigator().clearPathEntity();
		super.startExecuting();
	}

}
