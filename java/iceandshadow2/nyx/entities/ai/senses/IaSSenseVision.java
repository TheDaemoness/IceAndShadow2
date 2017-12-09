package iceandshadow2.nyx.entities.ai.senses;

import iceandshadow2.ias.items.tools.IaSItemArmor;
import iceandshadow2.util.IaSEntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;

public class IaSSenseVision extends IaSSenseOld {

	public static boolean isTargetInvisible(Entity ent) {
		if (ent.isInvisible()) {
			if (ent instanceof EntityLivingBase) {
				final EntityLivingBase seekable = (EntityLivingBase) ent;
				for (int i = 0; i <= 4; ++i)
					if (seekable.getEquipmentInSlot(i) != null) {
						if (i == 0)
							return false;
						if (i >= 2 && !(seekable.getEquipmentInSlot(i).getItem() instanceof IaSItemArmor))
							return false;
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

		if (owner instanceof EntityMob)
			if (((EntityMob) owner).getAttackTarget() == ent)
				return owner.canEntityBeSeen(ent);
		if (!IaSEntityHelper.isInFrontOf(owner, ent))
			return false;
		return owner.canEntityBeSeen(ent);
	}
}
