package iceandshadow2.nyx.toolmats;

import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import net.minecraft.world.World;

public class NyxGrenadeShadow extends IaSGrenadeLogic {

	@Override
	public void onDetonate(EntityGrenade ent) {
		if(!ent.worldObj.isRemote) {
			ent.worldObj.spawnEntityInWorld(new EntityShadowBall(ent.worldObj, ent.posX, ent.posY, ent.posZ, true, true));
		}
	}
	
	@Override
	public String getName() {
		return "nyxShadow";
	}
}
