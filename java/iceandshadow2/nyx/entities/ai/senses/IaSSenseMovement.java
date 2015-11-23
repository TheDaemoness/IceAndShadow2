package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class IaSSenseMovement extends IaSSense {

	public IaSSenseMovement(EntityLivingBase elb, double range) {
		super(elb, range);
	}

	@Override
	public boolean canSense(Entity ent) {
		if(ent.isSprinting() || ent.fallDistance > 3.25F)
			return isInRange(ent);
		return false;
	}
}
