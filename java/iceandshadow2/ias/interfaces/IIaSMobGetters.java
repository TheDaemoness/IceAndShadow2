package iceandshadow2.ias.interfaces;

import net.minecraft.entity.EntityLivingBase;

public interface IIaSMobGetters {
	public double getMoveSpeed();
	public void setSearchTarget(EntityLivingBase ent);
	public EntityLivingBase getSearchTarget();
}
