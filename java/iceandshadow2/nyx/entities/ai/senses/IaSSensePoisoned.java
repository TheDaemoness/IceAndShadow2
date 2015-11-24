package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.potion.Potion;

public class IaSSensePoisoned extends IaSSense {

	public IaSSensePoisoned(EntityMob elb, double range) {
		super(elb, range);
	}

	@Override
	public boolean canSense(Entity ent) {
		if(ent instanceof EntityLivingBase) {
			if(((EntityLivingBase)ent).isPotionActive(Potion.poison))
				return isInRange(ent);
		}
		return false;
	}

}
