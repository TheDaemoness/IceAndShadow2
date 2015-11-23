package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;

public class IaSSenseEnemy extends IaSSense {

	public IaSSenseEnemy(EntityMob elb, double range) {
		super(elb, range);
	}

	@Override
	public boolean canSense(Entity ent) {
		return isInRange(ent) && ((EntityMob)this.owner).getAttackTarget() == ent;
	}

}
