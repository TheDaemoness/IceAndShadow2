package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class IaSSenseOld {
	protected final EntityLivingBase owner;
	protected double dist;

	public IaSSenseOld(EntityLivingBase elb, double range) {
		owner = elb;
		dist = range;
	}

	public abstract boolean canSense(Entity ent);

	public double getRange() {
		return dist;
	}

	protected boolean isInRange(Entity ent) {
		final double range = getRange();
		return owner.getDistanceSqToEntity(ent) < range * range;
	}

	protected boolean isInRange(Entity ent, float mod) {
		final double range = getRange() * mod;
		return owner.getDistanceSqToEntity(ent) < range * range;
	}
}
