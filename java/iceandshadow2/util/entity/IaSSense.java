package iceandshadow2.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class IaSSense {
	protected final EntityLivingBase owner;
	public IaSSense(EntityLivingBase elb) {
		owner = elb;
	}
	public abstract boolean canSense(Entity ent);
}
