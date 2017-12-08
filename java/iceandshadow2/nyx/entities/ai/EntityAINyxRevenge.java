package iceandshadow2.nyx.entities.ai;

import iceandshadow2.ias.ai.IIaSMobGetters;
import iceandshadow2.nyx.entities.ai.senses.IIaSSensateOld;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class EntityAINyxRevenge extends EntityAINyxAttack {

	public EntityAINyxRevenge(EntityMob par1EntityCreature) {
		super(par1EntityCreature);
	}

	@Override
	public boolean shouldExecute() {
		final boolean EXECUTE_ME = isSuitableTarget(this.taskOwner.getAITarget(), false);
		if (EXECUTE_ME) { //Though we are trapped in this strange strange simulation.
			this.lastSeen = 0;
			this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
			this.taskOwner.getNavigator().clearPathEntity();
		}
		return EXECUTE_ME;
	}

}
