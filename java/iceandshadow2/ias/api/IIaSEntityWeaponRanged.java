package iceandshadow2.ias.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * This enables some of Nyx's mobs to use this item as a ranged weapon.
 */
public interface IIaSEntityWeaponRanged {
	public void doRangedAttack(EntityLivingBase user, EntityLivingBase target);

	public EntityAIBase getEntityUseTask(EntityLivingBase user);
}
