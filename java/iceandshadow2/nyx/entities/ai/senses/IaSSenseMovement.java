package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class IaSSenseMovement extends IaSSense {
	
	public IaSSenseMovement(EntityLivingBase elb, double range) {
		super(elb, range);
	}

	@Override
	public boolean isInRange(Entity ent) {
		double range = this.getRange();
		range *= ent.isSprinting()?2.0:1.0;
		return owner.getDistanceSqToEntity(ent) < range*range;
	}
	
	@Override
	public boolean canSense(Entity ent) {
		if(ent.isAirBorne || ent.isSneaking() || (ent.posX == ent.prevPosX && ent.posZ == ent.prevPosZ && !ent.isInWater()))
			return false;
		return this.isInRange(ent);
	}
}
