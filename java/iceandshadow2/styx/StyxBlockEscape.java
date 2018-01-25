package iceandshadow2.styx;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class StyxBlockEscape extends StyxBlockNoReplace {

	public StyxBlockEscape(String texName) {
		super(texName);
		setLightLevel(0.5F);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
		if (ent.isSneaking() && !world.isRemote && ent instanceof EntityLivingBase) {
			final EntityLivingBase elb = (EntityLivingBase) ent;
			int i = 6;
			for (; i < 255; ++i)
				if (world.getBlock(x, i, z) == Styx.gatestone) {
					break;
				}
			elb.setPositionAndUpdate(ent.posX, i + 1.1, ent.posZ);
		}
	}
}
