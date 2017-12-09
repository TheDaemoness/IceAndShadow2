package iceandshadow2.nyx.entities.ai.senses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class IaSSenseActions extends IaSSenseOld {

	public IaSSenseActions(EntityLivingBase elb, double range) {
		super(elb, range);
	}

	@Override
	public boolean canSense(Entity ent) {
		if (isInRange(ent))
			if (ent instanceof EntityLivingBase) {
				if (((EntityLivingBase) ent).isSwingInProgress)
					return true;
				if (ent instanceof EntityPlayer)
					return ((EntityPlayer) ent).isUsingItem();
			}
		return false;
	}
}
