package iceandshadow2.nyx.entities.ai;

import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.ai.IIaSMobGetters;
import iceandshadow2.nyx.entities.ai.senses.IIaSSensateOld;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class EntityAINyxTargeter extends EntityAINyxAttack {
	
	public EntityAINyxTargeter(EntityMob par1EntityCreature) {
		super(par1EntityCreature);
	}

	@Override
	public boolean continueExecuting() {
		return !this.taskOwner.isPotionActive(Potion.confusion.id) && super.continueExecuting();
	}

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

			boolean hates = true;
			if(this.taskOwner instanceof IIaSMobGetters)
				hates = ((IIaSMobGetters)this.taskOwner).hates(EnumIaSAspect.getAspect(ent));
			// Give priority to players.
			if(hates) {
				if (ent instanceof EntityPlayer) {
					playerflag = true;
					if (this.taskOwner.getDistanceSqToEntity(ent) < nearest) {
						nearest = this.taskOwner.getDistanceSqToEntity(ent);
						targ = (EntityLivingBase) ent;
					}
				} else if (!playerflag) {
					if (this.taskOwner.getDistanceSqToEntity(ent) < nearest) {
						nearest = this.taskOwner.getDistanceSqToEntity(ent);
						targ = (EntityLivingBase) ent;
					}
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

}
