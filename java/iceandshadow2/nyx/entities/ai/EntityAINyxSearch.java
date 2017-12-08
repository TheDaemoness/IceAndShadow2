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
		this.taskOwner = b;
		seen = 0;
	}

	@Override
	public boolean continueExecuting() {
		if(this.seen > 125 || ((IIaSSensateOld) this.taskOwner).getSense().canSense(this.target)) {
			((IIaSMobGetters) this.taskOwner).setSearchTarget(null);
			this.taskOwner.getNavigator().clearPathEntity();
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
		return ((IIaSMobGetters) this.taskOwner).getSearchTarget() != null;
	}

	@Override
	public void startExecuting() {
		this.target = ((IIaSMobGetters) this.taskOwner).getSearchTarget();
		this.seen = 0;
		this.taskOwner.getNavigator().clearPathEntity();
		this.taskOwner.getNavigator().tryMoveToEntityLiving(this.target,
				((IIaSMobGetters) this.taskOwner).getMoveSpeed());
	}

	@Override
	public void updateTask() {
		++this.seen;
	}

}
