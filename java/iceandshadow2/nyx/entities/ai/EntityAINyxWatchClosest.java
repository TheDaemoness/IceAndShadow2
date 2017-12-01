package iceandshadow2.nyx.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;

public class EntityAINyxWatchClosest extends EntityAIWatchClosest {

	protected EntityLiving taskOwner;

	public EntityAINyxWatchClosest(EntityLiving owner, Class target, float range) {
		super(owner, target, range);
		this.taskOwner = owner;
	}

	public EntityAINyxWatchClosest(EntityLiving owner, Class target, float range, float chance) {
		super(owner, target, range, chance);
		this.taskOwner = owner;
	}

	@Override
	public boolean shouldExecute() {
		if (super.shouldExecute())
			return !this.closestEntity.isSneaking() || this.taskOwner.getAttackTarget() == this.closestEntity;
		return false;
	}

}
