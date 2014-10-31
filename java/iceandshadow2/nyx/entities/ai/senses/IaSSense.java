package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class IaSSense {
	protected final EntityLivingBase owner;
	protected double dist;
	public IaSSense(EntityLivingBase elb, double range) {
		owner = elb;
		dist = range;
	}
	
	public double getRange() {
		return dist;
	}
	
	protected boolean isInRange(Entity ent) {
		double range = getRange();
		return owner.getDistanceSqToEntity(ent) < range*range;
	}
	
	public abstract boolean canSense(Entity ent);
}
