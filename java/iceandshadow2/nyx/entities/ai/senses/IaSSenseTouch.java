package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class IaSSenseTouch extends IaSSense {

	public IaSSenseTouch(EntityLivingBase elb) {
		super(elb, 1.5);
	}

	@Override
	public boolean canSense(Entity ent) {
		return this.isInRange(ent);
	}

}
