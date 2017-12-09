package iceandshadow2.ias.ai;

import net.minecraft.entity.Entity;

public interface IIaSSensate {
	public boolean canSense(Entity ent, EnumIaSSenses sense);

	public boolean disableSense(boolean yes, EnumIaSSenses sense);

	public float getMaxSenseRange();

	public float getMaxSenseRange(EnumIaSSenses sense);

	public void notice(Entity ent, EnumIaSSenses sense);
}
