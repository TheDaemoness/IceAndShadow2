package iceandshadow2.nyx.entities.ai;

import iceandshadow2.ias.ai.IIaSMobGetters;
import iceandshadow2.nyx.entities.ai.senses.IIaSSensateOld;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;

public class EntityAINyxSearch extends EntityAIBase {

	protected EntityMob taskOwner;
	protected EntityLivingBase target;
	protected int seen;

	public EntityAINyxSearch(EntityMob b) {
		taskOwner = b;
		seen = 0;
	}

	@Override
	public boolean continueExecuting() {
		if (seen > 125 || ((IIaSSensateOld) taskOwner).getSense().canSense(target)) {
			((IIaSMobGetters) taskOwner).setSearchTarget(null);
			taskOwner.getNavigator().clearPathEntity();
			seen = 0;
		}
		return true;
	}

	@Override
	public boolean isInterruptible() {
		return true;
	}

	@Override
	public boolean shouldExecute() {
		return ((IIaSMobGetters) taskOwner).getSearchTarget() != null;
	}

	@Override
	public void startExecuting() {
		target = ((IIaSMobGetters) taskOwner).getSearchTarget();
		seen = 0;
		taskOwner.getNavigator().clearPathEntity();
		taskOwner.getNavigator().tryMoveToEntityLiving(target,
				((IIaSMobGetters) taskOwner).getMoveSpeed());
	}

	@Override
	public void updateTask() {
		++seen;
	}

}
