package iceandshadow2.nyx.entities.mobs;

import net.minecraft.entity.EntityLivingBase;

public interface IIaSMobGetters {
	public double getMoveSpeed();
	public void setSearchTarget(EntityLivingBase ent);
	public double getScaledMaxHealth();
	public EntityLivingBase getSearchTarget();
}
