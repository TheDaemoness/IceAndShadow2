package iceandshadow2.nyx.entities.ai.senses;

import iceandshadow2.ias.items.tools.IaSItemArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;

public class IaSSenseVision extends IaSSense {

	public IaSSenseVision(EntityLivingBase elb, double range) {
		super(elb, range);
	}

	public double getRange(Entity ent) {
		return dist;
	}

	public static boolean isTargetInvisible(Entity ent) {
		if(ent.isInvisible()) {
			if(ent instanceof EntityLivingBase) {
				EntityLivingBase seekable = (EntityLivingBase)ent;
				for(int i = 0; i <= 4; ++i) {
					if(seekable.getEquipmentInSlot(i) != null) {
						if(i == 0)
							return false;
						if(i >= 2 && !(seekable.getEquipmentInSlot(i).getItem() instanceof IaSItemArmor))
							return false;
					}
				}
			}
			return !ent.isBurning();	
		}
		return false;
	}

	public boolean canSense(Entity ent) {
		if(!this.isInRange(ent))
			return false;

		if(isTargetInvisible(ent))
			return false;

		double xdif = ent.posX - owner.posX;
		double zdif = ent.posZ - owner.posZ;

		if(xdif == 0.0)
			xdif += 0.0001;

		double r = Math.sqrt(xdif*xdif + zdif*zdif);
		double ang = Math.atan(zdif/xdif);

		ang *= 180.0/Math.PI;

		if(xdif < 0)
			ang += 180.0;
		else if(zdif < 0)
			ang += 360.0;

		double delta = ang-owner.rotationYawHead; //NOTE: When the skeleton looks directly at the player, this will be 90.
		if(delta > 180)
			return false;

		return owner.canEntityBeSeen(ent);
	}
}
