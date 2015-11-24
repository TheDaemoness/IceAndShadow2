package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class IaSSenseActions extends IaSSense {

	public IaSSenseActions(EntityLivingBase elb, double range) {
		super(elb, range);
	}

	@Override
	public boolean canSense(Entity ent) {
		if(ent instanceof EntityLivingBase) {
			if(((EntityLivingBase)ent).isSwingInProgress || ent.isEating())
				return isInRange(ent);
		}
		return false;
	}
}
