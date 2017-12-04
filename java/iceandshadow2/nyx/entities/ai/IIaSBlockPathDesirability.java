package iceandshadow2.nyx.entities.ai;

import net.minecraft.world.IBlockAccess;

public interface IIaSBlockPathDesirability {
	public float getBlockPathWeight(IBlockAccess w, int x, int y, int z);
}
