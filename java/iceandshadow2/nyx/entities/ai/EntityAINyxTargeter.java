package iceandshadow2.nyx.entities.ai;

import iceandshadow2.nyx.entities.ai.senses.IIaSSensate;
import iceandshadow2.nyx.entities.mobs.IIaSMobGetters;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public class EntityAINyxTargeter extends EntityAITarget {

	protected int lastSeen;
    protected EntityLivingBase targetEntity;

	public EntityAINyxTargeter(EntityMob par1EntityCreature) {
		super(par1EntityCreature, false, false);
		lastSeen = 0;
	}

	@Override
	public void resetTask() {
		super.resetTask();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		EntityLivingBase elb = this.taskOwner.getAttackTarget();
		
		if (elb == null) {
			return false;
		} else if (!elb.isEntityAlive()) {
			return false;
		} else if(!((IIaSSensate)this.taskOwner).getSense().canSense(elb)) {
			++lastSeen;
			if(lastSeen > 30) {
				((IIaSMobGetters)this.taskOwner).setSearchTarget(elb);
				return false;
			}
			return true;
		} else if(elb instanceof EntityPlayer) {
			if(((EntityPlayer)elb).capabilities.isCreativeMode)
				return false;
		}
		lastSeen = 0;
		return true;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		double d0 = ((IIaSSensate)this.taskOwner).getSense().getRange();
		List<Entity> list = this.taskOwner.worldObj
				.getEntitiesWithinAABBExcludingEntity((Entity) this.taskOwner,
						this.taskOwner.boundingBox.expand(d0, d0 / 2.0, d0));

		if (list.isEmpty())
			return false;

		double nearest = Double.MAX_VALUE;
		EntityLivingBase targ = null;
		boolean playerflag = false;
		for (Entity ent : list) {
			
			//Basic checks.
			if(!(ent instanceof EntityLivingBase))
				continue;
			if(!isSuitableTarget((EntityLivingBase)ent,false))
				continue;
			
			//Give priority to players.
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
	
    public void startExecuting()
    {
    	lastSeen = 0;
        this.taskOwner.setAttackTarget(this.targetEntity);
        targetEntity = null;
		this.taskOwner.getNavigator().clearPathEntity();
        super.startExecuting();
    }

	@Override
	protected boolean isSuitableTarget(EntityLivingBase candi, boolean par2) {
		if (!super.isSuitableTarget(candi, par2))
			return false;
		return ((IIaSSensate)this.taskOwner).getSense().canSense(candi);
	}

}
