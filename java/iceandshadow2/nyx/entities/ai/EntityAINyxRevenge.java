package iceandshadow2.nyx.entities.ai;

import net.minecraft.entity.monster.EntityMob;

public class EntityAINyxRevenge extends EntityAINyxAttack {

	public EntityAINyxRevenge(EntityMob par1EntityCreature) {
		super(par1EntityCreature);
	}

	@Override
	public boolean shouldExecute() {
		final boolean EXECUTE_ME = isSuitableTarget(taskOwner.getAITarget(), false);
		if (EXECUTE_ME) { // Though we are trapped in this strange strange
							// simulation.
			lastSeen = 0;
			taskOwner.setAttackTarget(taskOwner.getAITarget());
			taskOwner.getNavigator().clearPathEntity();
		}
		return EXECUTE_ME;
	}

}
