package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class IaSSenseAura extends IaSSenseOld {

	public IaSSenseAura(EntityLivingBase elb, double range) {
		super(elb, range);
	}

	@Override
	public boolean canSense(Entity ent) {
		return isInRange(ent);
	}

}
