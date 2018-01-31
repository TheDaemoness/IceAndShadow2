package iceandshadow2.nyx.toolmats;

import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import net.minecraft.world.World;

public class NyxGrenadeExplosive extends IaSGrenadeLogic {

	@Override
	public void onDetonate(EntityGrenade ent) {
		if(!ent.worldObj.isRemote)
			ent.worldObj.createExplosion(ent.getThrower(), ent.posX, ent.posY, ent.posZ, 2, true);
	}

	@Override
	public void onSpawnParticle(World w, double x, double y, double z, boolean fuseLit) {
		
	}

}
