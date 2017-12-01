package iceandshadow2.nyx.entities.ai;

import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.potion.Potion;

public class EntityAINyxSkeletonWeaponSwitch extends EntityAIBase {

	/** The winter skeleton. */
	EntityNyxSkeleton skel;

	public EntityAINyxSkeletonWeaponSwitch(EntityNyxSkeleton skello) {
		this.skel = skello;
		setMutexBits(1);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
	}

	@Override
	public boolean shouldExecute() {
		final EntityLivingBase entityliving = this.skel.getAttackTarget();
		if (entityliving != null) {
			if (this.skel.getHeldItem() == null && this.skel.getReserveWeapon() != null)
				return true;
			if (this.skel.getNyxSkeletonCombatType() == EntityNyxSkeleton.EnumNyxSkeletonType.BOW_FROST_SHORT) {
				if (this.skel.isUsingAlternateWeapon()) {
					if (this.skel.isPotionActive(Potion.moveSlowdown.id)
							&& this.skel.getDistanceSqToEntity(entityliving) > 9)
						return true;
					if (this.skel.getDistanceSqToEntity(entityliving) > 25)
						return true;
				} else {
					if (this.skel.isPotionActive(Potion.moveSlowdown.id)
							&& this.skel.getDistanceSqToEntity(entityliving) < 4)
						return true;
					if (!this.skel.isPotionActive(Potion.moveSlowdown.id)
							&& this.skel.getDistanceSqToEntity(entityliving) < 16)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		final EntityLivingBase entityliving = this.skel.getAttackTarget();
		if (entityliving != null) {
			if (this.skel.getNyxSkeletonCombatType() == EntityNyxSkeleton.EnumNyxSkeletonType.BOW_FROST_SHORT) {
				if (this.skel.isUsingAlternateWeapon())
					this.skel.useAlternateWeapon(false);
				else
					this.skel.useAlternateWeapon(true);
			} else if (this.skel.getHeldItem() == null) {
				if (this.skel.isUsingAlternateWeapon())
					this.skel.useAlternateWeapon(false);
				else
					this.skel.useAlternateWeapon(true);
			}
		}
	}
}
