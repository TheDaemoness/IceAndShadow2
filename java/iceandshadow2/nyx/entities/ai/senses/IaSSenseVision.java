package iceandshadow2.nyx.entities.ai.senses;

import iceandshadow2.ias.items.tools.IaSItemArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;

public class IaSSenseVision extends IaSSense {

	public static boolean isTargetInvisible(Entity ent) {
		if (ent.isInvisible()) {
			if (ent instanceof EntityLivingBase) {
				final EntityLivingBase seekable = (EntityLivingBase) ent;
				for (int i = 0; i <= 4; ++i) {
					if (seekable.getEquipmentInSlot(i) != null) {
						if (i == 0)
							return false;
						if (i >= 2
								&& !(seekable.getEquipmentInSlot(i).getItem() instanceof IaSItemArmor))
							return false;
					}
				}
			}
			return !ent.isBurning();
		}
		return false;
	}

	public IaSSenseVision(EntityLivingBase elb, double range) {
		super(elb, range);
	}

	@Override
	public boolean canSense(Entity ent) {

		if (!isInRange(ent))
			return false;

		if (IaSSenseVision.isTargetInvisible(ent))
			return false;

		if (this.owner instanceof EntityMob) {
			if (((EntityMob) this.owner).getAttackTarget() == ent)
				this.owner.canEntityBeSeen(ent);
		}

		final double xdif = ent.posX - this.owner.posX;
		final double zdif = ent.posZ - this.owner.posZ;
		double ratio;

		if(2*Math.sqrt(xdif*xdif+zdif*zdif) < (ent.posY-this.owner.posY))
			return false;

		if (xdif == 0.0)
			ratio = zdif/xdif;
		else
			ratio = 0;

		double ang = Math.atan(ratio) * 180.0 / Math.PI;
		if (xdif < 0)
			ang += 180.0;
		else if (zdif < 0)
			ang += 360.0;

		double delta = ang - this.owner.rotationYawHead; // NOTE: When the skeleton
		// looks directly at the
		// player, this will be 90.

		delta %= 360;
		if (delta > 180)
			return false;

		return this.owner.canEntityBeSeen(ent);
	}
}
