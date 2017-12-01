package iceandshadow2.nyx.entities.ai;

import iceandshadow2.nyx.entities.ai.senses.IIaSSensate;
import iceandshadow2.nyx.entities.mobs.IIaSMobGetters;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;

public class EntityAINyxSearch extends EntityAIBase {

	protected EntityMob taskOwner;
	protected EntityLivingBase target;
	protected int seen;

	public EntityAINyxSearch(EntityMob b) {
		this.taskOwner = b;
	}

	@Override
	public boolean continueExecuting() {
		++this.seen;
		return this.seen < 125 && !((IIaSSensate) this.taskOwner).getSense().canSense(this.target);
	}

	@Override
	public boolean isInterruptible() {
		return false;
	}

	@Override
	public void resetTask() {
		((IIaSMobGetters) this.taskOwner).setSearchTarget(null);
		super.resetTask();
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
	}

}
