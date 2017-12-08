package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class IaSSenseMovement extends IaSSenseOld {

	public IaSSenseMovement(EntityLivingBase elb, double range) {
		super(elb, range);
	}

	@Override
	public boolean canSense(Entity ent) {
		if (ent == null)
			return false;
		if (ent.isSneaking())
			return false;
		if (ent.fallDistance > 3.25F)
			return isInRange(ent, 2F);
		if(ent instanceof EntityLivingBase) {
			EntityLivingBase elb = (EntityLivingBase)ent;
			double speed = Math.min(elb.moveStrafing, elb.moveForward);
			double mod = speed>0?(elb.motionX*elb.motionX + elb.motionZ*elb.motionZ)/speed:1;
			if(mod > 0.5)
				return isInRange(ent, (float)mod+(ent.isSprinting()?1:0));
		}
		return false;
	}
}
