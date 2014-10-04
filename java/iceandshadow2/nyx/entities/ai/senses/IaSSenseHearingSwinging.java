package iceandshadow2.nyx.entities.ai.senses;

import iceandshadow2.util.IaSEntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.MovingObjectPosition;

public class IaSSenseHearingSwinging extends IaSSense {
	
	public IaSSenseHearingSwinging(EntityLivingBase elb, double range) {
		super(elb, range);
	}
	
	public boolean canSense(Entity ent) {
		if(ent instanceof EntityLivingBase) {
			EntityLivingBase elf = (EntityLivingBase)ent;
			MovingObjectPosition p = IaSEntityHelper.getObjectPosition(owner.worldObj, elf, true);
			if(elf.isSwingInProgress)
				return this.isInRange(ent);
		}
		return false;
	}
}
