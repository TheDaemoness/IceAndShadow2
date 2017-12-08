package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class IaSSenseTouch extends IaSSenseOld {

	public IaSSenseTouch(EntityLivingBase elb) {
		super(elb, 1);
	}

	@Override
	public boolean canSense(Entity ent) {
		return isInRange(ent);
	}

}
