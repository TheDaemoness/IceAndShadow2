package iceandshadow2.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;

public class IaSSenseHearingSwinging extends IaSSense {
	private double dist;
	
	public IaSSenseHearingSwinging(EntityLivingBase elb, double range) {
		super(elb);
		dist = range;
	}
	
	public double getRange(Entity ent) {
		return dist;
	}
	
	public boolean canSense(Entity ent) {
		return false;
	}
}
