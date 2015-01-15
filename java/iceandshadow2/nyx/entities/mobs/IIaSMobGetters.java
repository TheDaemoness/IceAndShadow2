package iceandshadow2.nyx.entities.mobs;

import net.minecraft.entity.EntityLivingBase;

public interface IIaSMobGetters {
	public double getMoveSpeed();

	public double getScaledMaxHealth();

	public EntityLivingBase getSearchTarget();

	public void setSearchTarget(EntityLivingBase ent);
}
