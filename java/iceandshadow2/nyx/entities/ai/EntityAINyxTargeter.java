package iceandshadow2.nyx.entities.ai;

import iceandshadow2.nyx.entities.ai.senses.IIaSSensate;

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

	protected double base_range;
	protected int lastseen;
    protected EntityLivingBase targetEntity;

	public EntityAINyxTargeter(EntityMob par1EntityCreature, double range) {
		super(par1EntityCreature, false, false);
		base_range = range;
	}

	/*
	 * Simulate the conditions to maximize an entity's seek range and return
	 * that value.
	 */
	public double getMaxSeekRange() {
		return base_range*2.0F; //canSenseMadness
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
			this.taskOwner.getNavigator().
				tryMoveToEntityLiving(elb, 
						this.taskOwner.getAttributeMap().
						getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue());
			return false;
		} else if(elb instanceof EntityPlayer) {
			if(((EntityPlayer)elb).capabilities.isCreativeMode)
				return false;
		}
		return true;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		double d0 = this.getMaxSeekRange();
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
    	lastseen = 0;
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

	@Override
	protected boolean isSuitableTarget(EntityLivingBase candi, boolean par2) {
		if (((IIaSSensate)this.taskOwner).getSense().canSense(candi))
			return super.isSuitableTarget(candi, par2);
		return false;
	}

}
